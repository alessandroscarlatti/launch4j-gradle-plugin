package com.scarlatti.launch4j.details;

import com.scarlatti.launch4j.FileResolutionStrategy;

import java.io.File;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Thursday, 7/19/2018
 * <p>
 * Details regarding locating or generating an icon.
 */
public class PropertiesConfigurationDetails {

    /**
     * Whether configuration of icon is enabled at all, whether by file or automatic generation.
     */
    private boolean enabled;

    private FileResolutionStrategy resolve;

    public PropertiesConfigurationDetails() {
    }

    /**
     * Copy constructor.
     *
     * @param other the details to copy.
     */
    public PropertiesConfigurationDetails(PropertiesConfigurationDetails other) {
        this.enabled = other.enabled;
        this.resolve = other.resolve;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public FileResolutionStrategy getResolve() {
        return resolve;
    }

    public void setResolve(FileResolutionStrategy resolve) {
        this.resolve = resolve;
    }

    public void setResolutionStrategy(File file) {
        this.resolve = (task) -> file;
    }
}
