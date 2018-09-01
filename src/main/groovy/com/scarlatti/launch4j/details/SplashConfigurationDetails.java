package com.scarlatti.launch4j.details;

import com.scarlatti.launch4j.FileResolutionStrategy;

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
    private FileResolutionStrategy resolve;

    /**
     * The text to use when generating a splash.
     */
    private String text;

    public SplashConfigurationDetails() {
    }


    /**
     * Copy constructor.
     * @param other the details to copy.
     */
    public SplashConfigurationDetails(SplashConfigurationDetails other) {
        this.autoGenerate = other.autoGenerate;
        this.resolve = other.resolve;
        this.text = other.text;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
