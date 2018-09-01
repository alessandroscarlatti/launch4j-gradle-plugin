package com.scarlatti.launch4j.task;

import com.scarlatti.launch4j.FileResolutionStrategy;
import com.scarlatti.launch4j.details.PropertiesConfigurationDetails;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Internal;
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
 * Tuesday, 8/14/2018
 */
public class ConfigureLaunch4jFromPropertiesTask extends DefaultTask {

    @Internal
    private Launch4jHelperTask helperTask;
    @Internal
    private FileResolutionStrategy resolve;

    /**
     * Configure this task from a PropertiesConfigurationDetails instance.
     * Called after this task has been created.
     */
    public void configureFromPropertiesConfigDtls(PropertiesConfigurationDetails details) {
        this.resolve = details.getResolve();
    }

    /**
     * This task should never be skipped as "UP-TO-DATE" due to inputs or outputs.
     * The properties always need to be applied to ensure accuracy
     * in applying the "supply resources" tasks.
     */
    @TaskAction
    public void configureLaunch4jPropertiesFile() {
        File file = resolve.resolve(helperTask);
        if (file != null) {
            configureLaunch4jTaskFromProperties(file.toPath());
        }
    }

    /**
     * Apply the properties in the properties file to the launch4j task.
     * If the file doesn't exist, we won't read it.
     *
     * @param propertiesFile the properties file to use
     */
    private void configureLaunch4jTaskFromProperties(Path propertiesFile) {
        if (!Files.exists(propertiesFile))
            return;

        // load the properties
        Properties props = new Properties();
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
            helperTask.getLaunch4jTask().setProperty(key, value);  // this will succeed even if the task doesn't explicitly declare the property
        }
    }

    public FileResolutionStrategy getResolve() {
        return resolve;
    }

    public void setResolve(FileResolutionStrategy resolve) {
        this.resolve = resolve;
    }

    public Launch4jHelperTask getHelperTask() {
        return helperTask;
    }

    public void setHelperTask(Launch4jHelperTask helperTask) {
        this.helperTask = helperTask;
    }
}
