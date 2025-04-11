plugins {
  id("com.android.library")
}

android {
  namespace = "dev.trindadedev.c2bsh"
  compileSdk = 34

  defaultConfig {
    minSdk = 21

    consumerProguardFiles("consumer-rules.pro")

    ndk {
      abiFilters += listOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64")
    }
  }

  externalNativeBuild {
    cmake {
      path = file("CMakeLists.txt")
    }
  }

  buildTypes {
    release {
      isMinifyEnabled = true
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
}