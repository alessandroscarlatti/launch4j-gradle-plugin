package com.scarlatti.launch4j.extension.launch4jLibraryTaskHelper;

import edu.sc.seis.launch4j.tasks.Launch4jLibraryTask;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Saturday, 5/5/2018
 *
 * A configurer for a Launch4JLibraryTask.
 */
public class Launch4jLibraryTaskConfigurer implements Runnable {
    private Launch4jLibraryTask task;
    private Launch4jLibraryTaskConfigurationDelegate config;

    // convention constants
    private final String DEFAULT_ICON_FILE_NAME = "icon.ico";
    private final String DEFAULT_SPLASH_FILE_NAME = "splash.bmp";
    private final String DEFAULT_MANIFEST_FILE_NAME = "application.manifest";

    // caching
    private boolean haveEvaluatedResourcesDir;
    private Property<String> cachedResourcesDir = new Property<>();

    public Launch4jLibraryTaskConfigurer(Launch4jLibraryTask task, Launch4jLibraryTaskConfigurationDelegate config) {
        this.task = task;
        this.config = config;
    }

    @Override
    public void run() {
        // configure the task based on the configuration.
        Launch4jLibraryTaskProps launch4JLibraryTaskProps = buildLaunch4jTaskProps();
        configure(launch4JLibraryTaskProps);
    }

    /**
     * Convert the config into actual launch4j task props.
     *
     * @return the actual launch4j props.
     */
    private Launch4jLibraryTaskProps buildLaunch4jTaskProps() {
        Launch4jLibraryTaskProps props = new Launch4jLibraryTaskProps();

        evaluateOutputDir(props);
        evaluateIconPath(props);
        evaluateSplashPath(props);
        evaluateManifestPath(props);

        return props;
    }

    private void evaluateOutputDir(Launch4jLibraryTaskProps props) {
        String outputDir = config.getOutputDir();
        if (outputDir != null) {

            // convert to relative to 'build' folder

            props.getOutputDir().setValue(outputDir);
        }
    }

    private void evaluateIconPath(Launch4jLibraryTaskProps props) {
        if (getResourcesDir().isSet()) {

            // look for a file in the resources dir
            Path iconPath = Paths.get(getResourcesDir().getValue(), DEFAULT_ICON_FILE_NAME);
            if (Files.exists(iconPath)) {
                props.getIconPath().setValue(iconPath.toString());
            }

            // if file not found, leave icon unchanged...
        }
    }

    private void evaluateSplashPath(Launch4jLibraryTaskProps props) {
        if (getResourcesDir().isSet()) {

            // look for a file in the resources dir
            Path splashPath = Paths.get(getResourcesDir().getValue(), DEFAULT_SPLASH_FILE_NAME);
            if (Files.exists(splashPath)) {
                props.getSplashPath().setValue(splashPath.toString());
            }

            // if file not found, leave splash unchanged...
        }
    }

    private void evaluateManifestPath(Launch4jLibraryTaskProps props) {
        if (getResourcesDir().isSet()) {

            // look for a file in the resources dir
            Path manifestPath = Paths.get(getResourcesDir().getValue(), DEFAULT_MANIFEST_FILE_NAME);
            if (Files.exists(manifestPath)) {
                props.getManifestPath().setValue(manifestPath.toString());
            }

            // if file not found, leave manifest unchanged...
        }
    }

    private Property<String> getResourcesDir() {
        if (haveEvaluatedResourcesDir) return cachedResourcesDir;

        if (config.getResourcesDir() != null) {
            String configResourcesDir = config.getResourcesDir();
            String absoluteProjectDir = task.getProject().getProjectDir().getAbsolutePath();
            String absoluteResourcesDir = Paths.get(absoluteProjectDir, configResourcesDir).toString();
            if (configResourcesDir != null) cachedResourcesDir.setValue(absoluteResourcesDir);
        }

        haveEvaluatedResourcesDir = true;
        return cachedResourcesDir;
    }

    /**
     * Configure the task with the given launch4j props.
     * @param props the props to use when configuring the task.
     */
    private void configure(Launch4jLibraryTaskProps props) {
        System.out.println(props);
        if (props.getOutputDir().isSet()) task.setOutputDir(props.getOutputDir().getValue());
        if (props.getIconPath().isSet()) task.setIcon(props.getIconPath().getValue());
        if (props.getSplashPath().isSet()) task.setSplashFileName(props.getSplashPath().getValue());
        if (props.getManifestPath().isSet()) task.setManifest(props.getManifestPath().getValue());
    }
}
