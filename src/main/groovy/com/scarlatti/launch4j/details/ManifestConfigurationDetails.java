package com.scarlatti.launch4j.details;

import com.scarlatti.launch4j.ManifestProvider;
import com.scarlatti.launch4j.DefaultManifestProvider;
import com.scarlatti.launch4j.FileResolutionStrategy;
import com.scarlatti.launch4j.SimpleManifestProvider;

import java.io.Serializable;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Thursday, 7/19/2018
 *
 * Details about locating or generating a manifest.
 */
public class ManifestConfigurationDetails implements AutoGeneratable, Serializable {

    private boolean autoGenerate;

    /**
     * A specific resolve to use.  Takes precedence over auto-generation.
     */
    private FileResolutionStrategy resolve;

    private ManifestProvider base = new DefaultManifestProvider();

    private String emptyManifest() {
        return "";
    }

    public ManifestProvider asInvoker() {
        return new SimpleManifestProvider(ElevationLevel.AS_INVOKER);
    }

    public ManifestProvider requireAdministrator() {
        return new SimpleManifestProvider(ElevationLevel.REQUIRE_ADMINISTRATOR);
    }

    public ManifestProvider getBase() {
        return base;
    }

    public void setBase(ManifestProvider base) {
        this.base = base;
    }

    public enum ElevationLevel {
        AS_INVOKER,
        REQUIRE_ADMINISTRATOR
    }

    public ManifestConfigurationDetails() {
    }

    /**
     * Copy constructor.
     * @param other the details to copy.
     */
    public ManifestConfigurationDetails(ManifestConfigurationDetails other) {
        this.autoGenerate = other.autoGenerate;
        this.resolve = other.resolve;
        this.base = other.base;
    }

    @Override
    public boolean getAutoGenerate() {
        return autoGenerate;
    }

    @Override
    public void setAutoGenerate(boolean autoGenerate) {
        this.autoGenerate = autoGenerate;
    }

    public FileResolutionStrategy getResolve() {
        return resolve;
    }

    public void setResolve(FileResolutionStrategy resolve) {
        this.resolve = resolve;
    }
}
