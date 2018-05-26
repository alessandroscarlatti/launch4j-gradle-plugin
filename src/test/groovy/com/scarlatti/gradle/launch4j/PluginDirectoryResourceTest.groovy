package com.scarlatti.gradle.launch4j

import coms.scarlatti.util.GradleBuildSpecification

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Saturday, 5/26/2018
 */
class PluginDirectoryResourceTest extends GradleBuildSpecification {

    def "exe directory not required for build"() {
        expect:
            customGradleRunner()
                    .fromProjectDir("/src/test-projects/ProjectWithoutExeDirectoryShouldBeOK")
                    .appendBuildFileFromResource("/simpleUsage.gradle")
                    .withTask("launch4jTask")
                    .build()
    }
}
