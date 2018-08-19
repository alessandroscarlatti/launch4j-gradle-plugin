package com.scarlatti.gradle.launch4j.gen2.task;

import com.scarlatti.util.ImageGenerator;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Random;
import java.util.function.Supplier;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Tuesday, 8/14/2018
 */
public class SupplySplashTask extends DefaultTask {

    @Input
    private boolean autoGenerate;

    /**
     * The text to use when generating a splash.
     */
    @Optional
    @Input
    private String text;

    @Optional
    @InputFile
    private File splash;

    @OutputFile
    private File destination;

    /**
     * A specific location to use.  Takes precedence over auto-generation;
     */
    private Supplier<File> resolve;

    @TaskAction
    public void generateSplash() {

        if (splash != null) {
            try {
                Files.copy(splash.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                throw new RuntimeException("Error copying splash " + splash + " to " + destination, e);
            }
        } else if (autoGenerate) {
            // if text is null generate a random string
            if (text == null) {
                text = String.valueOf(Math.abs(new Random(System.currentTimeMillis()).nextInt()));
            }

            ImageGenerator.generateSplashFileForStringHash(destination.toPath(), text);
        }
    }

    public boolean getAutoGenerate() {
        return autoGenerate;
    }

    public void setAutoGenerate(boolean autoGenerate) {
        this.autoGenerate = autoGenerate;
    }

    public Supplier<File> getResolve() {
        return resolve;
    }

    public void setResolve(Supplier<File> resolve) {
        this.resolve = resolve;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public File getDestination() {
        return destination;
    }

    public void setDestination(File destination) {
        this.destination = destination;
    }

    public File getSplash() {
        return splash;
    }

    public void setSplash(File splash) {
        this.splash = splash;
    }
}
