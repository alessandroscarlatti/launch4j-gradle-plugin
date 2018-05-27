package com.scarlatti.gradle.launch4j;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Tuesday, 5/22/2018
 */
public class ManifestConfigurationDelegate {
    private ManifestProvider base = this::emptyManifest;

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

    enum ElevationLevel {
        AS_INVOKER,
        REQUIRE_ADMINISTRATOR
    }
}
