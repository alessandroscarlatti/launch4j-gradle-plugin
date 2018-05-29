package com.scarlatti.util;

import org.gradle.plugins.ide.internal.tooling.GradleBuildBuilder;
import org.gradle.util.GFileUtils;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Scanner;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Friday, 5/25/2018
 */
public class TestProjectBuilder extends GradleBuildBuilder {

    private String sourceDir;
    private String targetDir;
    private String buildFileContents;
    private String targetBuildFile;
    private String name;

    // hide constructor
    protected TestProjectBuilder() {
    }

    public static TestProjectBuilder newTestProject(String name) {
        Objects.requireNonNull(name, "Test Project name required.");
        TestProjectBuilder builder = new TestProjectBuilder();
        builder.name = name;
        builder.generateTargetDirAndBuildFileNames(name);
        return builder;
    }

    private void generateTargetDirAndBuildFileNames(String name) {
        String projectDir = System.getProperty("user.dir");
        Path targetDir = Paths.get(projectDir, "build", "test-projects", name);
        Path targetBuildFile = Paths.get(targetDir.toString(), "build.gradle");
        this.targetDir = targetDir.toString();
        this.targetBuildFile = targetBuildFile.toString();
    }

    public TestProjectBuilder fromSourceDir(String sourceDir) {
        this.sourceDir = sourceDir;
        return this;
    }

    public TestProjectBuilder withBuildFileContents(String buildFileContents) {
        this.buildFileContents = buildFileContents;
        return this;
    }

    public TestProjectBuilder withBuildFile(InputStream inputStream) {
        this.buildFileContents = new Scanner(inputStream).useDelimiter("\\Z").next();
        return this;
    }

    public TestProjectBuilder withBuildFileFromResource(String resourceName) {
        return withBuildFile(getClass().getResourceAsStream(resourceName));
    }

    public TestProjectBuilder withBuildFileFromPath(String path) {
        try {
            this.buildFileContents = new String(Files.readAllBytes(Paths.get(path)));
            return this;
        } catch (Exception e) {
            throw new RuntimeException("Unable to read build file at " + path, e);
        }
    }

    public TestProjectBuilder withBuildFileFromPath(File file) {
        return withBuildFileFromPath(file.getAbsolutePath());
    }

//    // otherwise, a random dir when it's time to do the copy
//    TestProjectBuilder toTargetDir(String targetDir) {
//        this.targetDir = targetDir;
//        return this;
//    }

    public TestProject build() {
        // build the target dir
        // use /build/test-projects/${name}
        // do we wipe each time or sync?
        // actually, even "sync" is wiping.  So wipe it is.


        try {
            if (Files.exists(Paths.get(targetDir))) {
                GFileUtils.deleteDirectory(Paths.get(targetDir).toFile());
            }
            Files.createDirectories(Paths.get(targetDir));

            // if there is a source dir copy the sourceDir into the target dir
            if (sourceDir != null) {
                GFileUtils.copyDirectory(Paths.get(sourceDir).toFile(), Paths.get(targetDir).toFile());
            }

            // create the build file
            Files.write(Paths.get(targetBuildFile), buildFileContents.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("Unable to created directories for test project at " + targetDir, e);
        }

        TestProject testProject = new TestProject();
        testProject.setProjectDir(Paths.get(targetDir).toFile());
        testProject.setBuildFile(Paths.get(targetBuildFile).toFile());
        return testProject;
    }
}
