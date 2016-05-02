# Android-DBVarna

## Requirements

* JDK 7 or later to build with annotation processors
* Android API level 15 to use

## Getting Started

First, you need to include the  `android-apt` plugin in classpath to enable annotation processing in android projects.

```groovy
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
    }
}

apply plugin: 'com.neenbedankt.android-apt'

repositories {
    jcenter()
}
```
