package com.scarlatti.gradle.launch4j.gen2.task;

import com.scarlatti.gradle.launch4j.ManifestProvider;
import com.scarlatti.gradle.launch4j.gen2.DefaultManifestProvider;
import com.scarlatti.gradle.launch4j.gen2.SimpleManifestProvider;
import com.scarlatti.gradle.launch4j.gen2.details.ManifestConfigurationDetails;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.*;
import org.gradle.api.tasks.incremental.IncrementalTaskInputs;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.function.Supplier;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Tuesday, 8/14/2018
 */
public class SupplyManifestTask extends DefaultTask implements Serializable {

    @Input
    private boolean autoGenerate;

    @Optional
    @InputFile
    private File manifest;

    @OutputFile
    private File destination;

    @Input
    private ManifestProvider base = new DefaultManifestProvider();

    /**
     * A specific resolve to use.  Takes precedence over auto-generation.
     */
    @Internal
    private Supplier<File> resolve;

    public void configureFromManifestConfigDetails(ManifestConfigurationDetails details) {
        autoGenerate = details.getAutoGenerate();
        base = details.getBase();
    }

    @Internal
    private boolean ran;

    @Internal
    private boolean suppliedManifest;

    private String emptyManifest() {
        return "";
    }

    public ManifestProvider asInvoker() {
        return new SimpleManifestProvider(ManifestConfigurationDetails.ElevationLevel.AS_INVOKER);
    }

    public ManifestProvider requireAdministrator() {
        return new SimpleManifestProvider(ManifestConfigurationDetails.ElevationLevel.REQUIRE_ADMINISTRATOR);
    }

    public ManifestProvider getBase() {
        return base;
    }

    public void setBase(ManifestProvider base) {
        this.base = base;
    }

    @TaskAction
    public void generateManifest(IncrementalTaskInputs inputs) {
        ran = true;
        try {
            byte[] bytes;
            if (manifest != null) {
                bytes = Files.readAllBytes(manifest.toPath());
            } else {
                bytes = base.buildRawManifest().getBytes();
            }

            Files.write(destination.toPath(), bytes);
            suppliedManifest = true;
        } catch (Exception e) {
            throw new RuntimeException("Error generating manifest file at " + destination, e);
        }
    }

    public boolean shouldSupplyManifest() {
        if (!isEnabled())
            return false;

        if (ran) {
            return suppliedManifest;
        }
        else {
            return manifest != null || autoGenerate;
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

    public File getManifest() {
        return manifest;
    }

    public void setManifest(File manifest) {
        this.manifest = manifest;
    }

    public File getDestination() {
        return destination;
    }

    public void setDestination(File destination) {
        this.destination = destination;
    }
}
