// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.4" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.dagger.hilt.android") version "2.51" apply false
    id("org.jetbrains.kotlin.jvm") version "1.9.23"
    id("com.google.devtools.ksp") version "1.9.23-1.0.20"
    id("com.google.gms.google-services") version "4.4.2" apply false //추가
}