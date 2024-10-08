plugins {
    id("com.android.application")
    id("com.google.gms.google-services") //추가
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.dayj"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.dayj"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField(
            "String",
            "GOOGLE_CLIENT_ID",
            "\"242647061672-neilee1h2i1dr2j778dfml46ao42trok.apps.googleusercontent.com\""
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("com.google.android.gms:play-services-auth:20.5.0")

    implementation("androidx.navigation:navigation-compose:2.7.7")

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
    implementation("androidx.activity:activity-compose:1.9.1")
    implementation(platform("androidx.compose:compose-bom:2024.06.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.06.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation("com.thedeanda:lorem:2.2")
    implementation(platform("com.google.firebase:firebase-bom:33.3.0")) //추가
    implementation("com.google.firebase:firebase-analytics") //추가


    val hiltVersion = "2.51"
    val hiltNavigationCompose = "1.2.0"
    val viewModelComposeVersion = "2.8.4"
    val lifecycleVersion = "2.8.4"

    implementation("com.google.dagger:hilt-android:$hiltVersion")
    implementation("androidx.hilt:hilt-navigation-compose:${hiltNavigationCompose}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${lifecycleVersion}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:${viewModelComposeVersion}")
    ksp("com.google.dagger:hilt-android-compiler:$hiltVersion")
    ksp("com.google.dagger:hilt-compiler:$hiltVersion")

    implementation("com.squareup.moshi:moshi-kotlin:1.12.0")
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")


    implementation("com.github.commandiron:WheelPickerCompose:1.1.11")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")


    implementation("androidx.datastore:datastore-preferences:1.1.1")
    implementation("androidx.datastore:datastore-preferences-core:1.1.1")

}