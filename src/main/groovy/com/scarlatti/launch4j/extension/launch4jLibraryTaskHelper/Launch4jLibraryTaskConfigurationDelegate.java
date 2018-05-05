package com.scarlatti.launch4j.extension.launch4jLibraryTaskHelper;

public class Launch4jLibraryTaskConfigurationDelegate {

    private String outputDir;
    private String resourcesDir;
    private String icon;
    private String manifest;
    private String exeName;

    /**
     * Translate the currently configured task fields into
     * internal task props.
     *
     * TODO move this method to another class.  It is not a concern of this class.
     *
     * @return the internal task props from the currently configured task fields.
     */
    private Launch4jLibraryTaskProps buildInternalProps() {
        // TODO build internal props
        return new Launch4jLibraryTaskProps();
    }

    @Override
    public String toString() {
        return "Launch4jLibraryTaskConfigurationDelegate{" +
            "outputDir='" + outputDir + '\'' +
            ", resourcesDir='" + resourcesDir + '\'' +
            ", icon='" + icon + '\'' +
            ", manifest='" + manifest + '\'' +
            ", exeName='" + exeName + '\'' +
            '}';
    }

    // =================================================================
    // Getters and Setters
    // =================================================================

    public String getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public String getResourcesDir() {
        return resourcesDir;
    }

    public void setResourcesDir(String resourcesDir) {
        this.resourcesDir = resourcesDir;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getManifest() {
        return manifest;
    }

    public void setManifest(String manifest) {
        this.manifest = manifest;
    }

    public String getExeName() {
        return exeName;
    }

    public void setExeName(String exeName) {
        this.exeName = exeName;
    }
}