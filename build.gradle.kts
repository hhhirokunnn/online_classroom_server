import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.2.6.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	id("org.jetbrains.gradle.plugin.idea-ext") version "0.3"
	kotlin("jvm") version "1.3.71"
	kotlin("plugin.spring") version "1.3.71"
	kotlin("plugin.jpa") version "1.3.50"
}

group = "com.hhhirokunnn"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

apply(mapOf("plugin" to "idea"))

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.cloud:spring-cloud-starter-aws:2.2.1.RELEASE")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation(platform("com.amazonaws:aws-java-sdk-bom:1.11.772"))
//	implementation("commons-fileupload:commons-fileupload:1.4")
//	implementation("com.amazonaws:aws-java-sdk-s3")


	runtimeOnly("mysql:mysql-connector-java")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
	implementation("org.springframework.boot:spring-boot-devtools")
	//jwt
	implementation("com.auth0:java-jwt:3.10.2")
}

idea {
	module {
		inheritOutputDirs = false
		outputDir = file("$buildDir/classes/kotlin/main")
	}
}

// TODO: fix build error
//tasks.withType<Test> {
//	useJUnitPlatform()
//}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}
