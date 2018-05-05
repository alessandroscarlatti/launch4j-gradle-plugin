package com.scarlatti.test.util

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.util.GFileUtils

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Thursday, 5/3/2018
 */
class CreateTestProjectTask extends DefaultTask {

    String templateDir
    String eventualProjectDir
    String gradleFile

    @TaskAction
    void createTestProject() {

        println "Copying template project ${templateDir} to ${eventualProjectDir}"

        Files.createDirectories(Paths.get(eventualProjectDir))
        GFileUtils.copyDirectory(new File(templateDir), new File(eventualProjectDir))

        if (gradleFile != null) {
            appendTestGradleFile()
        }
    }

    private void appendTestGradleFile() {
        // copy (and overwrite) gradle file

        String buildFilePath = Paths.get(eventualProjectDir, "build.gradle").toString()
        String buildFileContents = new String(Files.readAllBytes(Paths.get(buildFilePath)))
        String testBuildFileContents = new String(Files.readAllBytes(Paths.get(gradleFile)))
        String newBuildFileContents = buildFileContents + new String(Character.LINE_SEPARATOR) + testBuildFileContents

        println "Appending test gradle file ${gradleFile} to ${buildFilePath}"
        Files.write(Paths.get(buildFilePath), newBuildFileContents.bytes)
    }
}
