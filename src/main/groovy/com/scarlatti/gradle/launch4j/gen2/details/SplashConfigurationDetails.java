package com.scarlatti.gradle.launch4j.gen2.details;

import java.io.File;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Thursday, 7/19/2018
 *
 * Details regarding locating or generating a splash.
 */
public class SplashConfigurationDetails implements AutoGeneratable {

    private boolean autoGenerate;

    /**
     * A specific location to use.  Takes precedence over auto-generation;
     */
    private File location;

    public SplashConfigurationDetails() {
    }

    /**
     * Copy constructor.
     * @param other the details to copy.
     */
    public SplashConfigurationDetails(SplashConfigurationDetails other) {
        this.autoGenerate = other.autoGenerate;
        this.location = other.location;
    }

    @Override
    public boolean getAutoGenerate() {
        return autoGenerate;
    }

    @Override
    public void setAutoGenerate(boolean autoGenerate) {
        this.autoGenerate = autoGenerate;
    }

    public File getLocation() {
        return location;
    }

    public void setLocation(File location) {
        this.location = location;
    }
}
