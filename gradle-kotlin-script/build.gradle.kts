plugins {
    kotlin("jvm") version "1.5.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
}

task<JavaExec>("task1") {
    classpath = sourceSets.getAt("main").runtimeClasspath
    main = "org.example.tasks.Task1Kt"
}

task<JavaExec>("task2") {
    classpath = sourceSets.getAt("main").runtimeClasspath
    main = "org.example.tasks.Task2Kt"
}

task("taskAll") {
    dependsOn("task1")
    dependsOn("task2")
}

task<JavaExec>("taskWithName") {
    classpath = sourceSets.getAt("main").runtimeClasspath
    if (project.hasProperty("args")) {
        val mainClassName: String = "org.example.tasks." + (project.property("args") as String) + "Kt"
        println("Main Class Name : ${mainClassName}")
        main = mainClassName
    } else {
        println("No args Error")
    }
}



