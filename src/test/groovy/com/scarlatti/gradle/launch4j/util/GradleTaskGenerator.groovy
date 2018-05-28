package com.scarlatti.gradle.launch4j.util

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Saturday, 5/26/2018
 */
class GradleTaskGenerator {
    static String assertExeCreatedTask(String exeName) {
        Objects.requireNonNull(exeName, "Must provide exe name")
        return '''
        task assertExeCreated() {
            doLast {
                assert file("${project.buildDir.absolutePath}/launch4j/launch4jTask/#{exeName}").exists()
            }
        }
        '''.replace('#{exeName}', exeName)
    }

    static String runExe(String taskName, String exeName) {
        Objects.requireNonNull(taskName, "Must provide task name")
        Objects.requireNonNull(exeName, "Must provide exe name")
        return '''
            task runExe() {
                doLast {
                    def p = new ProcessBuilder("${buildDir}/launch4j/#{taskName}/#{exeName}.exe").start()
                    p.waitFor()
                    println ">>> process output:"
                    println p.inputStream.text
                    println ">>> process error:"
                    System.err.println p.errorStream.text
                }
            }
        '''.replace('#{taskName}', taskName)
        .replace('#{exeName}', exeName)
    }
}
