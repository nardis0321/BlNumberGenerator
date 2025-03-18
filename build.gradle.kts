plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.2" // Shadow 플러그인 추가
}

version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.poi:poi-ooxml:5.2.5") // Apache POI
    implementation("com.github.pjfanning:excel-streaming-reader:5.0.2")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.jar {
    manifest {
        attributes(
            "Main-Class" to "Main"
        )
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.shadowJar {
    version = "1.0-SNAPSHOT"
}