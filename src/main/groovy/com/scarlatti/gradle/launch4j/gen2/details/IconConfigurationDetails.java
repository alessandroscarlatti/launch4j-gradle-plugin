package com.scarlatti.gradle.launch4j.gen2.details;

import groovy.lang.Closure;

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
     *
     * todo But at runtime, we need to be able to tell the difference between
     * no specified resolution strategy and desire for the plugin to implement
     * the default resolutionStrategy?
     */
    private Closure<File> iconResolutionStrategy;

    /**
     * Whether configuration of icon is enabled at all, whether by file or automatic generation.
     */
    private boolean autoGenerate;

    /**
     * The name to use when generating an icon.
     */
    private String name;

    /**
     * A specific location for the icon.  Takes precedence over auto-generation.
     * A null value indicates for the plugin to attempt to find a resource,
     * unless {@code searchForResource} is false.
     *
     * todo the accessor for this could actually use the iconResolutionStrategy
     * do we actually ever need to retrieve this "File" value from this config?
     */
    private File location;

    public IconConfigurationDetails() {
    }

    /**
     * Copy constructor.
     *
     * @param other the details to copy.
     */
    public IconConfigurationDetails(IconConfigurationDetails other) {
        this.enabled = other.enabled;
        this.autoGenerate = other.autoGenerate;
        this.name = other.name;
        this.location = other.location;
    }

    public File getLocation() {
        return location;
    }

    public void setLocation(File location) {
        this.location = location;
    }

    @Override
    public boolean getAutoGenerate() {
        return autoGenerate;
    }

    @Override
    public void setAutoGenerate(boolean autoGenerate) {
        this.autoGenerate = autoGenerate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
