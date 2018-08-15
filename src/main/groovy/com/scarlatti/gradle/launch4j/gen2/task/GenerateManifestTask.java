package com.scarlatti.gradle.launch4j.gen2.task;

import com.scarlatti.gradle.launch4j.gen2.Launch4jHelperExtension;
import com.scarlatti.gradle.launch4j.gen2.details.ManifestConfigurationDetails;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Tuesday, 8/14/2018
 */
public class GenerateManifestTask extends DefaultTask {

    private String destination;
    private ManifestConfigurationDetails details;

    @TaskAction
    public void generateManifest() {
        details = Launch4jHelperExtension.defaultManifestConfigDtls();
    }
}
