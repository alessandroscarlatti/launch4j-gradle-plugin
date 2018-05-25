package com.scarlatti

import com.talanlabs.avatargenerator.IdenticonAvatar
import net.sf.image4j.codec.ico.ICOEncoder
import spock.lang.Shared
import spock.lang.Specification

import java.awt.image.BufferedImage
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Thursday, 5/24/2018
 */
class IconTest extends Specification {

    @Shared
    Path avatarDir
    @Shared
    Path avatarPath
    @Shared
    Path iconPath

    def setupSpec() {
        avatarDir = Paths.get(System.getProperty("user.dir"), "build", "avatar")
        avatarPath = Paths.get(avatarDir.toString(), "avatar.png")
        iconPath = Paths.get(avatarDir.toString(), "icon.ico")
        Files.createDirectories(avatarDir)
    }

    def "produce icon from png"() {
        when:
            List<BufferedImage> pngs = []
            List<Integer> dims = [16, 32, 48, 64, 96, 128, 256]
            for (int dim : dims) {
                pngs.add IdenticonAvatar.newAvatarBuilder().size(dim, dim).build().create("Lewis Franklin Bells".hashCode())
            }

            ICOEncoder.write(pngs, iconPath.toFile())
        then:
            noExceptionThrown()
    }

    def "produce png from hash"() {
        when:
            byte[] png = IdenticonAvatar.newAvatarBuilder().build().createAsPngBytes("Lewis Franklin Bells".hashCode())
            Files.write(avatarPath, png)
        then:
            noExceptionThrown()
    }
}
