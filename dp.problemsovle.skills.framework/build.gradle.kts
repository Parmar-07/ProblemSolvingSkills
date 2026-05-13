plugins {
    kotlin("jvm")
}

group = "dp.problemsovle.skills.framework"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}