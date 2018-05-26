package com.scarlatti.gradle.launch4j.gradle.launch4j

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Wednesday, 5/23/2018
 */
class Launch4jIntegrationPlugin implements Plugin<Project> {
    private Project project
    private static final LAUNCH4J_TEMPLATE_EXTENSION_NAME = "launch4jTemplate"

    @Override
    void apply(Project project) {
        this.project = project
        addLaunch4jTemplateExtension()
        applyLaunch4jPluginIfNecessary()
    }

    private void addLaunch4jTemplateExtension() {
        project.extensions.add(LAUNCH4J_TEMPLATE_EXTENSION_NAME, new Launch4jTemplateExtension(project))
    }

    private void applyLaunch4jPluginIfNecessary() {
        if (project.plugins.findPlugin("launch4j") == null) {
            project.apply(plugin: 'launch4j')
        }
    }
}
