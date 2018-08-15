package com.scarlatti.gradle.launch4j.gen2.task;

import com.scarlatti.gradle.launch4j.gen2.Launch4jHelperExtension;
import com.scarlatti.gradle.launch4j.gen2.details.IconConfigurationDetails;
import com.scarlatti.util.ImageGenerator;
import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.nio.file.Paths;

import static groovy.lang.Closure.DELEGATE_FIRST;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Tuesday, 8/14/2018
 */
public class GenerateIconTask extends DefaultTask {

    @OutputFile
    private File destination;
    private IconConfigurationDetails details;

    private static final String NAME_INPUT = "baseIconName";

    public GenerateIconTask() {
        details = Launch4jHelperExtension.defaultIconConfigDtls();
    }

    public void details(@DelegatesTo(value = IconConfigurationDetails.class, strategy = DELEGATE_FIRST) Closure config) {
        config.setDelegate(details);
        config.setResolveStrategy(DELEGATE_FIRST);
        config.call();

        // update the inputs...
        updateInputs();

        // todo but how to catch updating the inputs after the details#setName() may have been called?
    }

    private void updateInputs() {
        getInputs().property(NAME_INPUT, details.getName());
    }

    @TaskAction
    public void generateIcon() {
        // todo implement whether to generate?
        ImageGenerator.generateIconFileForStringHash(Paths.get(destination.getAbsolutePath()), details.getName());
    }

    public File getDestination() {
        return destination;
    }

    public void setDestination(File destination) {
        this.destination = destination;
    }

    public IconConfigurationDetails getDetails() {
        return details;
    }

    public void setDetails(IconConfigurationDetails details) {
        this.details = details;
    }
}
