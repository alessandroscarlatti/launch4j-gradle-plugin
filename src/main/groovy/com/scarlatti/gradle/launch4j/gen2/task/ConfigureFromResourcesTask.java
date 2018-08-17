package com.scarlatti.gradle.launch4j.gen2.task;

import com.scarlatti.gradle.launch4j.gen2.details.IconConfigurationDetails;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Wednesday, 8/15/2018
 */
public class ConfigureFromResourcesTask extends DefaultTask {

    private Launch4jHelperTask helperTask;

    // todo but this part of the incremental build may not be that big of a deal.  It may be
    // OK to just use the configuration details from the helperTask.
    // TODO COULD THE EXTENSION USE THE ICON/SPLASH/MANIFEST/RESOURCES GENERATION TASKS INSTEAD OF COPYING THE
    // configuration details into this task???
    // todo 1 use an interface to define the default configuration details classes.
    // todo 2 pass generation tasks as delegates for icon/splash/manifest/resources rather than config details
    // todo 3 connect all of those together

    /**
     * This task would do the following:
     * <p>
     * - read launchj.properties and apply to the launch4j task
     * - if headerType turns out to use splash, configure generateSplashTask
     * - configure generateIconTask
     * - configure generateManifestTask
     */
    @TaskAction
    public void configureFromResources() {
        configureLaunch4jPropertiesFile();
        configureSupplyIconTask();
    }

    private void configureLaunch4jPropertiesFile() {
        File file = helperTask.getResources().getLaunch4jPropertiesResolutionStrategy().resolve(helperTask);
        if (file != null) {
            configureLaunch4jTaskFromProperties(file.toPath());
        }
    }

    /**
     * Apply the properties in the properties file to the launch4j task.
     *
     * @param propertiesFile the properties file to use
     */
    private void configureLaunch4jTaskFromProperties(Path propertiesFile) {
        Properties props = new Properties();

        // load the properties
        try (InputStream propsStream = Files.newInputStream(propertiesFile)) {
            props.load(propsStream);
        }
        catch (Exception e) {
            throw new RuntimeException("Error loading properties file " + propertiesFile, e);
        }

        // now try updating the task
        for (String key : props.stringPropertyNames()) {
            Object value = props.get(key);
            System.out.println("Configuring launch4j task with " + key + "=" + value);
            helperTask.getLaunch4jTask().setProperty(key, value);
        }
    }

    /**
     * Configure the associated SupplyIconTask.
     * <p>
     * If we are not set up to configure an icon, disable the task.
     * <p>
     * If we are set up to generate at all:
     * Resolve an icon using the resolution strategy.
     */
    private void configureSupplyIconTask() {
        SupplyIconTask task = helperTask.getSupplyIconTask();
        if (helperTask.getIcon().getEnabled()) {
            IconConfigurationDetails config = helperTask.getIcon();
            File icon = config.getResolutionStrategy().resolve(helperTask);
            task.setIcon(icon);
            task.setIconName(config.getName());
            task.setAutoGenerate(config.getAutoGenerate());
            task.setDestination(new File("build/launch4j/#taskName/resources/icon.ico"));  // todo get the launch4j resources directory
        }
        else {
            task.setEnabled(false);
        }
    }

    public Launch4jHelperTask getHelperTask() {
        return helperTask;
    }

    public void setHelperTask(Launch4jHelperTask helperTask) {
        this.helperTask = helperTask;
    }
}
