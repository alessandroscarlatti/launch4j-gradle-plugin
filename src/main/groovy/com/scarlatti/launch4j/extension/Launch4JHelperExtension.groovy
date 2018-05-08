package com.scarlatti.launch4j.extension

import com.scarlatti.launch4j.extension.launch4jLibraryTaskHelper.Launch4jLibraryTaskConfigurationDelegate
import com.scarlatti.launch4j.extension.launch4jLibraryTaskHelper.Launch4jLibraryTaskConfigurer
import com.scarlatti.launch4j.extension.launch4jLibraryTaskHelper.Launch4jLibraryTaskHelper
import edu.sc.seis.launch4j.tasks.Launch4jLibraryTask
import org.gradle.api.Project
import org.gradle.api.distribution.Distribution

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

    Launch4jLibraryTaskHelper with(Launch4jLibraryTask task) {
        return new Launch4jLibraryTaskHelper(task)
    }

    void configure(Launch4jLibraryTask task, @DelegatesTo(Launch4jLibraryTaskConfigurationDelegate) Closure closure) {
        new Launch4jLibraryTaskHelper(task).configure(closure)
    }
}
