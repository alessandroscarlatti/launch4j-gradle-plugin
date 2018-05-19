package com.scarlatti.distribution.extension

import com.scarlatti.distribution.extension.distributionHelper.DistributionHelper
import com.scarlatti.distribution.extension.launch4jLibraryTaskHelper.Launch4jLibraryTaskHelper
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
class DistributionHelperExtension {
    final Project project

    DistributionHelperExtension(Project project) {
        this.project = project
    }

    Launch4jLibraryTaskHelper with(Launch4jLibraryTask task) {
        return new Launch4jLibraryTaskHelper(task)
    }

    DistributionHelper configure(Distribution distribution) {
        new DistributionHelper(distribution)
    }
}
