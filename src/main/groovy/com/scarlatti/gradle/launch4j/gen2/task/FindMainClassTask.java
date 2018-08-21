package com.scarlatti.gradle.launch4j.gen2.task;

import com.scarlatti.gradle.launch4j.MainClassFinder;
import com.scarlatti.gradle.launch4j.gen2.details.MainClassConfigurationDetails;
import org.gradle.api.DefaultTask;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.TaskAction;

import java.io.File;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Tuesday, 8/14/2018
 */
public class FindMainClassTask extends DefaultTask {

    @Internal
    private MainClassFinder.Result result;

    /**
     * Configure this task from a PropertiesConfigurationDetails instance.
     * Called after this task has been created.
     */
    public void configureFromMainClassDetails(MainClassConfigurationDetails details) {
        setEnabled(details.getFindMainClass());

        String mainClassName = details.getMainClassName();
        if (mainClassName != null && !mainClassName.trim().isEmpty()) {
            result = new MainClassFinder.Result(false, mainClassName);
        }
    }

    @TaskAction
    public void configureMainClass() {

        if (result != null)
            return;

        if (getProject().getPlugins().findPlugin("java") != null) {
            File jarFile = getProject().getTasks().getByName(JavaPlugin.JAR_TASK_NAME).getOutputs().getFiles().getSingleFile();
            result = new MainClassFinder(jarFile).evaluateMainClass();
        }
    }

    public MainClassFinder.Result getResult() {
        return result;
    }
}
