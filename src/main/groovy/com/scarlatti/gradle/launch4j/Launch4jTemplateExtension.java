package com.scarlatti.gradle.launch4j;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import org.gradle.api.Project;

import java.io.File;

public class Launch4jTemplateExtension {
    private Project project;
    private File baseResourcesDir;
    private String taskGroup;

    private DefaultIconConfigurationDelegate defaultIconConfigurationDelegate;
    private DefaultSplashConfigurationDelegate defaultSplashConfigurationDelegate;
    private DefaultManifestConfigurationDelegate defaultManifestConfigurationDelegate;

    public Launch4jTemplateExtension(Project project) {
        this.project = project;
        baseResourcesDir = getDefaultBaseResourceDir();
        taskGroup = getDefaultTaskGroup();

        defaultIconConfigurationDelegate = new DefaultIconConfigurationDelegate();
        defaultSplashConfigurationDelegate = new DefaultSplashConfigurationDelegate();
        defaultManifestConfigurationDelegate = new DefaultManifestConfigurationDelegate();
    }

    public DefaultIconConfigurationDelegate getIcon() {
        return defaultIconConfigurationDelegate;
    }

    public DefaultSplashConfigurationDelegate getSplash() {
        return defaultSplashConfigurationDelegate;
    }

    public DefaultManifestConfigurationDelegate getManifest() {
        return defaultManifestConfigurationDelegate;
    }

    public void icon(@DelegatesTo(DefaultIconConfigurationDelegate.class) Closure closure) {
        closure.setDelegate(defaultIconConfigurationDelegate);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
    }

    public void splash(@DelegatesTo(DefaultSplashConfigurationDelegate.class) Closure closure) {
        closure.setDelegate(defaultSplashConfigurationDelegate);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
    }

    public void manifest(@DelegatesTo(DefaultManifestConfigurationDelegate.class) Closure closure) {
        closure.setDelegate(defaultManifestConfigurationDelegate);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
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

    private String getDefaultTaskGroup() {
        return "exe";
    }

}