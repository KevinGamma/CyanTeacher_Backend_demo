plugins {
    kotlin("js") version "1.9.20"
    kotlin("plugin.serialization") version "1.9.20"
}

group = "com.eduassist"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Kotlin/JS
    implementation("org.jetbrains.kotlin:kotlin-stdlib-js")
    
    // Kotlin Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    
    // Kotlin Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    
    // React
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react:18.2.0-pre.467")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:18.2.0-pre.467")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-router-dom:6.8.1-pre.467")
    
    // Material-UI
    implementation("org.jetbrains.kotlin-wrappers:kotlin-mui:5.11.9-pre.467")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-mui-icons:5.11.9-pre.467")
    
    // Browser APIs
    implementation("org.jetbrains.kotlinx:kotlinx-browser:0.2")
}

kotlin {
    js(IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
        }
    }
}