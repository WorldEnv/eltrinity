plugins {
  id("com.android.library") version "8.10.0"
  id("org.jetbrains.kotlin.android") version "2.1.21"
  `maven-publish`
}

group = "dev.trindadedev"
version = "1.0.0"

android {
  namespace = "dev.trindadedev.c2bsh"
  compileSdk = 34
  buildToolsVersion = "34.0.0"

  defaultConfig {
    minSdk = 21

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
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  kotlin {
    jvmToolchain(17)
  }
}

afterEvaluate {
  publishing {
    publications {
      register("mavenRelease", MavenPublication::class) {
        groupId = "dev.trindadedev"
        artifactId = "c2bsh"
        version = "1.0.0"
        from(components["release"])
      }
    }
  }
}
