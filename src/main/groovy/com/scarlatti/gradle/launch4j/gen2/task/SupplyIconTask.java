package com.scarlatti.gradle.launch4j.gen2.task;

import com.scarlatti.gradle.launch4j.gen2.details.IconConfigurationDetails;
import com.scarlatti.util.ImageGenerator;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.util.function.Supplier;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Tuesday, 8/14/2018
 */
public class SupplyIconTask extends DefaultTask {

    @OutputFile
    private File destination;

    @InputFile
    private File icon;

    /**
     * this task should be blind to the HelperTask
     *
     * this won't be invoked during this task,
     * but it should be invoked before the task inputs/outputs are evaluated.
     *
     * this probably means that we need to encapsulate
     * the FileResolutionStrategy inside a {@link Supplier<File>}
     */
    private Supplier<File> resolve;

    /**
     * Whether configuration of icon is enabled at all, whether by file or automatic generation.
     */
    private boolean autoGenerate;

    /**
     * The name to use when generating an icon.
     */
    private String text;

    /**
     * Configure this task from a PropertiesConfigurationDetails instance.
     * Called after this task has been created.
     */
    public void configureFromIconConfigDtls(IconConfigurationDetails details) {
        autoGenerate = details.getAutoGenerate();
        text = details.getText();
    }

    /**
     * Move the input icon file to the destination,
     * or if it does not exist, generate an icon.
     */
    @TaskAction
    public void generateIcon() {
        // todo implement what to name the icon once it is in the launch4j resources dir
        ImageGenerator.generateIconFileForStringHash(destination.toPath(), "icon.ico");
    }

    public File getDestination() {
        return destination;
    }

    public void setDestination(File destination) {
        this.destination = destination;
    }

    public File getIcon() {
        return icon;
    }

    public void setIcon(File icon) {
        this.icon = icon;
    }

    public Supplier<File> getResolve() {
        return resolve;
    }

    public void setResolve(Supplier<File> resolve) {
        this.resolve = resolve;
    }

    public boolean getAutoGenerate() {
        return autoGenerate;
    }

    public void setAutoGenerate(boolean autoGenerate) {
        this.autoGenerate = autoGenerate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
