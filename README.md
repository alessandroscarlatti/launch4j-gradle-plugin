# Launch4j Gradle Plugin 

[![Travis CI](https://travis-ci.com/alessandroscarlatti/launch4j-gradle-plugin.svg?branch=master)](https://travis-ci.com/alessandroscarlatti/launch4j-gradle-plugin "Travis CI")
[![Bintray Download](https://api.bintray.com/packages/alessandroscarlatti/maven/launch4j-gradle-plugin/images/download.svg)](https://bintray.com/alessandroscarlatti/maven/launch4j-gradle-plugin/_latestVersion "Bintray Download")

The [Launch4j Gradle plugin](https://github.com/TheBoegl/gradle-launch4j) made even simpler.

*You want to quickly package your Java project as a great-looking exe.*  This is easy with the `Launch4jHelper` Gradle plugin.
Building an executable is now only a few lines of code, thanks to a few sensible conventions and defaults.

# How to Use

## Pull in the dependency.
```
buildscript {
    repositories {
        jcenter()  // for plugin dependencies
        maven {
            url "https://dl.bintray.com/alessandroscarlatti/maven"
        }
    }
    dependencies {
        classpath 'com.scarlatti:launch4j-gradle-plugin:2.0.2'
    }
}

apply plugin: 'java'  // and other plugins...
apply plugin: 'com.scarlatti.launch4j-helper'
```

## Configure your Exe.

```
import com.scarlatti.launch4j.task.Launch4jHelperTask

task helpCreateExe(type: Launch4jHelperTask) {
    launch4jTask(createExe)
}
```
Go ahead and run the `createExe` task. You should now have an exe waiting for you at `/build/launch4j/createExe/<project name>.exe`.

That's it.

## Congratulations! *Enjoy your Exe.*

## Things `Launch4jHelperPlugin` Configures for You

| Configuration | Details |
| ----- | ------|
| Icon  | Generates a Github avatar style icon, or you can provide your own.
| Splash  | Generates a Github avatar style splash, or you can provide your own.

## Add an Icon
The `Launch4jHelperTask` takes an opinionated view on providing resources, such as an icon.  Just provide an icon (.ico) file at `/icon.ico` in your project and the Launch4jHelper task will automatically use it for your exe.

## Add a Splash Screen
Provide a splash (.bmp only) file at `/splash.bmp` and the Launch4jHelper task will automatically use it for the exe.

## Expert Options
Of course, properties of the actual Launch4j task can still be configured as specified in the [Launch4j plugin documentation](https://github.com/TheBoegl/gradle-launch4j).