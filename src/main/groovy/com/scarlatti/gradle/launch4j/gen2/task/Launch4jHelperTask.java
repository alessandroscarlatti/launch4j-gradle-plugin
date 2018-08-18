package com.scarlatti.gradle.launch4j.gen2.task;

import com.scarlatti.gradle.launch4j.gen2.Launch4jHelperExtension;
import com.scarlatti.gradle.launch4j.gen2.details.*;
import edu.sc.seis.launch4j.tasks.Launch4jLibraryTask;
import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import org.gradle.api.DefaultTask;
import org.gradle.api.Task;

import static com.scarlatti.gradle.launch4j.gen2.Launch4jHelperPlugin.LAUNCH4J_HELPER_EXTENSION_NAME;
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
    private PropertiesConfigurationDetails propertiesConfigurationDetails;
    private ResourcesConfigurationDetails resourcesConfigurationDetails;
    private HelperTaskConfigurationDetails helperTaskConfigurationDetails;

    private ConfigureLaunch4jFromPropertiesTask configurePropertiesTask;
    private ConfigureFromResourcesTask configureFromResourcesTask;
    private FindMainClassTask findMainClassTask;
    private SupplyIconTask supplyIconTask;
    private SupplySplashTask supplySplashTask;
    private SupplyManifestTask supplyManifestTask;

    /**
     * Create a new Launch4jHelperTask for a launch4jTask.
     * All configurations are copied from the launch4jHelper extension
     * as it exists at the time of this invocation.
     */
    public Launch4jHelperTask() {
        this.extension = (Launch4jHelperExtension) getProject().getExtensions().findByName(LAUNCH4J_HELPER_EXTENSION_NAME);

        // copy the details from the extension
        // todo create the tasks and
        iconConfigurationDetails = new IconConfigurationDetails(extension.getIcon());
        manifestConfigurationDetails = new ManifestConfigurationDetails(extension.getManifest());
        splashConfigurationDetails = new SplashConfigurationDetails(extension.getSplash());
        mainClassConfigurationDetails = new MainClassConfigurationDetails(extension.getMainClass());
        propertiesConfigurationDetails = new PropertiesConfigurationDetails(extension.getProperties());
        resourcesConfigurationDetails = new ResourcesConfigurationDetails(extension.getResources());
        helperTaskConfigurationDetails = new HelperTaskConfigurationDetails(extension.getMeta());

        applyMeta();
    }

    /**
     * Apply the information from the Launch4jHelperTaskConfiguration
     */
    private void applyMeta() {
        // apply details of description
        // the gradle description from the map parameter overload gets set before the config closure is called.
        if (helperTaskConfigurationDetails.getGenerateDescription()) {
            setDescription(helperTaskConfigurationDetails.getDescriptionTemplate());
        }
        else {
            setDescription(helperTaskConfigurationDetails.getDescription());
        }

        // apply details for group
        // the gradle group from the map parameter overload gets set after the config closure is called.
        setGroup(helperTaskConfigurationDetails.getGroup());
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
     * Set the group for this task, and also update the meta details.
     *
     * @param group the group to specify for this task.
     */
    @Override
    public void setGroup(String group) {
        helperTaskConfigurationDetails.setGroup(group);
        super.setGroup(group);
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
     * <p>
     * Crucially, this task sets itself as a dependency of the launch4j task
     * so that this task's outputs are available as inputs to the launch4j task.
     *
     * @param launch4jTask set the associated launch4jTask.
     *             Right now, accepts only launch4jLibraryTask.
     */
    public void setLaunch4jTask(Task launch4jTask) {
        Launch4jHelperExtension.validateTaskIsLaunch4jLibraryTask(launch4jTask);

        // remove old dependency, if any.
        if (this.launch4jTask != null) {
            getDependsOn().remove(this.launch4jTask);
        }

        if (helperTaskConfigurationDetails.getGenerateDescription()) {
            setDescription(helperTaskConfigurationDetails.getDescriptionTemplate());
        }

        // set up associated tasks.
        this.launch4jTask = (Launch4jLibraryTask) launch4jTask;
        removeAssociatedTasks();
        createConfigureLaunch4jPropertiesTask();
        createConfigureFromResourcesTask();
        createSupplyIconTask();
        createSupplySplashTask();
        createSupplyManifestTask();
        createFindMainClassTask();
        buildTasksDependencies();

        // todo declare task inputs if any...
        // todo declare task outputs
        // launch4j inputs are strings, assumes the file exists.
    }

    /**
     * Remove tasks associated to this helper task.
     * This includes tasks like the ConfigureFromResourcesTask and SupplyIconTask.
     */
    private void removeAssociatedTasks() {
        if (configureFromResourcesTask != null)
            getProject().getTasks().remove(configureFromResourcesTask);
        if (supplyIconTask != null)
            getProject().getTasks().remove(supplyIconTask);
        if (supplySplashTask != null)
            getProject().getTasks().remove(supplySplashTask);
        if (supplyManifestTask != null)
            getProject().getTasks().remove(supplyManifestTask);
    }

    private void buildTasksDependencies() {
        launch4jTask.dependsOn(this);
        this.dependsOn(supplyIconTask);
        this.dependsOn(supplySplashTask);
        this.dependsOn(supplyManifestTask);
        this.dependsOn(findMainClassTask);

        supplyIconTask.dependsOn(configureFromResourcesTask);
        supplySplashTask.dependsOn(configureFromResourcesTask);
        supplyManifestTask.dependsOn(configureFromResourcesTask);
        findMainClassTask.dependsOn(configureFromResourcesTask);

        configureFromResourcesTask.dependsOn(configurePropertiesTask);
    }

    /**
     * Set up the associated ConfigureLaunch4jPropertiesTask.
     *
     * Only call when we actually have a launch4j task associated to this HelperTask,
     * since the names and descriptions of these tasks depend on the launch4j task.
     *
     * todo set up dependencies
     */
    private void createConfigureLaunch4jPropertiesTask() {
        configurePropertiesTask = getProject().getTasks().create(
            supplyConfigureLaunch4jPropertiesTaskName(),
            ConfigureLaunch4jFromPropertiesTask.class
        );

        configurePropertiesTask.setDescription(supplyConfigureLaunch4jPropertiesTaskDescription());
        configurePropertiesTask.setGroup(helperTaskConfigurationDetails.getGroup());

        configurePropertiesTask.setHelperTask(this);
        configurePropertiesTask.configureFromPropertiesConfigDtls(extension.getProperties());
    }

    private String supplyConfigureLaunch4jPropertiesTaskName() {
        return getName() + "ConfigureProperties";
    }

    private String supplyConfigureLaunch4jPropertiesTaskDescription() {
        return "Configure properties source for the " + launch4jTask.getName() + " task.";
    }

    /**
     * Set up the associated ConfigureFromResourcesTask.
     * Only call when we actually have a launch4j task associated to this HelperTask,
     * since the names and descriptions of these tasks depend on the launch4j task.
     *
     * todo set up dependencies
     */
    private void createConfigureFromResourcesTask() {
        configureFromResourcesTask = getProject().getTasks().create(
            supplyConfigureResourcesTaskName(),
            ConfigureFromResourcesTask.class
        );
        configureFromResourcesTask.setDescription(supplyConfigureResourcesTaskDescription());
        configureFromResourcesTask.setGroup(helperTaskConfigurationDetails.getGroup());

        configureFromResourcesTask.setHelperTask(this);
        configureFromResourcesTask.configureFromResourcesDtls(resourcesConfigurationDetails);
    }

    private String supplyConfigureResourcesTaskName() {
        return getName() + "ConfigureResources";
    }

    private String supplyConfigureResourcesTaskDescription() {
        return "Configure resources for the " + launch4jTask.getName() + " task.";
    }

    /**
     * Set up the associated findMainClassTask.
     * Only call when we actually have a launch4j task associated to this HelperTask,
     * since the names and descriptions of these tasks depend on the launch4j task.
     *
     * todo set up dependencies
     */
    private void createFindMainClassTask() {
        findMainClassTask = getProject().getTasks().create(findMainClassTaskName(), FindMainClassTask.class);
        findMainClassTask.setDescription(findMainClassTaskDescription());
        findMainClassTask.setGroup(helperTaskConfigurationDetails.getGroup());
    }

    private String findMainClassTaskName() {
        return getName() + "FindMainClass";
    }

    private String findMainClassTaskDescription() {
        return "Find an main class for the " + launch4jTask.getName() + " task.";
    }

    /**
     * Set up the associated SupplyIconTask.
     * Only call when we actually have a launch4j task associated to this HelperTask,
     * since the names and descriptions of these tasks depend on the launch4j task.
     */
    private void createSupplyIconTask() {
        supplyIconTask = getProject().getTasks().create(supplyIconTaskName(), SupplyIconTask.class);
        supplyIconTask.setDescription(supplyIconTaskDescription());
        supplyIconTask.setGroup(helperTaskConfigurationDetails.getGroup());

        supplyIconTask.configureFromIconConfigDtls(iconConfigurationDetails);
        supplyIconTask.setResolve(() -> iconConfigurationDetails.getResolve().resolve(this));
    }

    private String supplyIconTaskName() {
        return getName() + "SupplyIcon";
    }

    private String supplyIconTaskDescription() {
        return "Supply an icon resource for the " + launch4jTask.getName() + " task.";
    }

    /**
     * Set up the associated SupplySplashTask.
     * Only call when we actually have a launch4j task associated to this HelperTask,
     * since the names and descriptions of these tasks depend on the launch4j task.
     *
     * todo set up dependencies
     */
    private void createSupplySplashTask() {
        supplySplashTask = getProject().getTasks().create(supplySplashTaskName(), SupplySplashTask.class);
        supplySplashTask.setDescription(supplySplashTaskDescription());
        supplySplashTask.setGroup(helperTaskConfigurationDetails.getGroup());
    }

    private String supplySplashTaskName() {
        return getName() + "SupplySplash";
    }

    private String supplySplashTaskDescription() {
        return "Supply a splash screen resource for the " + launch4jTask.getName() + " task.";
    }
    
    /**
     * Set up the associated SupplyManifestTask.
     * Only call when we actually have a launch4j task associated to this HelperTask,
     * since the names and descriptions of these tasks depend on the launch4j task.
     *
     * todo set up dependencies
     */
    private void createSupplyManifestTask() {
        supplyManifestTask = getProject().getTasks().create(supplyManifestTaskName(), SupplyManifestTask.class);
        supplyManifestTask.setDescription(supplyManifestTaskDescription());
        supplyManifestTask.setGroup(helperTaskConfigurationDetails.getGroup());
    }

    private String supplyManifestTaskName() {
        return getName() + "SupplyManifest";
    }

    private String supplyManifestTaskDescription() {
        return "Supply a manifest screen resource for the " + launch4jTask.getName() + " task.";
    }

    public SupplyIconTask getSupplyIconTask() {
        return supplyIconTask;
    }

    public ConfigureFromResourcesTask getConfigureFromResourcesTask() {
        return configureFromResourcesTask;
    }

    public SupplySplashTask getSupplySplashTask() {
        return supplySplashTask;
    }

    public SupplyManifestTask getSupplyManifestTask() {
        return supplyManifestTask;
    }

    /**
     * Public api to access the icon task.
     *
     * @return the icon task
     */
    public SupplyIconTask getIcon() {
        return supplyIconTask;
    }

    /**
     * Public api to access the manifest task.
     *
     * @return the manifest task.
     */
    public SupplyManifestTask getManifest() {
        return supplyManifestTask;
    }

    /**
     * Public api to access the splash task.
     *
     * @return the splash task.
     */
    public SupplySplashTask getSplash() {
        return supplySplashTask;
    }


    /**
     * Public api to access the main class task.
     *
     * @return the main class task.
     */
    public FindMainClassTask getMainClass() {
        return findMainClassTask;
    }

    /**
     * Public api to access the properties task.
     *
     * @return the properties task.
     */
    public ConfigureLaunch4jFromPropertiesTask getProperties() {
        return configurePropertiesTask;
    }

    /**
     * Public api to access the resources task.
     *
     * @return the resources task.
     */
    public ConfigureFromResourcesTask getResources() {
        return configureFromResourcesTask;
    }

    /**
     * Public api to access the helper task task.
     *
     * @return the helper task task.
     */
    public HelperTaskConfigurationDetails getMeta() {
        return helperTaskConfigurationDetails;
    }

    /**
     * Public api to configure the icon task.
     *
     * @param config the closure to apply.
     */
    public void icon(@DelegatesTo(value = SupplyIconTask.class, strategy = DELEGATE_FIRST) Closure config) {
        config.setDelegate(supplyIconTask);
        config.setResolveStrategy(DELEGATE_FIRST);
        config.call();
    }

    /**
     * Public api to configure the manifest task.
     *
     * @param config the closure to apply.
     */
    public void manifest(@DelegatesTo(value = SupplyManifestTask.class, strategy = DELEGATE_FIRST) Closure config) {
        config.setDelegate(supplyManifestTask);
        config.setResolveStrategy(DELEGATE_FIRST);
        config.call();
    }

    /**
     * Public api to configure the splash task.
     *
     * @param config the closure to apply.
     */
    public void splash(@DelegatesTo(value = SupplySplashTask.class, strategy = DELEGATE_FIRST) Closure config) {
        config.setDelegate(supplySplashTask);
        config.setResolveStrategy(DELEGATE_FIRST);
        config.call();
    }

    /**
     * Public api to configure the mainClass task.
     *
     * @param config the closure to apply.
     */
    public void mainClass(@DelegatesTo(value = FindMainClassTask.class, strategy = DELEGATE_FIRST) Closure config) {
        config.setDelegate(findMainClassTask);
        config.setResolveStrategy(DELEGATE_FIRST);
        config.call();
    }

    /**
     * Public api to configure the properties task.
     *
     * @param config the closure to apply.
     */
    public void properties(@DelegatesTo(value = ConfigureLaunch4jFromPropertiesTask.class, strategy = DELEGATE_FIRST) Closure config) {
        config.setDelegate(configurePropertiesTask);
        config.setResolveStrategy(DELEGATE_FIRST);
        config.call();
    }

    /**
     * Public api to configure the resources task.
     *
     * @param config the closure to apply.
     */
    public void resources(@DelegatesTo(value = ResourcesConfigurationDetails.class, strategy = DELEGATE_FIRST) Closure config) {
        config.setDelegate(resourcesConfigurationDetails);
        config.setResolveStrategy(DELEGATE_FIRST);
        config.call();
    }

    /**
     * Public api to configure the helper task.
     *
     * @param config the closure to apply.
     */
    public void meta(@DelegatesTo(value = HelperTaskConfigurationDetails.class, strategy = DELEGATE_FIRST) Closure config) {
        config.setDelegate(helperTaskConfigurationDetails);
        config.setResolveStrategy(DELEGATE_FIRST);
        config.call();

        applyMeta();
    }
}
