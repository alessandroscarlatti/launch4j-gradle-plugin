package com.scarlatti.gradle.launch4j.gen2.task;

import com.scarlatti.gradle.launch4j.gen2.Launch4jHelperExtension;
import com.scarlatti.gradle.launch4j.gen2.details.ManifestConfigurationDetails;
import com.scarlatti.gradle.launch4j.gen2.details.SplashConfigurationDetails;
import com.scarlatti.util.ImageGenerator;
import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.nio.file.Paths;

import static groovy.lang.Closure.DELEGATE_FIRST;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Tuesday, 8/14/2018
 */
public class SupplySplashTask extends DefaultTask {

    private String destination;
    private SplashConfigurationDetails details;

    public SupplySplashTask() {
        details = Launch4jHelperExtension.defaultSplashConfigDtls();
    }

    public void details(@DelegatesTo(value = ManifestConfigurationDetails.class, strategy = DELEGATE_FIRST) Closure config) {
        config.setDelegate(details);
        config.setResolveStrategy(DELEGATE_FIRST);
        config.call();
    }

    @TaskAction
    public void generateSplash() {
        ImageGenerator.generateSplashFileForStringHash(Paths.get(destination), details.getName());
    }
}
