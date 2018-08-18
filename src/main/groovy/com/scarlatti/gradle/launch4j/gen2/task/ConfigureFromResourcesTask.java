package com.scarlatti.gradle.launch4j.gen2.task;

import com.scarlatti.gradle.launch4j.gen2.details.IconConfigurationDetails;
import com.scarlatti.gradle.launch4j.gen2.details.ResourcesConfigurationDetails;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Wednesday, 8/15/2018
 */
public class ConfigureFromResourcesTask extends DefaultTask {

    private Launch4jHelperTask helperTask;

    private File resourcesDir;
    private String launch4jPropertiesFileName;
    private String iconFileName;
    private String splashFileName;
    private String manifestFileName;

    public void configureFromResourcesDtls(ResourcesConfigurationDetails details) {
        resourcesDir = details.getResourcesDir();
        launch4jPropertiesFileName = details.getLaunch4jPropertiesFileName();
        iconFileName = details.getIconFileName();
        splashFileName = details.getSplashFileName();
        manifestFileName = details.getManifestFileName();
    }

    /**
     * This task would do the following:
     * <p>
     * - read launchj.properties and apply to the launch4j task
     * - if headerType turns out to use splash, configure generateSplashTask
     * - configure generateIconTask
     * - configure generateManifestTask
     *
     * todo we may still need this to do some logic relating to headerType and whether or not to apply a splash, or infer header type based on presence of splash.
     */
    @TaskAction
    public void configureFromResources() {
        configureSupplyIconTask();
    }

    /**
     * Configure the associated SupplyIconTask.
     * <p>
     * If we are not set up to configure an icon, disable the task.
     * <p>
     * If we are set up to generate at all:
     * Resolve an icon using the resolution strategy.
     */
    private void configureSupplyIconTask() {
        SupplyIconTask task = helperTask.getSupplyIconTask();
        if (helperTask.getIcon().getEnabled()) {

            // this stuff will be done earlier at the initial task creation...
            SupplyIconTask config = helperTask.getIcon();
            File icon = config.getResolve().resolve(helperTask);
            task.setIcon(icon);
            task.setText(config.getText());
            task.setAutoGenerate(config.getAutoGenerate());
            task.setDestination(new File("build/launch4j/#taskName/resources/icon.ico"));  // todo get the launch4j resources directory
        }
        else {
            task.setEnabled(false);
        }
    }

    public Launch4jHelperTask getHelperTask() {
        return helperTask;
    }

    public void setHelperTask(Launch4jHelperTask helperTask) {
        this.helperTask = helperTask;
    }

    public File getResourcesDir() {
        return resourcesDir;
    }

    public void setResourcesDir(File resourcesDir) {
        this.resourcesDir = resourcesDir;
    }

    public String getLaunch4jPropertiesFileName() {
        return launch4jPropertiesFileName;
    }

    public void setLaunch4jPropertiesFileName(String launch4jPropertiesFileName) {
        this.launch4jPropertiesFileName = launch4jPropertiesFileName;
    }

    public String getIconFileName() {
        return iconFileName;
    }

    public void setIconFileName(String iconFileName) {
        this.iconFileName = iconFileName;
    }

    public String getSplashFileName() {
        return splashFileName;
    }

    public void setSplashFileName(String splashFileName) {
        this.splashFileName = splashFileName;
    }

    public String getManifestFileName() {
        return manifestFileName;
    }

    public void setManifestFileName(String manifestFileName) {
        this.manifestFileName = manifestFileName;
    }
}
