package com.scarlatti.gradle.launch4j.gen2.details;

import com.scarlatti.gradle.launch4j.gen2.FileResolutionStrategy;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Thursday, 7/19/2018
 *
 * Details about locating or generating a manifest.
 */
public class ManifestConfigurationDetails implements AutoGeneratable {

    private boolean autoGenerate;

    /**
     * A specific resolve to use.  Takes precedence over auto-generation.
     */
    private FileResolutionStrategy resolve;

    public ManifestConfigurationDetails() {
    }

    /**
     * Copy constructor.
     * @param other the details to copy.
     */
    public ManifestConfigurationDetails(ManifestConfigurationDetails other) {
        this.autoGenerate = other.autoGenerate;
        this.resolve = other.resolve;
    }

    @Override
    public boolean getAutoGenerate() {
        return autoGenerate;
    }

    @Override
    public void setAutoGenerate(boolean autoGenerate) {
        this.autoGenerate = autoGenerate;
    }

    public FileResolutionStrategy getResolve() {
        return resolve;
    }

    public void setResolve(FileResolutionStrategy resolve) {
        this.resolve = resolve;
    }
}
