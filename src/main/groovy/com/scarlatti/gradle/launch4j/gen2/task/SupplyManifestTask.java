package com.scarlatti.gradle.launch4j.gen2.task;

import com.scarlatti.gradle.launch4j.gen2.FileResolutionStrategy;
import com.scarlatti.gradle.launch4j.gen2.Launch4jHelperExtension;
import com.scarlatti.gradle.launch4j.gen2.details.IconConfigurationDetails;
import com.scarlatti.gradle.launch4j.gen2.details.ManifestConfigurationDetails;
import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import static groovy.lang.Closure.DELEGATE_FIRST;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Tuesday, 8/14/2018
 */
public class SupplyManifestTask extends DefaultTask {

    private boolean autoGenerate;

    /**
     * A specific resolve to use.  Takes precedence over auto-generation.
     */
    private FileResolutionStrategy resolve;


    @TaskAction
    public void generateManifest() {

    }

    public boolean getAutoGenerate() {
        return autoGenerate;
    }

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
