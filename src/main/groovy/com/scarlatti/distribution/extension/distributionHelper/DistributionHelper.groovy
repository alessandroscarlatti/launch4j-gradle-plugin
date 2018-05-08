package com.scarlatti.distribution.extension.distributionHelper

import edu.sc.seis.launch4j.tasks.Launch4jLibraryTask
import org.gradle.api.Task
import org.gradle.api.distribution.Distribution
import org.gradle.api.tasks.bundling.Tar

import java.nio.file.Paths

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Monday, 5/7/2018
 */
class DistributionHelper {
    private final Distribution distribution

    DistributionHelper(Distribution distribution) {
        this.distribution = distribution
    }

    DistributionHelper from(Launch4jLibraryTask task) {
        String distributionSourceDir = Paths.get(task.project.buildDir.absolutePath, task.outputDir).toString()
        distribution.baseName = "${task.productName}-${distribution.baseName}"
        distribution.contents {
            from { distributionSourceDir }
        }

        setDependencies(task)

        return this
    }

    private void setDependencies(Task task) {
        task.project.tasks.each {
            if (it.name.toLowerCase().contains("dist") && it.group == ("distribution")) {
                it.dependsOn(task)
            }
        }
    }
}
