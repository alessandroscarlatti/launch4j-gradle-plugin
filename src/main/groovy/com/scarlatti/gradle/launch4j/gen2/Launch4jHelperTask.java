package com.scarlatti.gradle.launch4j.gen2;

import edu.sc.seis.launch4j.tasks.Launch4jLibraryTask;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.Task;

import static com.scarlatti.gradle.launch4j.gen2.Launch4jHelperExtension.validateTaskIsLaunch4jLibraryTask;
import static com.scarlatti.gradle.launch4j.gen2.Launch4jHelperPlugin.LAUNCH4J_HELPER_EXTENSION_NAME;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Thursday, 7/19/2018
 * <p>
 * A helper task to locate, generate, and configure
 * resources for a launch4j task based on:
 * - global specifications from launch4jHelperExtension
 * - specific instructions from configuration of this task
 * - local resources, based on naming conventions
 */
public class Launch4jHelperTask extends DefaultTask {
    private Project project;
    private Launch4jHelperExtension extension;
    private Launch4jLibraryTask launch4jTask;

    /**
     * Create a new Launch4jHelperTask for a launch4jTask.
     *
     * @param task the launch4jTask this task will help.  For the time being, must be a Launch4jLibraryTask.
     */
    public Launch4jHelperTask(Task task) {
        validateTaskIsLaunch4jLibraryTask(task);

        this.project = launch4jTask.getProject();
        this.extension = (Launch4jHelperExtension) project.getExtensions().findByName(LAUNCH4J_HELPER_EXTENSION_NAME);
        this.launch4jTask = (Launch4jLibraryTask) task;

        // todo create the dependencies...
    }

    // todo declare task inputs if any...
    // todo declare task outputs
}
