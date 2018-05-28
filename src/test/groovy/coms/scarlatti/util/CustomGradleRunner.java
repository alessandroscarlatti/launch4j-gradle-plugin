package coms.scarlatti.util;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.internal.DefaultGradleRunner;
import org.gradle.util.GFileUtils;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.function.Consumer;

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
        Path targetDir = Paths.get(this.targetDir);

        try {
            if (Files.exists(targetDir)) {
                GFileUtils.deleteDirectory(targetDir.toFile());
            }

            Files.createDirectories(targetDir);
            GFileUtils.copyDirectory(Paths.get(projectDir, sourceDir).toFile(), targetDir.toFile());
            this.sourceDir = sourceDir;
            return this;
        } catch (Exception e) {
            throw new RuntimeException("Error building test project from souroce dir " + sourceDir, e);
        }

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

    @Override
    public BuildResult build() {
        logTargetDir();
        BuildResult buildResult = super.build();
        System.out.printf(">>> Gradle Build result (expect success): %n%n%s%n%n", buildResult.getOutput());
        return buildResult;
    }

    @Override
    public BuildResult buildAndFail() {
        logTargetDir();
        BuildResult buildResult = super.buildAndFail();
        System.out.printf(">>> Gradle Build result (expect failure): %n%n%s%n%n", buildResult.getOutput());
        return buildResult;
    }

    public BuildResult build(String... args) {
        withArguments(addStacktraceArgIfNecessary(args));
        return build();
    }

    public BuildResult buildAndFail(String... args) {
        withArguments(addStacktraceArgIfNecessary(args));
        return buildAndFail();
    }

    private String[] addStacktraceArgIfNecessary(String[] args) {
        List<String> newArgs = new ArrayList<>(Arrays.asList(args));
        if (!newArgs.contains("--stacktrace")) {
            newArgs.add("--stacktrace");
        }
        return newArgs.toArray(new String[]{});
    }

    private void logTargetDir() {
        System.out.println(">>> Test Project deployed at \"" + targetDir + "\"");
    }
}
