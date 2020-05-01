import org.gradle.jvm.tasks.Jar

plugins {
    java
    id("org.jetbrains.kotlin.jvm") version "1.3.50"
    id("org.jetbrains.dokka") version "0.10.0"
}

group = "org"
version = "1.0"

val sourceCompatibility = 1.8
buildDir = File("C://libs/alphavantagekt")

repositories {
    mavenCentral()
    jcenter()
}

tasks {
    dokka {
        outputFormat = "html"
        outputDirectory = "$buildDir/javadoc"
    }
    val dokkaJar by creating(Jar::class) {
        group = JavaBasePlugin.DOCUMENTATION_GROUP
        description = "Assembles Kotlin docs with Dokka"
        classifier = "javadoc"
        from(dokka)
    }
    val sourcesJar by creating(Jar::class) {
        archiveClassifier.set("sources")
        from(sourceSets.main.get().allSource)
    }
    val javadocJar by creating(Jar::class) {
        val javadoc by tasks;
        from(javadoc);
        classifier = "javadoc";
    }
    artifacts {
        archives(sourcesJar)
        archives(dokkaJar)
        archives(jar)
    }
}



dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.junit.jupiter:junit-jupiter:5.6.1")
}

val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
val compileTestKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks

compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}