package com.scarlatti.gradle.launch4j.gen2.task;

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

    @Input
    private String iconName;

    @Input
    private boolean autoGenerate;

    private Launch4jHelperTask helperTask;

    private static final String NAME_INPUT = "baseIconName";

    public SupplyIconTask() {
    }

    /**
     * Move the input icon file to the destination,
     * or if it does not exist, generate an icon.
     */
    @TaskAction
    public void generateIcon() {
        // todo implement whether to generate?



        ImageGenerator.generateIconFileForStringHash(Paths.get(destination.getAbsolutePath()), iconName);
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

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public boolean getAutoGenerate() {
        return autoGenerate;
    }

    public void setAutoGenerate(boolean autoGenerate) {
        this.autoGenerate = autoGenerate;
    }
}
