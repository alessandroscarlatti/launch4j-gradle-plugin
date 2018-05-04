package com.scarlatti.test.util

import org.gradle.api.DefaultTask
import org.gradle.api.Task
import org.gradle.api.tasks.GradleBuild

import java.nio.file.Paths

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Thursday, 5/3/2018
 */
class TestGradleBuildsTask extends DefaultTask {

    void test(String dir) {
        println "creating task for dir ${dir}"
        createTestGradleBuildTask(dir)
    }

    void test(String dir, @DelegatesTo(GradleBuild) Closure closure) {
        println "creating task for dir ${dir}"
        Task task = createTestGradleBuildTask(dir)
        closure.setDelegate(task)
        closure()
    }

    private Task createTestGradleBuildTask(String rawDir) {

        String dirTaskName = Paths.get(rawDir).toString().replace(File.separatorChar, '_' as char)
        String taskName = "test_" + dirTaskName
        println "Creating task named ${taskName}"

        project.tasks.create(taskName, GradleBuild.class) { build ->
            build.mustRunAfter(project.tasks.getByName('build'))
            build.dir = Paths.get(project.projectDir.absolutePath, rawDir).toString()
            build.tasks = ['testPluginProject']
            build.startParameter.projectProperties.putAll([
                    group:  project.group,
                    version: project.version,
                    repoDir: Paths.get(project.projectDir.absolutePath, project.properties.pluginRepoDir),
                    pluginName: project.properties.pluginName
            ])
        }

        project.tasks.test.finalizedBy(project.tasks.getByName(taskName))

        return project.tasks.getByName(taskName)
    }
}
