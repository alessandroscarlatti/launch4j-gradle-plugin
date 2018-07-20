package com.scarlatti.gradle.launch4j.gen2.details;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Thursday, 7/19/2018
 * <p>
 * Describes an instance that may be configured to
 * permit auto-generation.
 */
public interface AutoGeneratable {
    /**
     * @return Is this instance configured to auto-generate?
     */
    boolean getAutoGenerate();

    /**
     * Instruct this instance to permit auto-generate.
     * @param autoGenerate whether or not to permit auto-generation.
     */
    void setAutoGenerate(boolean autoGenerate);
}
