package com.scarlatti.gradle.launch4j;

import edu.sc.seis.launch4j.tasks.Launch4jLibraryTask;
import org.gradle.api.Action;
import org.gradle.api.Task;
import org.gradle.api.plugins.JavaPlugin;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Saturday, 5/5/2018
 *
 * A configurer for a Launch4JLibraryTask.
 */
public class Launch4jLibraryTaskConfigurer {
    private Launch4jLibraryTask task;
    private String parentTaskName;

    private final String DEFAULT_ICON_FILE_NAME = "icon.ico";
    private final String DEFAULT_SPLASH_FILE_NAME = "splash.bmp";
    private final String DEFAULT_MANIFEST_FILE_NAME = "application.manifest";
    private final String DEFAULT_PROPERTIES_FILE_NAME = "launch4j.properties";
    private final String DEFAULT_GENERATED_MANIFEST_FILE_NAME_BASE = "generated-manifest-${hash}.manifest";

    private Action<Task> generateManifest;
    private Action<Task> cleanupManifest;

    public Launch4jLibraryTaskConfigurer(Launch4jLibraryTask task, String parentTaskName) {
        this.parentTaskName = parentTaskName;
        this.task = task;
        configureDefaults();
    }

    private void configureDefaults() {
        setDefaultOutputDir();
        configureExeName(task.getProject().getName());
        configureNoOpGeneratedManifest();
        configureTaskGenerateManifestAspect();
        task.setGroup("launch4j");
        task.setDescription("Build the exe for the '${name} Launch4j template task.");
        task.setStayAlive(true);
        task.setHeaderType("console");  // set as "console" so that there will be visible output by default
        task.setFileDescription(parentTaskName);
        task.setProductName(parentTaskName);
        task.setInternalName(parentTaskName);  // otherwise, it will be the project name, which could be too long (> 50 chars)

        task.doFirst(this::configureMainClass);
    }

    void configureDependencies() {
        if (task.getProject().getTasks().findByName("bootRepackage") != null) {
            fromBootRepackage();
        }
        else if (task.getProject().getTasks().findByName("bootJar") != null) {
            fromBootJar();
        }
    }

    private void fromBootRepackage() {
        task.dependsOn("bootRepackage");
        task.setCopyConfigurable(task.getProject().getTasks().findByName("jar").getOutputs().getFiles());
    }

    private void fromBootJar() {
        task.dependsOn("bootJar");
        task.setCopyConfigurable(task.getProject().getTasks().findByName("jar").getOutputs().getFiles());
    }

    private void setDefaultOutputDir() {
        task.setOutputDir(getDefaultOutputDir());
    }

    private String getDefaultOutputDir() {
        return "launch4j/" + parentTaskName;
    }

    private String getDefaultGeneratedManifestPath(String content) {
        return Paths.get(
            task.getProject().getBuildDir().getAbsolutePath(),
            getDefaultOutputDir(),
            getDefaultGeneratedManifestFileName(content)
        ).toString();
    }

    private String getDefaultGeneratedManifestFileName(String content) {
        return DEFAULT_GENERATED_MANIFEST_FILE_NAME_BASE.replace("${hash}", Integer.toHexString(content.hashCode()));
    }

    public void configureFromResourcesDir(String dir) {

        if (dir == null) return;

        task.getInputs().dir(Paths.get(dir).toFile()).optional();

        // evaluate any other properties, with respect to the custom properties
        configureIconPath(Paths.get(dir, DEFAULT_ICON_FILE_NAME).toString());
        configureSplashPath(Paths.get(dir, DEFAULT_SPLASH_FILE_NAME).toString());
        configureManifestPath(Paths.get(dir, DEFAULT_MANIFEST_FILE_NAME).toString());

        // configure properties from file to get the user's custom properties
        configurePropertiesPath(Paths.get(dir, DEFAULT_PROPERTIES_FILE_NAME).toString());
    }

    public void configureExeName(String name) {
        task.setOutfile(name + ".exe");
    }

    private void configureIconPath(String iconPath) {
        // look for a file in the resources dir
        if (Files.exists(Paths.get(iconPath))) {
            task.setIcon(iconPath);
        }

        // if file not found, leave icon unchanged...
    }

    private void configureSplashPath(String splashPath) {
        // look for a file in the resources dir
        if (Files.exists(Paths.get(splashPath))) {
            task.setSplashFileName(splashPath);
            task.setHeaderType("gui");
            // if we are providing a splash via a file,
            // go ahead and change the header to "gui" so that it will run.
        }

        // if file not found, leave splash unchanged...
    }

    private void configureManifestPath(String manifestPath) {
        // look for a file in the resources dir
        if (Files.exists(Paths.get(manifestPath))) {
            task.setManifest(manifestPath);
        }

        // if file not found, leave manifest unchanged...
    }

    private void configureTaskGenerateManifestAspect() {
        task.doFirst(this::generateManifest);
        task.doLast(this::cleanupManifest);
    }

    private void generateManifest(Object task) {
        generateManifest.execute((Task) task);
    }

    private void cleanupManifest(Object task) {
        cleanupManifest.execute((Task) task);
    }

    private void configureNoOpGeneratedManifest() {
        generateManifest = (task) -> {};
        cleanupManifest = (task) -> {};
    }

    public void configureGeneratedManifest(String manifest) {

        if (manifest == null) manifest = "";
        byte[] bytes = manifest.getBytes();
        String manifestPath = getDefaultGeneratedManifestPath(manifest);

        // add the generated file to the designated location
        generateManifest = task -> {
            try {
                Files.createDirectories(Paths.get(manifestPath).getParent());
                Files.write(Paths.get(manifestPath), bytes);
            } catch (Exception e) {
                throw new RuntimeException("Error generating manifest file at " + manifestPath, e);
            }
        };

        // delete the generated file from the designated location
        cleanupManifest = task -> {
            try {
                Files.delete(Paths.get(manifestPath));
            } catch (Exception e) {
                new RuntimeException("Error deleting generated manifest file at " + manifestPath, e).printStackTrace();
            }
        };

        task.setManifest(manifestPath);
    }

    private void configurePropertiesPath(String propertiesPath) {
        // look for a file in the resources dir
        if (Files.exists(Paths.get(propertiesPath))) {
            configurePropertiesFromFile(propertiesPath);
        }

        // if file not found, don't apply any property changes
    }

    private void configurePropertiesFromFile(String propertiesFile) {
        Properties props = new Properties();

        // load the properties
        try (InputStream propsStream = Files.newInputStream(Paths.get(propertiesFile))) {
            props.load(propsStream);
        } catch (Exception e) {
            throw new RuntimeException("Error loading properties file " + propertiesFile, e);
        }

        // now try updating the task
        for (String key : props.stringPropertyNames()) {
            Object value = props.get(key);
            System.out.println("Configuring launch4j task with " + key + "=" + value);
            task.setProperty(key, value);
        }
    }

    private void configureMainClass(Task ignored) {
        // TODO it depends on how we are doing this...
        // if we check for the main class at configuration time,
        // or if we check for the main class at execution time.
        // organize as doFirst() on the launch4j task

        // so... we only need to check for the main class if it is not specified in the task property
        if (task.getMainClassName() != null && !task.getMainClassName().trim().equals("")) return;

        if (task.getProject().getPlugins().findPlugin("java") != null) {
            File jarFile = task.getProject().getTasks().getByName(JavaPlugin.JAR_TASK_NAME).getOutputs().getFiles().getSingleFile();
            MainClassFinder.Result result = new MainClassFinder(jarFile).evaluateMainClass();

            if (result.isExecutable()) {
                System.out.println("Jar will automatically execute main class " + result.getMainClassName());
            } else {
                task.setMainClassName(result.getMainClassName());
            }
        }
    }
}
