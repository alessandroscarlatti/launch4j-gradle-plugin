package com.scarlatti.gradle.launch4j;

import edu.sc.seis.launch4j.tasks.Launch4jLibraryTask;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    private String name;
    private Launch4jLibraryTask task;

    private final String DEFAULT_ICON_FILE_NAME = "icon.ico";
    private final String DEFAULT_SPLASH_FILE_NAME = "splash.bmp";
    private final String DEFAULT_MANIFEST_FILE_NAME = "application.manifest";
    private final String DEFAULT_PROPERTIES_FILE_NAME = "launch4j.properties";

    public Launch4jLibraryTaskConfigurer(String name, Launch4jLibraryTask task) {
        this.name = name;
        this.task = task;

        configureDefaults();
    }

    private void configureDefaults() {
        setDefaultOutputDir();
        configureExeName(name);
    }

    private void setDefaultOutputDir() {
        task.setOutputDir("launch4j/" + task.getName());
    }

    public void configureFromResourcesDir(String dir) {
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
}
