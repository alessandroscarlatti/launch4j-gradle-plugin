package com.scarlatti.gradle.launch4j

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner
import io.github.lukehutch.fastclasspathscanner.scanner.ScanResult

import java.lang.reflect.Method
import java.lang.reflect.Modifier
import java.util.jar.Attributes
import java.util.jar.JarFile
import java.util.jar.Manifest

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Monday, 5/28/2018
 */
class MainClassFinder {

    private File jar

    MainClassFinder(File jar) {
        this.jar = jar
    }

    Result evaluateMainClass() {

        if (!jar.exists()) {
            throw new IllegalStateException("Jar does not exist: " + jar)
        }

        // now we know the jar exists.
        // now open it up to check if it has a main-class attribute in the manifest

        Result result = new Result()

        JarFile jarFile = new JarFile(jar)
        Manifest manifest = jarFile.getManifest()
        Attributes attributes = manifest.getMainAttributes()
        String mainClassName = attributes.getValue(Attributes.Name.MAIN_CLASS)

        if (mainClassName != null && mainClassName.trim() != '') {
            result.mainClassName = mainClassName
            result.mainClassInManifest = true
        }
        else {
            // this is the project's source code, presumably...
            // so now look in the jar for a main class...
            mainClassName = getMainClass()
            result.mainClassName = mainClassName
            result.mainClassInManifest = false
        }

        return result
    }

    /**
     * Look for a main class.
     *
     * @throws IllegalStateException if no valid main class is not found.
     */
    String getMainClass() {
        try {
            // Adds each file in each classes output directory to the classes files list.
            List<String> mainClasses = findMainClasses(jar)

            if (mainClasses.size() == 1) {
                // Valid class found; use inferred main class
                return mainClasses.get(0)
            }

            if (mainClasses.size() == 0) {
                // No main class found anywhere
                throw new IllegalStateException("Main class was not found")
            } else if (mainClasses.size() > 1) {
                // More than one main class found with no jar plugin to fall back on; error
                throw new IllegalStateException("Multiple valid main classes were found: " + String.join(", ", mainClasses))
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to get main class", e)
        }
    }

    static List<String> findMainClasses(File file) {
        // get classes from reflections

        ScanResult scanResult = new FastClasspathScanner().overrideClasspath(file).scan()
        URLClassLoader classLoader = new URLClassLoader(file.toURI().toURL())

        List<String> classNames = scanResult.getNamesOfAllClasses()
        List<String> mainClassNames = []

        for (String className : classNames) {
            try {
                Class clazz = classLoader.loadClass(className)

                // Check if class contains {@code public static void main(String[] args)}
                Method main = clazz.getMethod("main", String[].class)
                if (main != null
                        && main.getReturnType() == void.class
                        && Modifier.isStatic(main.getModifiers())
                        && Modifier.isPublic(main.getModifiers())) {
                    mainClassNames.add(clazz.name)
                }
            } catch (NoSuchMethodException ignored) {
                // main method not found
            }
        }

        return mainClassNames
    }

    class Result {
        boolean mainClassInManifest
        String mainClassName

        boolean isExecutable() {
            return (mainClassInManifest)
        }
    }
}
