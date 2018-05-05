package com.scarlatti.launch4j.extension.launch4jLibraryTaskHelper;

import edu.sc.seis.launch4j.tasks.Launch4jLibraryTask;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Saturday, 5/5/2018
 *
 * A configurer a Launch4JLibraryTask.
 */
public class Launch4jLibraryTaskConfigurer implements Runnable {
    private Launch4jLibraryTask task;
    private Launch4jLibraryTaskConfigurationDelegate config;

    public Launch4jLibraryTaskConfigurer(Launch4jLibraryTask task, Launch4jLibraryTaskConfigurationDelegate config) {
        this.task = task;
        this.config = config;
    }

    @Override
    public void run() {
        // configure the task based on the configuration.
        Launch4jLibraryTaskProps launch4JLibraryTaskProps = buildLaunch4jTaskProps();
        configure(launch4JLibraryTaskProps);
    }

    /**
     * Convert the config into actual launch4j task props.
     *
     * @return the actual launch4j props.
     */
    private Launch4jLibraryTaskProps buildLaunch4jTaskProps() {
        return new Launch4jLibraryTaskProps();
    }

    /**
     * Configure the task with the given launch4j props.
     * @param props the props to use when configuring the task.
     */
    private void configure(Launch4jLibraryTaskProps props) {
        // for example...
        task.setProductName(props.getExeName().getValue());
    }
}
