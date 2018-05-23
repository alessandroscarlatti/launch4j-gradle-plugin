package com.scarlatti

import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Saturday, 5/19/2018
 */
class SimpleUsagesTest extends Specification {
    @Rule TemporaryFolder tempDir = new TemporaryFolder()
    File buildFile

    def setup() {
        buildFile = tempDir.newFile('build.gradle')
    }

    def "exeTask runs successfully"() {
        given:
            buildFile << """
            import com.scarlatti.gradle.tasks.Launch4jTemplateTask

            buildscript {
                repositories {jcenter()}
                dependencies.classpath 'edu.sc.seis.gradle:launch4j:2.4.2'
            }
            
            plugins {
                id 'launch4j'
                id 'com.scarlatti.release'
            }

            task launch4jTask(type: Launch4jTemplateTask) {
                resourcesDir = 'asdf'
            }
        """

        when:
            def result = GradleRunner.create()
                    .withProjectDir(tempDir.root)
                    .withArguments('launch4jTask', '--stacktrace')
                    .withPluginClasspath()
                    .withDebug(true)
                    .build()

        then:
            println result.output
            result.task(":launch4jTask").outcome == TaskOutcome.SUCCESS
    }
}
