package com.scarlatti.gradle.launch4j.gen2.details;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Thursday, 7/19/2018
 *
 * Details regarding locating the main class.
 */
public class MainClassConfigurationDetails {

    /**
     * Whether or not to automatically search for the main class.
     */
    private boolean findMainClass;

    /**
     * Specify the main class directly.
     * Has precedence over automatically locating main class.
     */
    private String mainClassName;

    public String getMainClassName() {
        return mainClassName;
    }

    public void setMainClassName(String mainClassName) {
        this.mainClassName = mainClassName;
    }

    public boolean getFindMainClass() {
        return findMainClass;
    }

    public void setFindMainClass(boolean findMainClass) {
        this.findMainClass = findMainClass;
    }
}
