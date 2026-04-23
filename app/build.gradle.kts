plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.ksp)
}

android {
    namespace = "com.example.moviesfeed"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.moviesfeed"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
    }
}

configurations.all {
    exclude(group = "com.intellij", module = "annotations")
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.bundles.koin)
    implementation(libs.bundles.networking)

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    testImplementation(libs.junit)
    testImplementation(libs.truth)

    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation("androidx.navigation:navigation-compose:2.9.7")
    implementation("androidx.compose.material:material-icons-extended")

    // Room
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    ksp(libs.room.compiler)

    // Paging
    implementation(libs.paging.runtime)
    implementation(libs.paging.compose)

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.9.7")
    implementation("org.jetbrains:annotations:23.0.0")
}
