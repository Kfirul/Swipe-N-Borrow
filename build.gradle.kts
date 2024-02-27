buildscript {
    val agp_version by extra("8.2.1")
    val agp_version1 by extra("7.3.0")
    dependencies {
        classpath("com.google.gms:google-services:4.4.0")
        classpath ("com.google.gms:google-services:3.0.0")

    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.2" apply false
}