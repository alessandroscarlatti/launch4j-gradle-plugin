package com.scarlatti.test.util

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.GradleBuild

import java.nio.file.Paths

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Thursday, 5/3/2018
 */
class TestProjectsPlugin implements Plugin<Project> {

    private Project project

    @Override
    void apply(Project project) {
        // read a list of test project dirs from the
        // gradle project property
        this.project = project

        createTestPluginProjectTask()
//        createTestPluginProjectTasks()
    }

//    private void createConvention() {
//        project.convention.plugins.put("testProjects", new TestGradleBuildsTask(project))
//    }

    private void createTestPluginProjectTasks() {
        List<String> testProjectDirs = getTestProjectDirs()
        println "Considering testProjectDirs ${testProjectDirs}"

        for (String dir : testProjectDirs) {
            createTestGradleBuildTask(dir)
        }
    }

    private List<String> getTestProjectDirs() {
        Object rawTestProjectDirs = project.gradle.testProjectDirs // project.getProperties().get("testProjectDirs")

        if (!(rawTestProjectDirs instanceof List))
            throw new IllegalArgumentException("testProjectDirs property should be a list of test project dirs (strings).")

        return (List<String>) rawTestProjectDirs
    }

    private void createTestPluginProjectTask() {
        project.tasks.create("testProjects", TestGradleBuildsTask.class) { task ->
            task.group = 'verification'
            task.description = 'Tests this plugin in test projects.'
            task.mustRunAfter(project.tasks.getByName("build"))
            task.outputs.upToDateWhen {false}
        }

        project.tasks.getByName("test").finalizedBy(project.tasks.getByName("testProjects"))
    }

    private void createTestGradleBuildTask(String rawDir) {

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
                    pluginArtifactName: project.properties.pluginArtifactName
            ])
        }

        project.tasks.test.finalizedBy(project.tasks.getByName(taskName))
    }
}
