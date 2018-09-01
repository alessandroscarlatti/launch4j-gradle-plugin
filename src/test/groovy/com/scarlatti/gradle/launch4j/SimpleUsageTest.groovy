package com.scarlatti.gradle.launch4j

import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import org.junit.rules.TestName
import spock.lang.Ignore
import spock.lang.Specification

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Saturday, 5/19/2018
 */
@Ignore
class SimpleUsageTest extends Specification {
    @Rule
    TemporaryFolder tempDir = new TemporaryFolder()

    @Rule
    TestName testName = new TestName()

    File buildFile

    def setup() {
        buildFile = tempDir.newFile('build.gradle')
    }

    def "exeTask runs successfully"() {
        given:
            buildFile << new Scanner(getClass().getResourceAsStream("/simpleUsage.gradle")).useDelimiter('\\Z').next()

        when:
            def result = GradleRunner.create()
                    .withProjectDir(tempDir.root)
                    .withArguments('build', '--stacktrace')
                    .withPluginClasspath()
                    .withDebug(true)
                    .build()

        then:
            println result.output
            result.task(":build").outcome == TaskOutcome.SUCCESS
    }
}
