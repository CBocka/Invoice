plugins {
    id("com.android.dynamic-feature")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs")
}
android {
    namespace = "com.hamilton.account"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    implementation(project(":app"))
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.fragment:fragment-ktx:1.6.1")
    implementation(project(":infrastructure:firebase"))
    testImplementation("junit:junit:4.13.2")
    implementation(project(mapOf("path" to ":domain:invoice")))
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //Implementar la navegación por módulos
    implementation ("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation ("androidx.navigation:navigation-ui-ktx:2.3.5")
    implementation("com.airbnb.android:lottie:6.2.0")

    implementation("androidx.room:room-ktx:2.6.1")
    //Convertir un Flow a LiveData

    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")
}