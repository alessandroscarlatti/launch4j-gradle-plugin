package com.scarlatti.launch4j

import com.scarlatti.testing.util.GradleBuildSpecification
import org.gradle.testkit.runner.TaskOutcome

import static BuildTemplates.*

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Monday, 7/23/2018
 */
class Launch4jHelperPluginTest extends GradleBuildSpecification {

    def "plugin can be applied without any usage or launch4j plugin"() {
        when:
            def result = customGradleRunner()
                    .appendBuildFileFromResource(common())
                    .withTask(tasks())
                    .build()
        then:
            result.task(tasks()).outcome == TaskOutcome.SUCCESS
    }

    def "plugin can be applied with just launch4j plugin"() {
        when:
            def result = customGradleRunner()
                    .appendBuildFileFromResource(common())
                    .appendBuildFileContents(applyLaunch4jPlugin())
                    .withTask(tasks())
                    .build()
        then:
            result.task(tasks()).outcome == TaskOutcome.SUCCESS
    }
}
