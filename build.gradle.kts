plugins {
    id("java")
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.4"
}
group = "com.t4a"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor("org.projectlombok:lombok:1.18.24")
    compileOnly("org.projectlombok:lombok:1.18.24")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation(files("/<path>/tools4ai-0.7.1-jar-with-dependencies.jar"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.processResources{
    dependsOn("ui:build")
}

