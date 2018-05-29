package com.scarlatti.testing.util

import com.talanlabs.avatargenerator.IdenticonAvatar
import net.sf.image4j.codec.ico.ICOEncoder

import java.awt.image.BufferedImage
import java.nio.file.Files
import java.nio.file.Path

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Monday, 5/28/2018
 */
class IconGenerator {

    static void generateIconFileForStringHash(Path path, String str) {
        List<BufferedImage> pngs = []
        List<Integer> dims = [16, 32, 48, 64, 96, 128, 256]
        for (int dim : dims) {
            pngs.add IdenticonAvatar.newAvatarBuilder().size(dim, dim).build().create(str.hashCode())
        }

        Files.createDirectories(path.getParent())
        ICOEncoder.write(pngs, path.toFile())
    }
}
