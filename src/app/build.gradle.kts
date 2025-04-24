plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
}

android {
  namespace = "dev.trindadedev.eltrinity"
  compileSdk = 34

  defaultConfig {
    applicationId = "dev.trindadedev.eltrinity"
    minSdk = 21
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"
    
    vectorDrawables.useSupportLibrary = true
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  signingConfigs {
    create("release") {
      // temporary keystore
      storeFile = file(layout.buildDirectory.dir("../release_key.jks"))
      storePassword = "release_temp"
      keyAlias = "release_temp"
      keyPassword = "release_temp"
    }
    getByName("debug") {
      storeFile = file(layout.buildDirectory.dir("../testkey.keystore"))
      storePassword = "testkey"
      keyAlias = "testkey"
      keyPassword = "testkey"
    }
  }
    
  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
      signingConfig = signingConfigs.getByName("release")
    }
    debug {
      applicationIdSuffix = ".debug"
    }
  }

  buildFeatures {
    viewBinding = true
  }

  kotlin {
    jvmToolchain(17)
  }
}

dependencies {
  implementation(files("libs/beanshell.jar"))
  implementation("androidx.constraintlayout:constraintlayout:2.2.1")
  implementation("com.google.android.material:material:1.13.0-alpha13")
  implementation("androidx.appcompat:appcompat:1.7.0")
  implementation("com.google.code.gson:gson:2.13.1")
  implementation(project(":c2bsh"))
}