package com.scarlatti.gradle.launch4j

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner
import spock.lang.Specification

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Monday, 5/28/2018
 */
class MainClassFinderTest extends Specification {

    static String projectDir = System.getProperty("user.dir")

    def "find main class in jar using FastClasspathScanner"() {
        setup:
            def scanner = new FastClasspathScanner("jar:ProduceJarWithMainClass.jar")
                    .overrideClasspath("${projectDir}/src/test/resources/jars/ProduceJarWithMainClass.jar")
        when:
            def result = scanner.scan()
        then:
            result != null
            result.namesOfAllClasses != null
            result.namesOfAllClasses.size() > 0
            result.namesOfAllClasses.contains("com.scarlatti.HelloWorldUtility")
    }


    def "find main class in jar using file with FastClasspathScanner"() {
        setup:
            def scanner = new FastClasspathScanner()
                    .overrideClasspath(new File("${projectDir}/src/test/resources/jars/ProduceJarWithMainClass.jar"))
        when:
            def result = scanner.scan()
        then:
            result != null
            result.namesOfAllClasses != null
            result.namesOfAllClasses.size() > 0
            result.namesOfAllClasses.contains("com.scarlatti.HelloWorldUtility")
    }

    def "find main class using static method"() {
        when:
            def result = MainClassFinder.findMainClasses(new File("${projectDir}/src/test/resources/jars/ProduceJarWithMainClass.jar"))
        then:
            result != null
            result.size() == 1
            result[0] == "com.scarlatti.HelloWorldUtility"
    }

    def "find main class from jar using utility"() {
        setup:
            File jar = new File("${projectDir}/src/test/resources/jars/ProduceJarWithMainClass.jar")
            MainClassFinder mainClassFinder = new MainClassFinder(jar)
        when:
            def result = mainClassFinder.getMainClass()
        then:
            result != null
            result == "com.scarlatti.HelloWorldUtility"
    }

    def "find main class from manifest using utility"() {
        setup:
            File jar = new File("${projectDir}/src/test/resources/jars/ProduceJarWithMainClassManifest.jar")
            MainClassFinder mainClassFinder = new MainClassFinder(jar)
        when:
            def result = mainClassFinder.getMainClass()
        then:
            result != null
            result == "com.scarlatti.HelloWorldUtility"
    }

    def "find two main classes"() {
        when:
            def result = MainClassFinder.findMainClasses(new File("${projectDir}/src/test/resources/jars/ProduceJarWithTwoMainClasses.jar"))
        then:
            result != null
            result.size() == 2
    }

    def "find two main classes throws exception"() {
        setup:
            File jar = new File("${projectDir}/src/test/resources/jars/ProduceJarWithTwoMainClasses.jar")
            MainClassFinder mainClassFinder = new MainClassFinder(jar)
        when:
            mainClassFinder.getMainClass()
        then:
            Exception e = thrown()
            e.printStackTrace()
            e instanceof IllegalStateException
    }
}
