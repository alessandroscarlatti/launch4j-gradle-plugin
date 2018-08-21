package com.scarlatti.gradle.launch4j.gen2.task;

import com.scarlatti.gradle.launch4j.gen2.details.IconConfigurationDetails;
import com.scarlatti.util.ImageGenerator;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Random;
import java.util.function.Supplier;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Tuesday, 8/14/2018
 */
public class SupplyIconTask extends DefaultTask {

    @Optional
    @InputFile
    private File icon;

    /**
     * Whether configuration of icon is enabled at all, whether by file or automatic generation.
     */
    @Input
    private boolean autoGenerate;

    /**
     * The name to use when generating an icon.
     */
    @Optional
    @Input
    private String text;

    @OutputFile
    private File destination;

    /**
     * this task should be blind to the HelperTask
     *
     * this won't be invoked during this task,
     * but it should be invoked before the task inputs/outputs are evaluated.
     *
     * this probably means that we need to encapsulate
     * the FileResolutionStrategy inside a {@link Supplier<File>}
     */
    @Internal
    private Supplier<File> resolve;

    @Internal
    private boolean ran;
    @Internal
    private boolean suppliedIcon;

    /**
     * Configure this task from a PropertiesConfigurationDetails instance.
     * Called after this task has been created.
     */
    public void configureFromIconConfigDtls(IconConfigurationDetails details) {
        autoGenerate = details.getAutoGenerate();
        text = details.getText();
    }

    /**
     * Move the input icon file to the destination,
     * or if it does not exist, generate an icon.
     */
    @TaskAction
    public void generateIcon() {
        ran = true;
        if (icon != null) {
            try {
                Files.copy(icon.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
                suppliedIcon = true;
            }
            catch (Exception e) {
                throw new RuntimeException("Error copying icon " + icon + " to " + destination, e);
            }
        }
        else if (autoGenerate) {
            // if text is null generate a random string
            if (text == null) {
                text = String.valueOf(Math.abs(new Random(System.currentTimeMillis()).nextInt()));
            }

            ImageGenerator.generateIconFileForStringHash(destination.toPath(), text);
            suppliedIcon = true;
        }

        // todo somehow we need to "update" the launch4jTask to let it know that we have or haven't
        // actually created an icon.
        // Because if autogenerate == false and no icon was provided, that means that we
        // should definitely not modify the launch4j icon property.
        // but if we did make an icon, we should configure the launch4j task appropriately.
        //
        // If there is already a value for the launch4j icon property, we don't care.
        // if the user has set it, that is their concern.
        // todo we could use a doLast clause that is added by the helperTask.
        // this will keep this task free of any launch4j constructs.
        // however, we would need to access some kind of field or property on this task
        // to know what was the result.  That's because an icon may already exist at the
        // "destination", but we didn't ACTUALLY "generate" it this particular build.
        // So we wouldn't want to get that mixed up as looking like we had actually generated it
        // when we hadn't.
    }

    public boolean shouldSupplyIcon() {
        if (!isEnabled())
            return false;

        if (ran) {
            return suppliedIcon;
        }
        else {
            return icon != null || autoGenerate;
        }
    }

    public File getDestination() {
        return destination;
    }

    public void setDestination(File destination) {
        this.destination = destination;
    }

    public File getIcon() {
        return icon;
    }

    public void setIcon(File icon) {
        this.icon = icon;
    }

    public Supplier<File> getResolve() {
        return resolve;
    }

    public void setResolve(Supplier<File> resolve) {
        this.resolve = resolve;
    }

    public boolean getAutoGenerate() {
        return autoGenerate;
    }

    public void setAutoGenerate(boolean autoGenerate) {
        this.autoGenerate = autoGenerate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
