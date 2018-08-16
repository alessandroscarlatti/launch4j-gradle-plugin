package com.scarlatti.gradle.launch4j.gen2.task;

import edu.sc.seis.launch4j.tasks.Launch4jLibraryTask;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
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

    @Input
    private GenerateIconTask generateIconTask;

    @Input
    private GenerateSplashTask generateSplashTask;

    @Input
    private GenerateManifestTask generateManifestTask;

    @Input
    private Launch4jHelperTask helperTask;

    @Input
    private Launch4jLibraryTask launch4jTask;

    // todo but this part of the incremental build may not be that big of a deal.  It may be
    // OK to just use the configuration details from the helperTask.
    // TODO COULD THE EXTENSION USE THE ICON/SPLASH/MANIFEST GENERATION TASKS INSTEAD OF COPYING THE
    // configuration details into this task???

    @InputFile
    private File iconFile;

    @InputFile
    private File splashFile;

    @InputFile
    private File manifestFile;

    @InputFile
    private File launch4jPropertiesFile;

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
        configureIconTask();
    }

    private void configureLaunch4jPropertiesFile() {
        if (launch4jPropertiesFile != null) {
            Path file = launch4jPropertiesFile.toPath();
            if (Files.exists(file)) {
                configureLaunch4jTaskFromProperties(file);
            }
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
        } catch (Exception e) {
            throw new RuntimeException("Error loading properties file " + propertiesFile, e);
        }

        // now try updating the task
        for (String key : props.stringPropertyNames()) {
            Object value = props.get(key);
            System.out.println("Configuring launch4j task with " + key + "=" + value);
            launch4jTask.setProperty(key, value);
        }
    }

    /**
     * Configure the associated GenerateIconTask.
     *
     * If we are not set up to configure an icon, disable the task.
     *
     * If we are set up to generate at all:
     * If an icon resource exists, set the GenerateIconTask to use it.
     * If we are set up to autogenerate, provide the iconName.
     */
    private void configureIconTask() {
        if (helperTask.getIcon().getEnabled()) {
            // this is the same as helperTask#getIcon#getLocation because the value in THIS file is set from the helperTask.
            // how do we know whether or not to use the file location from the resources dir or the icon task?
            // at helperTask creation time, the icon data from the extension is copied to the task.
            // it may be changed during configuration time.
            // it is finally used at runtime...but by whom?
            // The extension path should be based on the resources dir.
            // This means that any change to the resources dir should affect the icon path.
            // todo should we have an iconResolutionStrategy?
            generateIconTask.setIcon(iconFile);
            generateIconTask.setIconName(helperTask.getIcon().getName());
            generateIconTask.setAutoGenerate(helperTask.getIcon().getAutoGenerate());
            generateIconTask.setDestination(new File("build/launch4j/#taskName/resources/icon.ico"));  // todo get the launch4j resources directory
        }
        else {
            generateIconTask.setEnabled(false);
        }
    }
}
