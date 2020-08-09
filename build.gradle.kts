buildscript {
    apply(from = "./versions.gradle.kts")
    repositories {
        google()
        jcenter()
    }
    dependencies {
        val kotlinVersion: String by project.extra
        val navigationVersion: String by project.extra
        classpath("com.android.tools.build:gradle:4.0.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navigationVersion")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks {
    val clean by registering(Delete::class) {
        delete(buildDir)
    }
}
