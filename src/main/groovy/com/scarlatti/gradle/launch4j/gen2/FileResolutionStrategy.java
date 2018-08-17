package com.scarlatti.gradle.launch4j.gen2;

import com.scarlatti.gradle.launch4j.gen2.task.Launch4jHelperTask;

import java.io.File;

public interface FileResolutionStrategy {
    File resolve(Launch4jHelperTask launch4jHelperTask);
}