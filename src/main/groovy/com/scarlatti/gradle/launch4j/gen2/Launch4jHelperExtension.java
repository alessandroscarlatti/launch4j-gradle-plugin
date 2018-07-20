package com.scarlatti.gradle.launch4j.gen2;

import com.scarlatti.gradle.launch4j.gen2.details.IconConfigurationDetails;
import com.scarlatti.gradle.launch4j.gen2.details.MainClassConfigurationDetails;
import com.scarlatti.gradle.launch4j.gen2.details.ManifestConfigurationDetails;
import com.scarlatti.gradle.launch4j.gen2.details.SplashConfigurationDetails;
import edu.sc.seis.launch4j.tasks.Launch4jLibraryTask;
import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import org.gradle.api.Project;
import org.gradle.api.Task;

import java.util.HashMap;
import java.util.Map;

import static groovy.lang.Closure.DELEGATE_FIRST;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Thursday, 7/19/2018
 * <p>
 * Global settings for the helper are stored here
 * and can be configured by the user.
 * <p>
 * This extension manages the association between
 * a launch4jTask and its launch4jHelperTask.
 * <p>
 * There may only be one launch4jHelperTask for a single launch4jTask.
 */
public class Launch4jHelperExtension {
    private Project project;
    private Map<Task, Launch4jHelperTask> helperTasks = new HashMap<>();

    private IconConfigurationDetails iconConfigurationDetails = defaultIconConfigDtls();
    private ManifestConfigurationDetails manifestConfigurationDetails = defaultManifestConfigDtls();
    private SplashConfigurationDetails splashConfigurationDetails = defaultSplashConfigDtls();
    private MainClassConfigurationDetails mainClassConfigurationDetails = defaultMainClassConfigDtls();

    public Launch4jHelperExtension(Project project) {
        this.project = project;
    }

    /**
     * Create a new launch4jHelperTask if necessary for the given task.
     *
     * @param task the launch4jTask to help.  For the time being, a launch4jLibraryTask.
     */
    public void help(Task task) {
        validateTaskIsLaunch4jLibraryTask(task);

        // todo is this how we should create the task???
        Launch4jHelperTask helperTask = new Launch4jHelperTask(task);
        helperTasks.put(task, helperTask);
    }

    /**
     * Return the launch4jHelperTask associated to this task.
     *
     * @param task the launch4jTask, for the time being only an instance of Launch4jLibraryTask
     * @return the launch4jHelperTask associated through this extension.
     * or null if there is no associated task.
     */
    public Launch4jHelperTask helper(Task task) {
        validateTaskIsLaunch4jLibraryTask(task);
        return helperTasks.get(task);
    }

    /**
     * Configure the helper task for this launch4jTask via the configuration closure.
     *
     * @param task   the launch4jTask
     * @param config the configuration closure for the launch4jHelperTask
     */
    void helper(Task task, @DelegatesTo(value = Launch4jHelperTask.class, strategy = DELEGATE_FIRST) Closure config) {
        validateTaskIsLaunch4jLibraryTask(task);

        config.setDelegate(helperTasks.get(task));
        config.setResolveStrategy(DELEGATE_FIRST);
        config.call();
    }

    static void validateTaskIsLaunch4jLibraryTask(Task task) {
        if (!(task instanceof Launch4jLibraryTask)) {
            throw new IllegalStateException("Task " + task.getName() + " must be an instance of " + Launch4jLibraryTask.class.getName());
        }
    }

    /**
     * Public api to access the icon details.
     * @return the icon details
     */
    public IconConfigurationDetails getIcon() {
        return iconConfigurationDetails;
    }

    /**
     * Public api to access the manifest details.
     * @return the manifest details.
     */
    public ManifestConfigurationDetails getManifest() {
        return manifestConfigurationDetails;
    }

    /**
     * Public api to access the splash details.
     * @return the splash details.
     */
    public SplashConfigurationDetails getSplash() {
        return splashConfigurationDetails;
    }

    /**
     * Public api to configure the icon details.
     * @param config the closure to apply.
     */
    public void icon(@DelegatesTo(value = IconConfigurationDetails.class, strategy = DELEGATE_FIRST) Closure config) {
        config.setDelegate(iconConfigurationDetails);
        config.setResolveStrategy(DELEGATE_FIRST);
        config.call();
    }

    /**
     * Public api to configure the manifest details.
     * @param config the closure to apply.
     */
    public void manifest(@DelegatesTo(value = ManifestConfigurationDetails.class, strategy = DELEGATE_FIRST) Closure config) {
        config.setDelegate(manifestConfigurationDetails);
        config.setResolveStrategy(DELEGATE_FIRST);
        config.call();
    }

    /**
     * Public api to configure the splash details.
     * @param config the closure to apply.
     */
    public void splash(@DelegatesTo(value = SplashConfigurationDetails.class, strategy = DELEGATE_FIRST) Closure config) {
        config.setDelegate(splashConfigurationDetails);
        config.setResolveStrategy(DELEGATE_FIRST);
        config.call();
    }

    /**
     * Public api to configure the mainClass details.
     * @param config the closure to apply.
     */
    public void mainClass(@DelegatesTo(value = MainClassConfigurationDetails.class, strategy = DELEGATE_FIRST) Closure config) {
        config.setDelegate(mainClassConfigurationDetails);
        config.setResolveStrategy(DELEGATE_FIRST);
        config.call();
    }

    private IconConfigurationDetails defaultIconConfigDtls() {
        IconConfigurationDetails details = new IconConfigurationDetails();
        details.setAutoGenerate(true);
        return details;
    }

    private SplashConfigurationDetails defaultSplashConfigDtls() {
        SplashConfigurationDetails details = new SplashConfigurationDetails();
        details.setAutoGenerate(true);
        return details;
    }

    private ManifestConfigurationDetails defaultManifestConfigDtls() {
        ManifestConfigurationDetails details = new ManifestConfigurationDetails();
        details.setAutoGenerate(true);
        return details;
    }

    private MainClassConfigurationDetails defaultMainClassConfigDtls() {
        MainClassConfigurationDetails details = new MainClassConfigurationDetails();
        details.setFindMainClass(true);
        return details;
    }
}
