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
    private FileResolutionStrategy launch4jResourcesDir;

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
        this.launch4jResourcesDir = other.launch4jResourcesDir;
    }


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

    public FileResolutionStrategy getLaunch4jResourcesDir() {
        return launch4jResourcesDir;
    }

    public void setLaunch4jResourcesDir(FileResolutionStrategy launch4jResourcesDir) {
        this.launch4jResourcesDir = launch4jResourcesDir;
    }
}
