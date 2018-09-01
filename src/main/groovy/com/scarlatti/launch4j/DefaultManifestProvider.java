package com.scarlatti.launch4j;

import java.io.Serializable;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Monday, 8/20/2018
 */
public class DefaultManifestProvider implements ManifestProvider, Serializable {
    @Override
    public String buildRawManifest() {
        return SimpleManifestProvider.getResource("/manifest/asInvoker.manifest");
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) || obj.getClass() == DefaultManifestProvider.class;
    }
}
