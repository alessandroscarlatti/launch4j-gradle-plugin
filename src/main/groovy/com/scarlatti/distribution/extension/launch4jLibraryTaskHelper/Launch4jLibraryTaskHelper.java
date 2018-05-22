package com.scarlatti.distribution.extension.launch4jLibraryTaskHelper;

import com.scarlatti.gradle.launch4j.Launch4jLibraryTaskConfigurer;
import edu.sc.seis.launch4j.tasks.Launch4jLibraryTask;
import groovy.lang.Closure;
import groovy.lang.DelegatesTo;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Monday, 5/7/2018
 */
public class Launch4jLibraryTaskHelper {
    private final Launch4jLibraryTask task;

    public Launch4jLibraryTaskHelper(Launch4jLibraryTask task) {
        this.task = task;
    }

    public Launch4jLibraryTaskHelper configure(@DelegatesTo(Launch4jLibraryTaskConfigurationDelegate.class) Closure closure) {
        Launch4jLibraryTaskConfigurationDelegate config = new Launch4jLibraryTaskConfigurationDelegate();
        closure.setDelegate(config);
        closure.call();
        doConfigure(config);
        return this;
    }

    /**
     * Apply the settings stored in the configurer.
     *
     * @param config the configurer that has been used.
     */
    private void doConfigure(Launch4jLibraryTaskConfigurationDelegate config) {
        System.out.println("configuring launch4j task...");
//        new Launch4jLibraryTaskConfigurer(task, config).run();
    }
}
