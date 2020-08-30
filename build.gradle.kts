plugins {
    java
    id("org.springframework.boot") version "2.1.4.RELEASE"
    id("io.spring.dependency-management") version "1.0.7.RELEASE"
}

group = "tk.d4097.httpfs"
version = "0.3-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter", "junit-jupiter", "5.6.2")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
