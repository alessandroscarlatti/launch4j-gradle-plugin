package com.scarlatti.gradle.launch4j

import com.scarlatti.gradle.launch4j.util.GradleTaskGenerator
import coms.scarlatti.util.GradleBuildSpecification
import org.gradle.testkit.runner.TaskOutcome

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
                    .appendBuildFileContents(GradleTaskGenerator.assertExeCreatedTask("launch4jTask.exe"))
                    .withTask("launch4jTask")
                    .build()
        and:
            customGradleRunner().build("assertExeCreated")
    }

    def "launch4jTemplateTask is out-of-date when resourcesDir updated"() {
        setup:
            final String LAUNCH4J_TASK_NAME = ":launch4jTask_launch4jTask"
        when:
            def build1 = customGradleRunner()
                    .fromProjectDir("/src/test-projects/SimpleProjectTest")
                    .appendBuildFileFromResource("/simpleUsage.gradle")
                    .appendBuildFileFromResource("/replaceIconFile.gradle")
                    .build("launch4jTask")
        then:
            build1.task(LAUNCH4J_TASK_NAME) != null
            build1.task(LAUNCH4J_TASK_NAME).outcome == TaskOutcome.SUCCESS

        when:
            customGradleRunner().build("replaceIconFile")
        and:
            def build2 = customGradleRunner().build("launch4jTask")
        then:
            build2.task(LAUNCH4J_TASK_NAME) != null
            build2.task(LAUNCH4J_TASK_NAME).outcome == TaskOutcome.SUCCESS

    }
}
