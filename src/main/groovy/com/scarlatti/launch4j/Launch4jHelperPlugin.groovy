package com.scarlatti.launch4j

import com.scarlatti.launch4j.extension.Launch4JHelperExtension
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class Launch4jHelperPlugin implements Plugin<Project> {

    private Project project;

    public void apply(Project project) {
        this.project = project;
        // currently no default tasks?
        System.out.println("Applying Launch4jHelperPlugin...");
        applyLaunch4JHelperExtension();
    }

    private void applyLaunch4JHelperExtension() {
        System.out.println("Applying extension...");
        Launch4JHelperExtension extension = new Launch4JHelperExtension(project);
        project.ext {
            launch4JHelper = extension
        }
//        project.extensions.
        println "finished applying extension..."
    }
}
