package com.scarlatti.gradle.launch4j.gen2;

import com.scarlatti.gradle.launch4j.gen2.details.*;
import com.scarlatti.gradle.launch4j.gen2.task.Launch4jHelperTask;
import edu.sc.seis.launch4j.tasks.Launch4jLibraryTask;
import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import org.gradle.api.Project;
import org.gradle.api.Task;

import java.io.File;
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
    private ResourcesConfigurationDetails resourcesConfigurationDetails = defaultResourcesConfigDtls();
    private HelperTaskConfigurationDetails helperTaskConfigurationDetails = defaultHelperTaskConfigDtls();

    static final String DEFAULT_APP_NAME = "launch4jApp";
    static final String DEFAULT_HELPER_TASK_GROUP = "launch4jHelper";
    static final String DEFAULT_LAUNCH4J_TASK_VARIABLE = "#task";
    static final String DEFAULT_HELPER_TASK_DESCRIPTION_TEMPLATE = "Configures and generates resources for the " + DEFAULT_LAUNCH4J_TASK_VARIABLE + " task.";
    static final String DEFAULT_HELPER_TASK_DESCRIPTION = "Configures and generates resources for a Launch4j task.";

    static final String DEFAULT_RESOURCES_DIR = "launch4j";
    static final String DEFAULT_ICON_FILE_NAME = "icon.ico";
    static final String DEFAULT_SPLASH_FILE_NAME = "splash.bmp";
    static final String DEFAULT_MANIFEST_FILE_NAME = "application.manifests";
    static final String DEFAULT_LAUNCH4J_PROPERTIES_FILE_NAME = "launch4j.properties";

    public Launch4jHelperExtension(Project project) {
        this.project = project;
    }

    /**
     * Create a new launch4jHelperTask if necessary for the given task.
     *
     * @param task the launch4jTask to help.  For the time being, a launch4jLibraryTask.
     * @return the newly created helper task.
     */
    public Launch4jHelperTask help(Task task) {
        validateTaskIsLaunch4jLibraryTask(task);
        Launch4jHelperTask helperTask = helper(task);

        if (helperTask == null) {
            helperTask = createNewHelperTask((Launch4jLibraryTask) task);
            helperTasks.put(task, helperTask);
        }

        return helperTask;
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
    public void help(Task task, @DelegatesTo(value = Launch4jHelperTask.class, strategy = DELEGATE_FIRST) Closure config) {
        // create the task
        Task helper = help(task);

        config.setDelegate(helper);
        config.setResolveStrategy(DELEGATE_FIRST);
        config.call();
    }

    public static void validateTaskIsLaunch4jLibraryTask(Task task) {
        if (!(task instanceof Launch4jLibraryTask)) {
            throw new IllegalStateException("Task " + task.getName() + " must be an instance of " + Launch4jLibraryTask.class.getName());
        }
    }

    /**
     * Public api to access the icon details.
     *
     * @return the icon details
     */
    public IconConfigurationDetails getIcon() {
        return iconConfigurationDetails;
    }

    /**
     * Public api to access the manifest details.
     *
     * @return the manifest details.
     */
    public ManifestConfigurationDetails getManifest() {
        return manifestConfigurationDetails;
    }

    /**
     * Public api to access the splash details.
     *
     * @return the splash details.
     */
    public SplashConfigurationDetails getSplash() {
        return splashConfigurationDetails;
    }

    /**
     * Public api to access the main class details.
     *
     * @return the main class details.
     */
    public MainClassConfigurationDetails getMainClass() {
        return mainClassConfigurationDetails;
    }

    /**
     * Public api to access the resources details.
     *
     * @return the resources details.
     */
    public ResourcesConfigurationDetails getResources() {
        return resourcesConfigurationDetails;
    }

    /**
     * Public api to access the helper task details.
     *
     * @return the helper task details.
     */
    public HelperTaskConfigurationDetails getMeta() {
        return helperTaskConfigurationDetails;
    }

    /**
     * Public api to configure the icon details.
     *
     * @param config the closure to apply.
     */
    public void icon(@DelegatesTo(value = IconConfigurationDetails.class, strategy = DELEGATE_FIRST) Closure config) {
        config.setDelegate(iconConfigurationDetails);
        config.setResolveStrategy(DELEGATE_FIRST);
        config.call();
    }

    /**
     * Public api to configure the manifest details.
     *
     * @param config the closure to apply.
     */
    public void manifest(@DelegatesTo(value = ManifestConfigurationDetails.class, strategy = DELEGATE_FIRST) Closure config) {
        config.setDelegate(manifestConfigurationDetails);
        config.setResolveStrategy(DELEGATE_FIRST);
        config.call();
    }

    /**
     * Public api to configure the splash details.
     *
     * @param config the closure to apply.
     */
    public void splash(@DelegatesTo(value = SplashConfigurationDetails.class, strategy = DELEGATE_FIRST) Closure config) {
        config.setDelegate(splashConfigurationDetails);
        config.setResolveStrategy(DELEGATE_FIRST);
        config.call();
    }

    /**
     * Public api to configure the mainClass details.
     *
     * @param config the closure to apply.
     */
    public void mainClass(@DelegatesTo(value = MainClassConfigurationDetails.class, strategy = DELEGATE_FIRST) Closure config) {
        config.setDelegate(mainClassConfigurationDetails);
        config.setResolveStrategy(DELEGATE_FIRST);
        config.call();
    }

    /**
     * Public api to configure the resources details.
     *
     * @param config the closure to apply.
     */
    public void resources(@DelegatesTo(value = ResourcesConfigurationDetails.class, strategy = DELEGATE_FIRST) Closure config) {
        config.setDelegate(resourcesConfigurationDetails);
        config.setResolveStrategy(DELEGATE_FIRST);
        config.call();
    }

    /**
     * Public api to configure the helper task details.
     *
     * @param config the closure to apply.
     */
    public void meta(@DelegatesTo(value = HelperTaskConfigurationDetails.class, strategy = DELEGATE_FIRST) Closure config) {
        config.setDelegate(helperTaskConfigurationDetails);
        config.setResolveStrategy(DELEGATE_FIRST);
        config.call();
    }

    public static IconConfigurationDetails defaultIconConfigDtls() {
        IconConfigurationDetails details = new IconConfigurationDetails();
        details.setEnabled(true);
        details.setAutoGenerate(true);
        details.setName(DEFAULT_APP_NAME);
        return details;
    }

    public static SplashConfigurationDetails defaultSplashConfigDtls() {
        SplashConfigurationDetails details = new SplashConfigurationDetails();
        details.setAutoGenerate(true);
        details.setName(DEFAULT_APP_NAME);
        return details;
    }

    public static ManifestConfigurationDetails defaultManifestConfigDtls() {
        ManifestConfigurationDetails details = new ManifestConfigurationDetails();
        details.setAutoGenerate(true);
        return details;
    }

    private static MainClassConfigurationDetails defaultMainClassConfigDtls() {
        MainClassConfigurationDetails details = new MainClassConfigurationDetails();
        details.setFindMainClass(true);
        return details;
    }

    private ResourcesConfigurationDetails defaultResourcesConfigDtls() {
        ResourcesConfigurationDetails details = new ResourcesConfigurationDetails();
        details.setResourcesDir(project.getBuildDir().toPath().resolve(DEFAULT_RESOURCES_DIR).toFile());
        details.setIconFileName(DEFAULT_ICON_FILE_NAME);
        details.setSplashFileName(DEFAULT_SPLASH_FILE_NAME);
        details.setManifestFileName(DEFAULT_MANIFEST_FILE_NAME);
        details.setLaunch4jPropertiesFileName(DEFAULT_LAUNCH4J_PROPERTIES_FILE_NAME);

        return details;
    }

    private static HelperTaskConfigurationDetails defaultHelperTaskConfigDtls() {
        HelperTaskConfigurationDetails details = new HelperTaskConfigurationDetails();
        details.setGenerateDescription(true);
        details.setDescriptionTemplate(DEFAULT_HELPER_TASK_DESCRIPTION_TEMPLATE);
        details.setDescription(DEFAULT_HELPER_TASK_DESCRIPTION);
        details.setLaunch4jTaskVariable(DEFAULT_LAUNCH4J_TASK_VARIABLE);
        details.setGroup(DEFAULT_HELPER_TASK_GROUP);
        return details;
    }

    /**
     * Create a new helper task, initializing it with sensible defaults.
     *
     * @param task the launch4jTask to be associated to the helper task.
     * @return the newly created helper task.
     */
    private Launch4jHelperTask createNewHelperTask(Launch4jLibraryTask task) {
        String helperTaskName = task.getName() + "Launch4jHelper";
        Launch4jHelperTask helperTask = project.getTasks().create(helperTaskName, Launch4jHelperTask.class);

        // associate the launch4jTask
        helperTask.setLaunch4jTask(task);

        // apply the values from the extension

        // set up icon task

        // set up splash task

        // set up manifest task

        // todo aha! could I set up a "read configurations" task
        // that would be the task that finally updates all the inputs
        // of the other tasks?
        // also that would theoretically give a way for
        // a potential "generateResources" task to generate
        // the launch4j.properties or an icon.ico, e.g.

        return helperTask;
    }
}
