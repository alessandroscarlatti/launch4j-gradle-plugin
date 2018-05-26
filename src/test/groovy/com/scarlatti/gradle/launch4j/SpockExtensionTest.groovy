package com.scarlatti.gradle.launch4j

import coms.scarlatti.util.GradleBuildSpecification

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
            def result = newDefaultGradleRunner()
                    .withBuildFileContents(buildFileContents)
                    .withTask("silly")
                    .build()
        then:
            result.output.contains("what do you know")
    }

    def "CustomGradleRunner builds test project properly from resource"() {
        when:
            def result = newDefaultGradleRunner()
                    .withBuildFileFromResource("/util/silly.gradle")
                    .withTask("silly")
                    .build()
        then:
            result.output.contains("what do you know")
    }

    def "CustomGradleRunner builds test project properly from source dir"() {
        when:
            def result = newDefaultGradleRunner()
                    .fromProjectDir("/ideIntegration")
                    .withBuildFileFromResource("/util/silly.gradle")
                    .withTask("silly")
                    .build()
        then:
            result.output.contains("what do you know")
    }

}
