package com.scarlatti.gradle.launch4j.gen2.task;

import com.scarlatti.gradle.launch4j.gen2.FileResolutionStrategy;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.File;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Tuesday, 8/14/2018
 */
public class SupplySplashTask extends DefaultTask {

    private boolean autoGenerate;

    /**
     * A specific location to use.  Takes precedence over auto-generation;
     */
    private FileResolutionStrategy resolve;

    /**
     * The text to use when generating a splash.
     */
    private String text;

    @TaskAction
    public void generateSplash() {
//        ImageGenerator.generateSplashFileForStringHash(Paths.get(destination), details.getName());
    }

    public boolean getAutoGenerate() {
        return autoGenerate;
    }

    public void setAutoGenerate(boolean autoGenerate) {
        this.autoGenerate = autoGenerate;
    }

    public FileResolutionStrategy getResolve() {
        return resolve;
    }

    public void setResolve(FileResolutionStrategy resolve) {
        this.resolve = resolve;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
