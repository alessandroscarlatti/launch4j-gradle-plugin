package com.scarlatti.gradle.launch4j;

import com.scarlatti.gradle.launch4j.ManifestConfigurationDelegate.ElevationLevel;

import java.util.Scanner;

import static com.scarlatti.gradle.launch4j.ManifestConfigurationDelegate.ElevationLevel.AS_INVOKER;
import static com.scarlatti.gradle.launch4j.ManifestConfigurationDelegate.ElevationLevel.REQUIRE_ADMINISTRATOR;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Tuesday, 5/22/2018
 */
public class SimpleManifestProvider implements ManifestProvider {

    private ElevationLevel elevationLevel;

    private final String AS_INVOKER_MANIFEST_RESOURCE_PATH = "/manifest/application-asInvoker.manifest";
    private final String REQUIRE_MANIFEST_RESOURCE_PATH = "/manifest/application-asInvoker.manifest";

    public SimpleManifestProvider(ElevationLevel elevationLevel) {
        this.elevationLevel = elevationLevel;
    }

    @Override
    public String buildRawManifest() {
        if (elevationLevel == AS_INVOKER) {
            return getResource(AS_INVOKER_MANIFEST_RESOURCE_PATH);
        }
        else if (elevationLevel == REQUIRE_ADMINISTRATOR) {
            return getResource(REQUIRE_MANIFEST_RESOURCE_PATH);
        }
        else {
            return "";
        }
    }

    public String getResource(String resourcePath) {
        return new Scanner(getClass().getResourceAsStream(resourcePath)).next("\\Z");
    }
}
