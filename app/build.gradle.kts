plugins {
    id("application")
    id("checkstyle")
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    // Define the main class for the application.
    mainClass = "hexlet.code.App"
}

dependencies {
    implementation ("info.picocli:picocli:4.7.7")
    annotationProcessor ("info.picocli:picocli-codegen:4.7.7")
    implementation("tools.jackson.core:jackson-databind:3.1.2")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}