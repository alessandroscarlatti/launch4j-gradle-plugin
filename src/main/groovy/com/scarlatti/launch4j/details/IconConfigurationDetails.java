package com.scarlatti.launch4j.details;

import com.scarlatti.launch4j.FileResolutionStrategy;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Thursday, 7/19/2018
 * <p>
 * Details regarding locating or generating an icon.
 */
public class IconConfigurationDetails implements AutoGeneratable {

    /**
     * Whether configuration of icon is enabled at all, whether by file or automatic generation.
     */
    private boolean enabled;

    /**
     * Strategy to invoke to locate an icon resource.
     * By default, a default resolutions strategy (this can use closure parameters, or
     * maybe a delegate so the default closure can be created with the extension).
     * If null, the plugin will not search for an icon resource.
     *
     * If invocation of the resolutionStrategy returns null, the plugin
     * will evaluate whether or not to auto-generate an icon.
     */
    private FileResolutionStrategy resolve;

    /**
     * Whether configuration of icon is enabled at all, whether by file or automatic generation.
     */
    private boolean autoGenerate;

    /**
     * The name to use when generating an icon.
     */
    private String text;

    public IconConfigurationDetails() {
    }

    /**
     * Copy constructor.
     *
     * @param other the details to copy.
     */
    public IconConfigurationDetails(IconConfigurationDetails other) {
        this.enabled = other.enabled;
        this.resolve = other.resolve;
        this.autoGenerate = other.autoGenerate;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
}
