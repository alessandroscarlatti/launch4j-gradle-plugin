package com.scarlatti.gradle.launch4j;

import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Tuesday, 5/22/2018
 */
public class SimpleManifestProvider implements ManifestProvider {

    private ManifestConfigurationDelegate.ElevationLevel elevationLevel;

    private final String AS_INVOKER_MANIFEST_RESOURCE_PATH = "/manifest/asInvoker.manifest";
    private final String REQUIRE_ADMINISTRATOR_MANIFEST_RESOURCE_PATH = "/manifest/requireAdministrator.manifest";

    public SimpleManifestProvider(ManifestConfigurationDelegate.ElevationLevel elevationLevel) {
        this.elevationLevel = elevationLevel;
    }

    @Override
    public String buildRawManifest() {
        if (elevationLevel == ManifestConfigurationDelegate.ElevationLevel.AS_INVOKER) {
            return getResource(AS_INVOKER_MANIFEST_RESOURCE_PATH);
        }
        else if (elevationLevel == ManifestConfigurationDelegate.ElevationLevel.REQUIRE_ADMINISTRATOR) {
            return getResource(REQUIRE_ADMINISTRATOR_MANIFEST_RESOURCE_PATH);
        }
        else {
            return "";
        }
    }

    public String getResource(String resourcePath) {
        InputStream inputStream = getClass().getResourceAsStream(resourcePath);
        try {
            Objects.requireNonNull(inputStream, "Error getting input stream for resource " + resourcePath);
            return new Scanner(inputStream).useDelimiter("\\Z").next();
        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
