package com.scarlatti.launch4j.extension

import com.scarlatti.launch4j.extension.launch4jLibraryTaskHelper.Launch4jLibraryTaskConfigurationDelegate
import com.scarlatti.launch4j.extension.launch4jLibraryTaskHelper.Launch4jLibraryTaskConfigurer
import edu.sc.seis.launch4j.tasks.Launch4jLibraryTask
import org.gradle.api.Project

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Saturday, 5/5/2018
 */
class Launch4JHelperExtension {
    final Project project

    Launch4JHelperExtension(Project project) {
        this.project = project
    }

    void configure(Launch4jLibraryTask task, @DelegatesTo(Launch4jLibraryTaskConfigurationDelegate) Closure closure) {
        Launch4jLibraryTaskConfigurationDelegate config = new Launch4jLibraryTaskConfigurationDelegate()
        closure.delegate = config
        closure()
        doConfigure(task, config)
    }

    /**
     * Apply the settings stored in the configurer.
     *
     * @param task the task under configuration
     * @param configurer the configurer that has been used.
     */
    private void doConfigure(Launch4jLibraryTask task, Launch4jLibraryTaskConfigurationDelegate config) {
        new Launch4jLibraryTaskConfigurer(task, config).run()
    }
}
