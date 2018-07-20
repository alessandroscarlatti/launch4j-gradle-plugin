package com.scarlatti.gradle.launch4j.gen2

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Wednesday, 5/23/2018
 */
class Launch4jHelperPlugin implements Plugin<Project> {
    public static final String LAUNCH4J_HELPER_EXTENSION_NAME = "launch4jHelper"
    public static final String LAUNCH4J_HELPER_TASK_GROUP = "launch4jHelper"

    /**
     * Setup the Launch4jHelperExtension.
     * We will not apply the launch4j plugin for the user
     * in the interest of clarity.
     *
     * @param project the project receiving this plugin
     */
    @Override
    void apply(Project project) {
        project.extensions.add(LAUNCH4J_HELPER_EXTENSION_NAME, new Launch4jHelperExtension(project))
    }
}
