# Launch4J Gradle Plugin

This project facilitates releases with Launch4J.

It simplifies building an executable by using sensible default properties so that a release can be built by placing resource files in a distribution directory.

The plugin makes available a `Launch4JDistributionTask`.
This task can take a variety of configurable inputs, and outputs the
two artifacts side-by-side in a single specified directory
- yourApp.exe
- lib (dir)

### Opinionated Launch4j Settings

The values configurable within the launch4j extension along with their defaults are:

| Property Name | Default Value | Comment |
|---------------|---------------|---------|
| String outputDir | "launch4j" | This is the plugin's working path relative to `$buildDir`. Use the distribution plugin or a custom implementation to copy necessary files to an output location instead of adjusting this property.|
| String libraryDir | "lib" | |
| Object copyConfigurable | | |
| Set&lt;String&gt; classpath| [] | Use this property to override the classpath or configure it on you own if the `copyConfigurable` does not provide the results you want |
| String xmlFileName | "launch4j.xml" | |
| String mainClassName | | |
| boolean dontWrapJar | false | |
| String headerType | "gui" | |
| String jar | "lib/"+project.tasks[jar].archiveName or<br> "", if the JavaPlugin is not loaded | |
| String outfile | project.name+'.exe' | |
| String errTitle | "" | |
| String cmdLine | "" | |
| String chdir | '.' | |
| String priority | 'normal' | |
| String downloadUrl | "http://java.com/download" | |
| String supportUrl | "" | |
| boolean stayAlive | false | |
| boolean restartOnCrash | false | |
| String manifest | "" | |
| String icon | "" | A relative path from the outfile or an absolute path to the icon file. If you are uncertain use "${projectDir}/path/to/icon.ico" |
| String version | project.version | |
| String textVersion | project.version | |
| String copyright | "unknown" | |
| String companyName | "" | |
| ~~String description~~| project.name | deprecated use `fileDescription` instead |
| String fileDescription | project.name | |
| String productName | project.name | |
| String internalName | project.name | |
| String trademarks | | |
| String language | "ENGLISH_US" | |
| ~~String opt~~ | "" | deprecated use `jvmOptions` instead |
| Set&lt;String&gt; jvmOptions | [ ] | |
| String bundledJrePath | | |
| boolean bundledJre64Bit | false | |
| boolean bundledJreAsFallback | false | |
| String jreMinVersion | project.targetCompatibility or<br> the current java version,<br> if the property is not set | |
| String jreMaxVersion | | |
| String jdkPreference | "preferJre" | |
| String jreRuntimeBits | "64/32" | |
| Set&lt;String&gt; variables | [ ] | |
| String mutexName | | |
| String windowTitle | | |
| String messagesStartupError | | |
| String messagesBundledJreError | | |
| String messagesJreVersionError | | |
| String messagesLauncherError | | |
| String messagesInstanceAlreadyExists | | |
| Integer initialHeapSize | | |
| Integer initialHeapPercent | | |
| Integer maxHeapSize | | |
| Integer maxHeapPercent | | |
| String splashFileName | | A relative path from the outfile or an absolute path to the bmp splash file. |
| boolean splashWaitForWindows | true | |
| Integer splashTimeout | 60 | |
| boolean splashTimeoutError | true | |