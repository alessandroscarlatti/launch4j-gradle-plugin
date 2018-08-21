package com.scarlatti.gradle.launch4j.gen2.task;

import com.scarlatti.gradle.launch4j.gen2.details.SplashConfigurationDetails;
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

    @Internal
    private boolean ran;
    @Internal
    private boolean suppliedSplash;

    /**
     * A specific location to use.  Takes precedence over auto-generation;
     */
    @Internal
    private Supplier<File> resolve;

    public void configureFromSplashConfigDtls(SplashConfigurationDetails details) {
        autoGenerate = details.getAutoGenerate();
        text = details.getText();
    }

    @TaskAction
    public void generateSplash() {
        ran = true;
        if (splash != null) {
            try {
                Files.copy(splash.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
                suppliedSplash = true;
            } catch (Exception e) {
                throw new RuntimeException("Error copying splash " + splash + " to " + destination, e);
            }
        } else if (autoGenerate) {
            // if text is null generate a random string
            if (text == null) {
                text = String.valueOf(Math.abs(new Random(System.currentTimeMillis()).nextInt()));
            }

            ImageGenerator.generateSplashFileForStringHash(destination.toPath(), text);
            suppliedSplash = true;
        }

        // after this we need to somehow update the launch4j task.
        // if we actually created a splash, we should update launch4j.
        //
        // this task should only have been called when it is OK to use a splash.
        // that is, when the headerType is set to gui.
        //
        // so we shouldn't have to worry about that.
    }

    public boolean shouldSupplySplash() {
        if (!isEnabled())
            return false;

        if (ran) {
            return suppliedSplash;
        }
        else {
            return splash != null || autoGenerate;
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
