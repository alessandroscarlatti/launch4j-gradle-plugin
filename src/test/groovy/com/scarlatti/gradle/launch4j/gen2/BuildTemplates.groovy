package com.scarlatti.gradle.launch4j.gen2

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Monday, 7/23/2018
 */
class BuildTemplates {
    static String applyLaunch4jPlugin() {
        "apply plugin: 'launch4j'"
    }

    static String tasks() {
        ":tasks"
    }

    static String common() {
        "/gen2/common.gradle"
    }
}
