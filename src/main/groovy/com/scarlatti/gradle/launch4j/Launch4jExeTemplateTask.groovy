package com.scarlatti.gradle.launch4j

import edu.sc.seis.launch4j.tasks.Launch4jLibraryTask
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Saturday, 5/19/2018
 *
 * Create a launch4j exe task by convention.
 *
 * properties include:
 *
 * resourcesDir
 * (looking for:)
 * - icon.ico
 * - splash.bmp
 * - manifest.mf / manifest.manifest / manifest / {exeName}.manifest
 * - launch4j.properties
 *
 * and the following, which all override whatever is in the resourcesDir:
 * icon (path)
 * splash (path)
 * manifest (path)
 * config (path)
 *
 * All relative paths are evaluated relative to the project dir.
 */
class Launch4jExeTemplateTask extends DefaultTask {
    private String resourcesDir = ''
    private String icon = ''
    private String splash = ''
    private String manifest = ''
    private String config = ''

    private Launch4jLibraryTask launch4j

    Launch4jExeTemplateTask() {
        launch4j = project.tasks.create(generateLaunch4jTaskName(name), Launch4jLibraryTask)
    }

    private String generateLaunch4jTaskName(String templateTaskName) {
        return "launch4j${templateTaskName}"
    }

    @TaskAction
    void run() {
        // do nothing, because this task triggers the launch4j by dependency.
    }

    String getResourcesDir() {
        return resourcesDir
    }

    void setResourcesDir(String resourcesDir) {
        this.resourcesDir = resourcesDir
    }

    String getIcon() {
        return icon
    }

    void setIcon(String icon) {
        this.icon = icon
    }

    String getSplash() {
        return splash
    }

    void setSplash(String splash) {
        this.splash = splash
    }

    String getManifest() {
        return manifest
    }

    void setManifest(String manifest) {
        this.manifest = manifest
    }

    String getConfig() {
        return config
    }

    void setConfig(String config) {
        this.config = config
    }
}
