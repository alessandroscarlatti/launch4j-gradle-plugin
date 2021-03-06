package com.scarlatti.gradle.launch4j

import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import spock.lang.Ignore
import spock.lang.Specification

import java.nio.file.Paths

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Friday, 7/20/2018
 */
@Ignore
class IdeIntegrationTest extends Specification {

    def "run :tasks"() {
        when:
            def result = GradleRunner.create()
                    .withProjectDir(Paths.get("ideIntegration").toFile())
                    .withArguments('tasks', '--stacktrace')
                    .withPluginClasspath()
                    .withDebug(true)
                    .build()

        then:
            println result.output
            result.task(":tasks").outcome == TaskOutcome.SUCCESS
    }

    def "run :configureExeResourcesConfigureProperties"() {
        when:
            def result = GradleRunner.create()
                    .withProjectDir(Paths.get("ideIntegration").toFile())
                    .withArguments('configureExeResourcesConfigureProperties', '--stacktrace')
                    .withPluginClasspath()
                    .withDebug(true)
                    .build()

        then:
            println result.output
            result.task(":configureExeResourcesConfigureProperties").outcome == TaskOutcome.SUCCESS
    }

    def "run :configureExeResourcesSupplyIcon"() {
        when:
            def result = GradleRunner.create()
                    .withProjectDir(Paths.get("ideIntegration").toFile())
                    .withArguments('configureExeResourcesSupplyIcon', '--stacktrace')
                    .withPluginClasspath()
                    .withDebug(true)
                    .build()

        then:
            println result.output
            result.task(":configureExeResourcesSupplyIcon").outcome == TaskOutcome.SUCCESS
    }

    def "run :createExe"() {
        when:
            def result = GradleRunner.create()
                    .withProjectDir(Paths.get("ideIntegration").toFile())
                    .withArguments('createExe', '--stacktrace')
                    .withPluginClasspath()
                    .withDebug(true)
                    .build()

        then:
            println result.output
            result.task(":createExe").outcome == TaskOutcome.SUCCESS
    }
}
