package com.scarlatti.gradle.launch4j

import com.scarlatti.gradle.launch4j.util.GradleTaskGenerator
import com.scarlatti.testing.util.GradleBuildSpecification
import spock.lang.IgnoreIf
import spock.util.environment.OperatingSystem

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Monday, 5/28/2018
 */
class ExeRunsMainClassTest extends GradleBuildSpecification {

    @IgnoreIf({!OperatingSystem.current.windows})
    def "exe runs main class for vanilla java application"() {
        setup:
            customGradleRunner()
                .fromProjectDir("src/test-projects/VanillaConsoleApp")
                .appendBuildFileFromResource("/simpleUsage.gradle")
                .build("launch4jTask")

        when:
            def result = customGradleRunner()
                .appendBuildFileContents(GradleTaskGenerator.runExe("launch4jTask", "launch4jTask"))
                .build("runExe")
        then:
            result.output.contains("hello world")
    }

    @IgnoreIf({!OperatingSystem.current.windows})
    def "exe runs main class for vanilla java application without specifying main class"() {
        setup:
            customGradleRunner()
                    .fromProjectDir("src/test-projects/VanillaConsoleAppDoesNotSpecifyMainClass")
                    .appendBuildFileFromResource("/simpleUsage.gradle")
                    .build("launch4jTask")

        when:
            def result = customGradleRunner()
                    .appendBuildFileContents(GradleTaskGenerator.runExe("launch4jTask", "launch4jTask"))
                    .build("runExe")
        then:
            result.output.contains("hello world")
    }

    @IgnoreIf({!OperatingSystem.current.windows})
    def "exe runs main class for Spring Boot java application without specifying main class"() {
        setup:
            customGradleRunner()
                    .fromProjectDir("src/test-projects/SimpleSpringBootProject")
                    .appendBuildFileFromResource("/springBootBuild.gradle")
                    .appendBuildFileFromResource("/springBootUsage.gradle")
                    .build("launch4jTask")

        when:
            def result = customGradleRunner()
                    .appendBuildFileContents(GradleTaskGenerator.runExe("launch4jTask", "launch4jTask"))
                    .build("runExe")
        then:
            result.output.contains("hello Spring Boot3!")
    }
}
