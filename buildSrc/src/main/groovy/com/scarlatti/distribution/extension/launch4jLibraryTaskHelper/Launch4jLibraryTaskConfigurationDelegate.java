package com.scarlatti.distribution.extension.launch4jLibraryTaskHelper;

public class Launch4jLibraryTaskConfigurationDelegate {

    private String outputDir;
    private String resourcesDir;
    private String exeName;

    @Override
    public String toString() {
        return "Launch4jLibraryTaskConfigurationDelegate{" +
            "outputDir='" + outputDir + '\'' +
            ", resourcesDir='" + resourcesDir + '\'' +
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

    public String getExeName() {
        return exeName;
    }

    public void setExeName(String exeName) {
        this.exeName = exeName;
    }
}