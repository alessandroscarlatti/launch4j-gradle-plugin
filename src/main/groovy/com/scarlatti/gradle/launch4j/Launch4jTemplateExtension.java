package com.scarlatti.gradle.launch4j;

import org.gradle.api.Project;

import java.io.File;

public class Launch4jTemplateExtension {
    private Project project;
    private File baseResourcesDir;
    private String taskGroup;

    public Launch4jTemplateExtension(Project project) {
        this.project = project;
        baseResourcesDir = getDefaultBaseResourceDir();
    }

    public File getBaseResourcesDir() {
        return baseResourcesDir;
    }

    public void setBaseResourcesDir(File baseResourcesDir) {
        this.baseResourcesDir = baseResourcesDir;
    }

    public void setBaseResourceDir(String baseResourceDir) {
        this.baseResourcesDir = project.file(baseResourceDir);
    }

    public String getTaskGroup() {
        return taskGroup;
    }

    public void setTaskGroup(String taskGroup) {
        this.taskGroup = taskGroup;
    }

    private File getDefaultBaseResourceDir() {
        return project.file("exe");
    }

    private String getDefaultGroup() {
        return "exe";
    }

}