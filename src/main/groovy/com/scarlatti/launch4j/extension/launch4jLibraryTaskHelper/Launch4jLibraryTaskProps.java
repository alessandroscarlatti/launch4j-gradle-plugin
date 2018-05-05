package com.scarlatti.launch4j.extension.launch4jLibraryTaskHelper;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Saturday, 5/5/2018
 *
 * All paths should be in compliance with the specifications in the Launch4j documentation.
 *
 * Each property can be distinguished as set or not set.
 */
public class Launch4jLibraryTaskProps {
    private Property<String> outputDir = new Property<>();
    private Property<String> iconPath = new Property<>();
    private Property<String> manifestPath = new Property<>();
    private Property<String> exeName = new Property<>();

    public Property<String> getOutputDir() {
        return outputDir;
    }

    public Property<String> getIconPath() {
        return iconPath;
    }

    public Property<String> getManifestPath() {
        return manifestPath;
    }

    public Property<String> getExeName() {
        return exeName;
    }
}
