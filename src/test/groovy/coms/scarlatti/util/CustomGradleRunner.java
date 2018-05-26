package coms.scarlatti.util;

import org.gradle.api.invocation.Gradle;
import org.gradle.api.tasks.Copy;
import org.gradle.testkit.runner.internal.DefaultGradleRunner;
import org.gradle.util.GFileUtils;

import java.io.File;
import java.io.InputStream;
import java.nio.file.*;
import java.util.Objects;
import java.util.Scanner;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Saturday, 5/26/2018
 */
public class CustomGradleRunner extends DefaultGradleRunner {

    private String sourceDir;
    private String targetDir;
    private String buildFileContents;
    private String targetBuildFile;
    private String name;
    private String projectDir;

    // hide constructor
    private CustomGradleRunner(String name) {
        this.name = name;
        setup(name);
    }

    public static CustomGradleRunner create() {
        throw new IllegalStateException("Must use create(name) method");
    }

    public static CustomGradleRunner create(String name) {
        Objects.requireNonNull(name, "Test Project name required.");
        return new CustomGradleRunner(name);
    }

    private void setup(String name) {
        projectDir = System.getProperty("user.dir");
        Path targetDir = Paths.get(projectDir, "build", "test-projects", name);
        Path targetBuildFile = Paths.get(targetDir.toString(), "build.gradle");
        this.targetDir = targetDir.toString();
        this.targetBuildFile = targetBuildFile.toString();

        try {
            if (Files.exists(targetDir)) {
                GFileUtils.deleteDirectory(targetDir.toFile());
            }
            Files.createDirectories(targetDir);
            withProjectDir(targetDir.toFile());
        } catch (Exception e) {
            throw new RuntimeException("Unable to created directories for test project at " + targetDir, e);
        }
    }

    /**
     * Use a directory as source for a test project.  Use a relative path to the project dir.
     *
     * @param sourceDir the path to the test project dir, relative to the root project dir.
     * @return this runner, for chaining.
     */
    public CustomGradleRunner fromProjectDir(String sourceDir) {
        Objects.requireNonNull(sourceDir, "Must provide source dir");
        // if there is a source dir copy the sourceDir into the target dir
        GFileUtils.copyDirectory(Paths.get(projectDir, sourceDir).toFile(), Paths.get(targetDir).toFile());
        this.sourceDir = sourceDir;
        return this;
    }

    public CustomGradleRunner withBuildFileContents(String buildFileContents) {
        try {
            // create the build file
            Files.write(Paths.get(targetBuildFile), buildFileContents.getBytes());
            this.buildFileContents = buildFileContents;
            return this;
        } catch (Exception e) {
            throw new RuntimeException("Unable to create build file content for test project at " + targetDir +
                "Content was: " + buildFileContents, e);
        }
    }

    public CustomGradleRunner appendBuildFileContents(String buildFileContents) {
        try {
            String newContents = System.lineSeparator() + System.lineSeparator() + buildFileContents;
            Files.write(Paths.get(targetBuildFile), newContents.getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
            return this;
        } catch (Exception e) {
            throw new RuntimeException("Unable to create append file content for test project at " + targetDir +
                "New content was: " + buildFileContents, e);
        }
    }

    public CustomGradleRunner withBuildFile(InputStream inputStream) {
        return withBuildFileContents(new Scanner(inputStream).useDelimiter("\\Z").next());
    }

    public CustomGradleRunner appendBuildFile(InputStream inputStream) {
        return appendBuildFileContents(new Scanner(inputStream).useDelimiter("\\Z").next());
    }

    public CustomGradleRunner withBuildFileFromResource(String resourceName) {
        return withBuildFile(getClass().getResourceAsStream(resourceName));
    }

    public CustomGradleRunner appendBuildFileFromResource(String resourceName) {
        return appendBuildFile(getClass().getResourceAsStream(resourceName));
    }

    public CustomGradleRunner withTask(String task) {
        return (CustomGradleRunner) withArguments(task, "--stacktrace");
    }
}
