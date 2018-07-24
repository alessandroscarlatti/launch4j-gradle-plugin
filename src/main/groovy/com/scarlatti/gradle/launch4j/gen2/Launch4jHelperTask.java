package com.scarlatti.gradle.launch4j.gen2;

import com.scarlatti.gradle.launch4j.gen2.details.*;
import edu.sc.seis.launch4j.tasks.Launch4jLibraryTask;
import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import org.gradle.api.DefaultTask;
import org.gradle.api.Task;

import static com.scarlatti.gradle.launch4j.gen2.Launch4jHelperPlugin.LAUNCH4J_HELPER_EXTENSION_NAME;
import static com.scarlatti.gradle.launch4j.gen2.Launch4jHelperPlugin.LAUNCH4J_HELPER_TASK_GROUP;
import static groovy.lang.Closure.DELEGATE_FIRST;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Thursday, 7/19/2018
 * <p>
 * A helper task to locate, generate, and configure
 * resources for a launch4j task based on:
 * - global specifications from launch4jHelperExtension
 * - specific instructions from configuration of this task
 * - local resources, based on naming conventions
 */
public class Launch4jHelperTask extends DefaultTask {
    private Launch4jHelperExtension extension;
    private Launch4jLibraryTask launch4jTask;

    private IconConfigurationDetails iconConfigurationDetails;
    private ManifestConfigurationDetails manifestConfigurationDetails;
    private SplashConfigurationDetails splashConfigurationDetails;
    private MainClassConfigurationDetails mainClassConfigurationDetails;
    private HelperTaskConfigurationDetails helperTaskConfigurationDetails;

    /**
     * Create a new Launch4jHelperTask for a launch4jTask.
     * All configurations are copied from the launch4jHelper extension
     * as it exists at the time of this invocation.
     */
    public Launch4jHelperTask() {
        this.extension = (Launch4jHelperExtension) getProject().getExtensions().findByName(LAUNCH4J_HELPER_EXTENSION_NAME);

        // copy the details from the extension
        iconConfigurationDetails = new IconConfigurationDetails(extension.getIcon());
        manifestConfigurationDetails = new ManifestConfigurationDetails(extension.getManifest());
        splashConfigurationDetails = new SplashConfigurationDetails(extension.getSplash());
        mainClassConfigurationDetails = new MainClassConfigurationDetails(extension.getMainClass());
        helperTaskConfigurationDetails = new HelperTaskConfigurationDetails(extension.getMeta());

        // set the group
        // the gradle group from the map parameter overload gets set after the config closure is called.
        setGroup(LAUNCH4J_HELPER_TASK_GROUP);

        // the gradle description from the map parameter overload gets set before the config closure is called.
        applyMeta();

        // todo create the dependencies...
    }

    // todo declare task inputs if any...
    // todo declare task outputs

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
     * Public api to configure the helper task details.
     *
     * @param config the closure to apply.
     */
    public void meta(@DelegatesTo(value = HelperTaskConfigurationDetails.class, strategy = DELEGATE_FIRST) Closure config) {
        config.setDelegate(helperTaskConfigurationDetails);
        config.setResolveStrategy(DELEGATE_FIRST);
        config.call();

        // apply changes
        if (helperTaskConfigurationDetails.getGenerateDescription()) {
            setDescription(helperTaskConfigurationDetails.getDescriptionTemplate());
        } else {
            setDescription(helperTaskConfigurationDetails.getDescription());
        }
    }

    /**
     * Apply the information from the Launch4jHelperTaskConfiguration
     */
    private void applyMeta() {
        // apply details of description
        if (helperTaskConfigurationDetails.getGenerateDescription()) {
            setDescription(helperTaskConfigurationDetails.getDescriptionTemplate());
        } else {
            setDescription(helperTaskConfigurationDetails.getDescription());
        }
    }

    /**
     * Set the description for this task using a literal description or a template.
     * If there is no launch4jTask associated to this task, the fallback description will be used.
     *
     * @param description either the literal description, or a template,
     *                    which can accept variables.  See {@link HelperTaskConfigurationDetails}
     *                    and {@link Launch4jHelperExtension#defaultHelperTaskConfigDtls()}.
     */
    @Override
    public void setDescription(String description) {

        // only attempt to generate a description from a template
        // if we have been instructed to attempt it.
        if (helperTaskConfigurationDetails.getGenerateDescription()) {

            // update the description template, because we're "pretending" the description
            // is the same thing as the template, so editing the one should update the other.
            // todo this is getting set when it shouldn't be...
            helperTaskConfigurationDetails.setDescriptionTemplate(description);

            // if there is no task name, use the fallback description
            if (launch4jTask != null) {
                String taskName = launch4jTask.getName();
                description = fillInLaunch4jTemplateTaskName(
                    helperTaskConfigurationDetails.getDescriptionTemplate(),
                    helperTaskConfigurationDetails.getLaunch4jTaskVariable(),
                    taskName);
            } else {
                description = helperTaskConfigurationDetails.getDescription();
            }
        }

        super.setDescription(description);
    }

    static String fillInLaunch4jTemplateTaskName(String template, String taskNameVariable, String taskName) {
        return template.replace(taskNameVariable, taskName);
    }

    /**
     * @return the associated launch4jTask.
     */
    public Task getLaunch4jTask() {
        return launch4jTask;
    }

    /**
     * Set the associated launch4jTask and update the task description,
     * if that behavior is allowed, per {@link HelperTaskConfigurationDetails}
     *
     * todo this logic is messed up!  It's tough to handle state!
     *
     * @param task set the associated launch4jTask.
     *                     Right now, accepts only launch4jLibraryTask.
     */
    public void setLaunch4jTask(Task task) {
        Launch4jHelperExtension.validateTaskIsLaunch4jLibraryTask(task);
        this.launch4jTask = (Launch4jLibraryTask) task;

        if (helperTaskConfigurationDetails.getGenerateDescription()) {
            setDescription(helperTaskConfigurationDetails.getDescriptionTemplate());
        }
    }
}
