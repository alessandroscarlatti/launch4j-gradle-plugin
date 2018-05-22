package com.scarlatti.gradle.tasks

import com.scarlatti.gradle.launch4j.Launch4jLibraryTaskConfigurer
import edu.sc.seis.launch4j.tasks.Launch4jLibraryTask
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.Optional

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Saturday, 5/19/2018
 *
 * Create a exeTask exe task by convention.
 *
 * properties include:
 *
 * resourcesDir
 * (looking for:)
 * - icon.ico
 * - splash.bmp
 * - manifest.mf / manifest.manifest / manifest / {exeName}.manifest
 * - exeTask.properties
 *
 * DAY 2...
 * and the following, which all override whatever is in the resourcesDir:
 * icon (path)
 * splash (path)
 * manifest (path)
 * spec (path)
 *
 * All relative paths are evaluated relative to the project dir.
 */
class Launch4jTemplateTask extends DefaultTask {

    @Optional
    @InputDirectory
    private File resourcesDir = project.file('exe')

    @Optional
    @Input
    private String exeName = name

    private Launch4jLibraryTask exeTask
    private Launch4jLibraryTaskConfigurer exeTaskConfigurer

    private List<Closure> configs = []

    Launch4jTemplateTask() {
        project.afterEvaluate(this.&setupConfigurations)
    }

    void setupConfigurations() {
        exeTask = project.tasks.create(generateLaunch4jTaskName(name), Launch4jLibraryTask)
        exeTaskConfigurer = new Launch4jLibraryTaskConfigurer(exeName, exeTask)
        applyConfigurations()
        applyLaunch4jConfigurations()
        configureDependencies()
    }

    void applyConfigurations() {
        exeTaskConfigurer.configureFromExeName(exeName)
        exeTaskConfigurer.configureFromResourcesDir(resourcesDir.absolutePath)
    }

    void applyLaunch4jConfigurations() {
        for (Closure closure : configs) {
            closure.delegate = exeTask
            closure.resolveStrategy = Closure.DELEGATE_FIRST
            closure()
        }
    }

    void configureDependencies() {
        dependsOn exeTask
    }

    private static String generateLaunch4jTaskName(String templateTaskName) {
        return "exeTask${templateTaskName}"
    }

    void config(@DelegatesTo(Launch4jLibraryTask) Closure closure) {
        configs.add(closure)
    }

    void setResourcesDir(String dir) {
        setResourcesDir(project.file(dir))
    }

    File getResourcesDir() {
        return resourcesDir
    }

    void setResourcesDir(File resourcesDir) {
        this.resourcesDir = resourcesDir
    }

    String getExeName() {
        return exeName
    }

    void setExeName(String exeName) {
        this.exeName = exeName
    }

    private Launch4jLibraryTask getExeTask() {
        return exeTask
    }
}
