# gradle.resource.copy.plugin
gradle copy resources plugin



buildscript {

    repositories {
        maven { url "https://clojars.org/repo" }
    }

    dependencies {
        classpath 'com.steammachine.org:gradle.copy.plugin:0.9.0'
    }
}

apply plugin: 'com.steammachine.org.gradle.properties.plugins'
