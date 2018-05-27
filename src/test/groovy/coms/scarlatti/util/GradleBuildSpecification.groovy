package coms.scarlatti.util

import org.junit.Rule
import org.junit.rules.TestName
import spock.lang.Specification

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Friday, 5/25/2018
 */
class GradleBuildSpecification extends Specification {

    @Rule
    protected TestName testNameRule = new TestName()

    protected String getTestName() {
        return testNameRule.methodName
            .replace(":", "_")
            .replace("#", "_")
    }

    protected CustomGradleRunner customGradleRunner() {
        return CustomGradleRunner.create(testName)
            .withPluginClasspath()
            .withDebug(true)
    }
}
