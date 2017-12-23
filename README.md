# gradle.resource.copy.plugin
gradle copy resources plugin

[![Build Status](https://travis-ci.org/DarrylZero/gradle.resource.copy.plugin.svg?branch=development)]

                                                                                                                           
```groovy



```

buildscript {

    repositories {
        maven { url "https://clojars.org/repo" }
    }

    dependencies {
        classpath 'com.steammachine.org:gradle.copy.plugin:0.9.0'
    }
}

apply plugin: 'com.steammachine.org.gradle.properties.plugins'


When plugin is applied all files in the codebase that contain 'resource' in its path 
are copied into target/classes directory.

