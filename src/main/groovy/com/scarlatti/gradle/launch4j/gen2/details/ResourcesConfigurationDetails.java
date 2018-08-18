package com.scarlatti.gradle.launch4j.gen2.details;

import com.scarlatti.gradle.launch4j.gen2.FileResolutionStrategy;

import java.io.File;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Thursday, 7/19/2018
 * <p>
 * Details regarding locating or generating a splash.
 */
public class ResourcesConfigurationDetails {

    private File resourcesDir;
    private String launch4jPropertiesFileName;
    private String iconFileName;
    private String splashFileName;
    private String manifestFileName;
    private FileResolutionStrategy launch4jPropertiesResolutionStrategy;
    private FileResolutionStrategy iconResolutionStrategy;
    private FileResolutionStrategy splashResolutionStrategy;
    private FileResolutionStrategy manifestResolutionStrategy;

    public ResourcesConfigurationDetails() {
    }

    /**
     * Copy constructor.
     *
     * @param other the instance to copy.
     */
    public ResourcesConfigurationDetails(ResourcesConfigurationDetails other) {
        this.resourcesDir = other.resourcesDir;
        this.launch4jPropertiesFileName = other.launch4jPropertiesFileName;
        this.iconFileName = other.iconFileName;
        this.splashFileName = other.splashFileName;
        this.manifestFileName = other.manifestFileName;
        this.launch4jPropertiesResolutionStrategy = other.launch4jPropertiesResolutionStrategy;
        this.iconResolutionStrategy = other.iconResolutionStrategy;
        this.splashResolutionStrategy = other.splashResolutionStrategy;
        this.manifestResolutionStrategy = other.manifestResolutionStrategy;
    }

    // set file name?
    // use current resource directory for path
    //
    // set file path?
    // keep all other files the same.
    //
    // set resourcesDir?
    // keep any paths configured using name only.
    // replace paths configured by path


    public File getResourcesDir() {
        return resourcesDir;
    }

    public void setResourcesDir(File resourcesDir) {
        this.resourcesDir = resourcesDir;
    }

    public String getLaunch4jPropertiesFileName() {
        return launch4jPropertiesFileName;
    }

    public void setLaunch4jPropertiesFileName(String launch4jPropertiesFileName) {
        this.launch4jPropertiesFileName = launch4jPropertiesFileName;
    }

    public String getIconFileName() {
        return iconFileName;
    }

    public void setIconFileName(String iconFileName) {
        this.iconFileName = iconFileName;
    }

    public String getSplashFileName() {
        return splashFileName;
    }

    public void setSplashFileName(String splashFileName) {
        this.splashFileName = splashFileName;
    }

    public String getManifestFileName() {
        return manifestFileName;
    }

    public void setManifestFileName(String manifestFileName) {
        this.manifestFileName = manifestFileName;
    }

    public FileResolutionStrategy getLaunch4jPropertiesResolutionStrategy() {
        return launch4jPropertiesResolutionStrategy;
    }

    public void setLaunch4jPropertiesResolutionStrategy(FileResolutionStrategy launch4jPropertiesResolutionStrategy) {
        this.launch4jPropertiesResolutionStrategy = launch4jPropertiesResolutionStrategy;
    }
}
