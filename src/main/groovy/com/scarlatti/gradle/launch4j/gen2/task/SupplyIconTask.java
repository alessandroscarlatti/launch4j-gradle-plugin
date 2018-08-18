package com.scarlatti.gradle.launch4j.gen2.task;

import com.scarlatti.gradle.launch4j.gen2.FileResolutionStrategy;
import com.scarlatti.util.ImageGenerator;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import javax.activation.FileDataSource;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    private Launch4jHelperTask helperTask;

    public SupplyIconTask() {
    }

    /**
     * Move the input icon file to the destination,
     * or if it does not exist, generate an icon.
     */
    @TaskAction
    public void generateIcon() {
        // todo implement whether to generate?
//        ImageGenerator.generateIconFileForStringHash(Paths.get(destination.getAbsolutePath()), iconName);
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

    @Override
    public boolean getEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public FileResolutionStrategy getResolve() {
        return resolve;
    }

    public void setResolve(FileResolutionStrategy resolve) {
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
