package com.scarlatti.distribution

import com.scarlatti.distribution.extension.DistributionHelperExtension
import com.scarlatti.distribution.extension.Launch4JHelperExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.execution.TaskExecutionGraph
import org.gradle.api.tasks.bundling.Tar

class Launch4jHelperPlugin implements Plugin<Project> {

    private Project project

    private final String LAUNCH4J_HELPER_EXTENSION_NAME = "launch4jHelper"
    private final String DISTRIBUTION_HELPER_EXTENSION_NAME = "distributionHelper"

    void apply(Project project) {
        this.project = project

//        System.out.println("Applying Launch4jHelperPlugin...")
//        applyLaunch4jHelperExtension()
//        applyDistributionHelperExtension()
//
//        applyDistributionPluginConfigurations()
    }

    private void applyLaunch4jHelperExtension() {
        Launch4JHelperExtension extension = new Launch4JHelperExtension(project)
        project.ext.set(LAUNCH4J_HELPER_EXTENSION_NAME, extension)
    }

    private void applyDistributionHelperExtension() {
        DistributionHelperExtension extension = new DistributionHelperExtension(project)
        project.ext.set(DISTRIBUTION_HELPER_EXTENSION_NAME, extension)
    }

    private void applyDistributionPluginConfigurations() {
        // disable distribution tar tasks
        project.gradle.taskGraph.whenReady { TaskExecutionGraph graph ->
            graph.allTasks.each { Task task ->
                if (task instanceof Tar && task.name.toLowerCase().contains("dist") && task.group == ("distribution")) {
                    task.enabled = false
                }
            }
        }

    }
}
