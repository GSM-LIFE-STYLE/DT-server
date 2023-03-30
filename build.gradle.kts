import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.9"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	kotlin("plugin.jpa") version "1.6.21"
}

group = "lifestyle"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {

	// spring-starter
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")

	// kotlin
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	// database
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	runtimeOnly("com.mysql:mysql-connector-j")

	// test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	testImplementation("io.kotest:kotest-runner-junit5-jvm:4.4.3")
	testImplementation("io.kotest:kotest-assertions-core-jvm:4.4.3")
	implementation("io.kotest:kotest-extensions-spring:4.4.3")
	testImplementation("io.mockk:mockk:1.12.0")

	// jwt
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

	// aws
	implementation("io.awspring.cloud:spring-cloud-starter-aws:2.4.4")

	// feign
	implementation("io.github.openfeign:feign-httpclient:11.9.1")
	implementation("org.springframework.cloud:spring-cloud-starter-openfeign:3.1.4")


}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
