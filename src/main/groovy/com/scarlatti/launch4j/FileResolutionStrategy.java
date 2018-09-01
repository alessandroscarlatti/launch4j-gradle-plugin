package com.scarlatti.launch4j;

import com.scarlatti.launch4j.task.Launch4jHelperTask;

import java.io.File;

public interface FileResolutionStrategy {
    File resolve(Launch4jHelperTask launch4jHelperTask);
}