package com.scarlatti.launch4j;

import org.gradle.api.Project;

import java.io.File;

public class Launch4jTemplateExtension {
    private Project project;
    private File baseResourceDir;

    public Launch4jTemplateExtension(Project project) {
        this.project = project;
        baseResourceDir = getDefaultBaseResourceDir();
    }

    public File getBaseResourceDir() {
        return baseResourceDir;
    }

    public void setBaseResourceDir(File baseResourceDir) {
        this.baseResourceDir = baseResourceDir;
    }

    public void setBaseResourceDir(String baseResourceDir) {
        this.baseResourceDir = project.file(baseResourceDir);
    }

    private File getDefaultBaseResourceDir() {
        return project.file("exe");
    }

}