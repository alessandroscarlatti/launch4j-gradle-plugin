# Launch4J Gradle Plugin

![Travis CI](https://travis-ci.com/alessandroscarlatti/launch4j-gradle-plugin.svg?branch=master "Travis CI")
[ ![Bintray Download](https://api.bintray.com/packages/alessandroscarlatti/maven/launch4j-gradle-plugin/images/download.svg) ](https://bintray.com/alessandroscarlatti/maven/launch4j-gradle-plugin/_latestVersion "Bintray Download")

The [Launch4j Gradle plugin](https://github.com/TheBoegl/gradle-launch4j) made even simpler.

*You want to quickly package your jar as a great-looking exe.*  This is easy with the `Launch4jTemplateTask` Gradle task.
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
        classpath 'com.scarlatti:launch4j-gradle-plugin:1.0.1'
    }
}

apply plugin: 'java'  // and other plugins...
apply plugin: 'com.scarlatti.launch4j.integration'
```

## Configure an Exe.
If your build produces an executable jar this can be extremely simple.
```
import com.scarlatti.launch4j.Launch4jTemplateTask

task simpleExe(type: Launch4jTemplateTask) {
    exeName = 'simple'
}
```
Go ahead and run the `simpleExe` task. You should now have an exe waiting for you at `/build/launch4j/simpleExe/simple.exe`.

That's it.

## Congratulations! *Enjoy your Exe.*

## Add an Icon
The `Launch4jTemplateTask` takes an opinionated view on providing resources, such as an icon.  You may provide an icon (.ico) file at `/exe/icon.ico` and the task will automatically use it for the exe.

## Add a Splash Screen
You may provide an splash (.bmp only) file at `/exe/splash.bmp` and the task will automatically use it for the exe.

# Expert Options
Of course, properties of the actual Launch4j task can be configured.
```
task simpleExe(type: Launch4jTemplateTask) {

    // configure the actual launch4j task
    // more details at https://github.com/TheBoegl/gradle-launch4j
    config {
        companyName = "My Awesome Company"
    }
}
```

> The [Launch4j plugin documentation](https://github.com/TheBoegl/gradle-launch4j) provides details on all the configuration options available.
