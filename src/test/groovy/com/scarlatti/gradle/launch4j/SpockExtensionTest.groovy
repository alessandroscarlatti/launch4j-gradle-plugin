package com.scarlatti.gradle.launch4j

import com.scarlatti.util.GradleBuildSpecification

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Friday, 5/25/2018
 */
class SpockExtensionTest extends GradleBuildSpecification {

    def "CustomGradleRunner builds test project properly"() {
        setup:
            String buildFileContents = """
                task silly() {
                    println "what do you know"
                }
            """

        when:
            def result = customGradleRunner()
                    .withBuildFileContents(buildFileContents)
                    .withTask("silly")
                    .build()
        then:
            result.output.contains("what do you know")
    }

    def "CustomGradleRunner builds test project properly from resource"() {
        when:
            def result = customGradleRunner()
                    .withBuildFileFromResource("/util/silly.gradle")
                    .withTask("silly")
                    .build()
        then:
            result.output.contains("what do you know")
    }

    def "CustomGradleRunner builds test project properly from source dir"() {
        when:
            def result = customGradleRunner()
                    .fromProjectDir("/src/test-projects/SimpleProjectTest")
                    .appendBuildFileFromResource("/util/silly.gradle")
                    .withTask("silly")
                    .build()
        then:
            result.output.contains("what do you know")
    }

    def "CustomGradleRunner can expect a failure"() {
        expect:
            customGradleRunner()
                    .fromProjectDir("/src/test-projects/SimpleProjectTest")
                    .appendBuildFileFromResource("/util/silly.gradle")
                    .withTask("doesNotExist")
                    .buildAndFail()
    }
}
