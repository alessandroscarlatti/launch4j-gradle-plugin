# Launch4J Gradle Plugin

This project facilitates releases with Launch4J.

It simplifies building an executable by using sensible default properties so that a release can be built by placing resource files in a distribution directory.

The plugin makes available a `Launch4JDistributionTask`.
This task can take a variety of configurable inputs, and outputs the
two artifacts side-by-side in a single specified directory
- yourApp.exe
- lib (dir)