package com.scarlatti.gradle.launch4j.gen2.task;

import com.scarlatti.gradle.launch4j.gen2.details.ResourcesConfigurationDetails;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.nio.file.Files;

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
     * Determine conventional settings, such as:
     * <p>
     * should we infer a gui header type based on the presence of splash...
     * or if header type is explicitly set as console, to ignore a splash, even if present.
     * <p>
     * todo we may still need this to do some logic relating to headerType and
     * whether or not to apply a splash, or infer header type based on presence of splash.
     *
     * todo and we will need to handle what to do when a new iteration of the build results
     * in NOT generating an icon.  The launch4jTask should not specify the icon (unless
     * it was actually explicitly configured that way by the task author.
     */
    @TaskAction
    public void configureFromResources() {
        // set icon file as input for supplyIcon task
        File icon = helperTask.getIcon().getResolve().get();
        helperTask.getIcon().setIcon(nullifyIfNotExists(icon));

        // todo implement "should we even try to produce a splash?"

        // look at the headerType first.
        // ACTUALLY, we don't care.  We would assume that the user put a splash
        // in the directory, so that's what they want.  If they don't want to use a splash
        // they can disable the splash task.

        // set splash file as input for supplySplash task
        // if splash file is present change header type to gui if it is not already set.
        File splash = helperTask.getSplash().getResolve().get();
        helperTask.getSplash().setSplash(nullifyIfNotExists(splash));

        // todo update the launch4jTask with a "gui" headerType.
        // if we actually produced a splash, that is...
    }

    private File nullifyIfNotExists(File file) {
        if (Files.exists(file.toPath())) {
            return file;
        }

        return null;
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
