package com.scarlatti.gradle.launch4j;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Tuesday, 5/29/2018
 */
public class DefaultIconConfigurationDelegate {
    private boolean generateIcon = true;

    public boolean isGenerateIcon() {
        return generateIcon;
    }

    public void setGenerateIcon(boolean generateIcon) {
        this.generateIcon = generateIcon;
    }
}
