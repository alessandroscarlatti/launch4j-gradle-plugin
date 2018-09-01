package com.scarlatti.gradle.launch4j.gen2;

import com.scarlatti.gradle.launch4j.gen2.details.ManifestConfigurationDetails.ElevationLevel;

import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

public class SimpleManifestProvider implements ManifestProvider {

    private ElevationLevel elevationLevel;

    private final String AS_INVOKER_MANIFEST_RESOURCE_PATH = "/manifest/asInvoker.manifest";
    private final String REQUIRE_ADMINISTRATOR_MANIFEST_RESOURCE_PATH = "/manifest/requireAdministrator.manifest";

    public SimpleManifestProvider(ElevationLevel elevationLevel) {
        this.elevationLevel = elevationLevel;
    }

    @Override
    public String buildRawManifest() {
        if (elevationLevel == ElevationLevel.AS_INVOKER) {
            return getResource(AS_INVOKER_MANIFEST_RESOURCE_PATH);
        }
        else if (elevationLevel == ElevationLevel.REQUIRE_ADMINISTRATOR) {
            return getResource(REQUIRE_ADMINISTRATOR_MANIFEST_RESOURCE_PATH);
        }
        else {
            return "";
        }
    }

    public static String getResource(String resourcePath) {
        InputStream inputStream = SimpleManifestProvider.class.getResourceAsStream(resourcePath);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleManifestProvider that = (SimpleManifestProvider) o;
        return elevationLevel == that.elevationLevel;
    }

    @Override
    public int hashCode() {

        return Objects.hash(elevationLevel);
    }
}