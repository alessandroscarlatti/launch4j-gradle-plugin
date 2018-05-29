package com.scarlatti.gradle.launch4j

import edu.sc.seis.launch4j.tasks.Launch4jLibraryTask
import org.gradle.api.DefaultTask

import java.nio.file.Paths

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Saturday, 5/19/2018
 *
 * Create a launch4jTask exe task by convention.
 *
 * properties include:
 *
 * resourcesDir
 * (looking for:)
 * - icon.ico
 * - splash.bmp
 * - manifest.mf / manifest.manifest / manifest / {exeName}.manifest
 * - launch4jTask.properties
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

    // TODO
    // so we would need to make the resource dirs PLURAL
    // and make them outputs (since they are used as inputs for the launch4j task)
    // (wrong: that way they don't have to exist)



    private File resourcesDir
    private String exeName = name

    private Launch4jLibraryTask launch4jTask
    private Launch4jLibraryTaskConfigurer launch4jTaskConfigurer

    Launch4jTemplateTask() {
        resourcesDir = evaluateDefaultBaseResourcesDir()
        group = evaluateDefaultTaskGroup()
        launch4jTask = project.tasks.create(generateLaunch4jTaskName(name), Launch4jLibraryTask)
        this.dependsOn(launch4jTask)
        launch4jTaskConfigurer = new Launch4jLibraryTaskConfigurer(launch4jTask, name)
        launch4jTaskConfigurer.configureExeName(exeName)
        launch4jTaskConfigurer.configureDependencies()

        // configure from the base resources dir (if it exists at this time)
//        launch4jTask.inputs.dir(resourcesDir).optional()
        if (resourcesDir.exists()) {
            launch4jTaskConfigurer.configureFromResourcesDir(resourcesDir.absolutePath)
        }

        setupPrintExeLocation()
    }

    void config(@DelegatesTo(Launch4jLibraryTask) Closure closure) {
        closure.delegate = launch4jTask
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure()
    }

    void manifest(@DelegatesTo(ManifestConfigurationDelegate) Closure closure) {
        ManifestConfigurationDelegate config = new ManifestConfigurationDelegate()
        closure.delegate = config
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure()

        // the information configured here needs to be applied
        // before the resourceDir is applied so that anything in the resourceDir
        // will override this.
        launch4jTaskConfigurer.configureGeneratedManifest(config.base.buildRawManifest())
    }

    private static String generateLaunch4jTaskName(String templateTaskName) {
        return "${templateTaskName}_launch4jTask"
    }

    void setupPrintExeLocation() {
        String exeFileName = launch4jTask.outfile
        String relativeExeDir = launch4jTask.outputDir
        String buildDir = project.buildDir.absolutePath
        String absoluteExeDir = Paths.get(buildDir, relativeExeDir).toString()

        launch4jTask.doLast {
            println "${exeFileName} available at ${absoluteExeDir}"
        }
    }

    private File evaluateDefaultBaseResourcesDir() {
        return project.extensions.getByType(Launch4jTemplateExtension).baseResourcesDir
    }

    private String evaluateDefaultTaskGroup() {
        return project.extensions.getByType(Launch4jTemplateExtension).taskGroup
    }

    void setResourcesDir(String dir) {
        setResourcesDir(project.file(dir))
    }

    File getResourcesDir() {
        return resourcesDir
    }

    void setResourcesDir(File resourcesDir) {
        launch4jTaskConfigurer.configureFromResourcesDir(resourcesDir.absolutePath)
        this.resourcesDir = resourcesDir
    }

    String getExeName() {
        return exeName
    }

    void setExeName(String exeName) {
        this.exeName = exeName
    }

    private Launch4jLibraryTask getExeTask() {
        return launch4jTask
    }
}
