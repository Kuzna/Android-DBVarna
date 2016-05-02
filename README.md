# Android-DBVarna

[![Release](https://jitpack.io/v/Kuzna/Android-DBVarna.svg)](https://github.com/Kuzna/Android-DBVarna)
[![GitHub license](https://img.shields.io/github/license/mashape/apistatus.svg)](https://github.com/Kuzna/Android-DBVarna/blob/master/LICENSE.md)

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
