package com.scarlatti.gradle.launch4j.gen2.task;

import com.scarlatti.gradle.launch4j.gen2.Launch4jHelperExtension;
import com.scarlatti.gradle.launch4j.gen2.details.SplashConfigurationDetails;
import com.scarlatti.util.ImageGenerator;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.nio.file.Paths;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Tuesday, 8/14/2018
 */
public class GenerateSplashTask extends DefaultTask {

    private String destination;
    private SplashConfigurationDetails details;

    public GenerateSplashTask() {
        details = Launch4jHelperExtension.defaultSplashConfigDtls();
    }

    @TaskAction
    public void generateSplash() {
        ImageGenerator.generateSplashFileForStringHash(Paths.get(destination), details.getName());
    }
}
