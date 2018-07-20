package com.scarlatti.gradle.launch4j.gen2.details;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Thursday, 7/19/2018
 *
 * Details regarding launch4jHelperTasks.
 */
public class HelperTaskConfigurationDetails {

    /**
     * Should we generate descriptions using template when setting
     * the helper's associated launch4jTask?
     *
     * And should we interpret a custom description as a template?
     */
    private boolean generateDescription;

    /**
     * The description template to use for the helper task.
     */
    private String descriptionTemplate;

    /**
     * The variable to use to denote the launch4jTask name
     * associated to the helper task.
     */
    private String launch4jTaskVariable;

    public HelperTaskConfigurationDetails() {
    }

    public HelperTaskConfigurationDetails(HelperTaskConfigurationDetails other) {
        this.generateDescription = other.generateDescription;
        this.descriptionTemplate = other.descriptionTemplate;
        this.launch4jTaskVariable = other.launch4jTaskVariable;
    }

    public boolean getGenerateDescription() {
        return generateDescription;
    }

    public void setGenerateDescription(boolean generateDescription) {
        this.generateDescription = generateDescription;
    }

    public String getDescriptionTemplate() {
        return descriptionTemplate;
    }

    public void setDescriptionTemplate(String descriptionTemplate) {
        this.descriptionTemplate = descriptionTemplate;
    }

    public String getLaunch4jTaskVariable() {
        return launch4jTaskVariable;
    }

    public void setLaunch4jTaskVariable(String launch4jTaskVariable) {
        this.launch4jTaskVariable = launch4jTaskVariable;
    }
}
