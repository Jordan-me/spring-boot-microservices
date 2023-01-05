import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.7"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
	kotlin("jvm") apply true
	kotlin("plugin.spring") apply true
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-webflux")

	// spring data r2dbc and postgres drivers
	implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
	runtimeOnly("org.postgresql:r2dbc-postgresql")

	//spring doc support
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springdoc:springdoc-openapi-kotlin:1.6.14")
	// https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-webflux-ui
	implementation("org.springdoc:springdoc-openapi-webflux-ui:1.6.14")

	//kotlin support
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	// https://mvnrepository.com/artifact/org.springframework/spring-r2dbc
//	implementation("org.springframework:spring-r2dbc:6.0.3")

	// https://mvnrepository.com/artifact/org.springframework.data/spring-data-r2dbc
//	implementation("org.springframework.data:spring-data-r2dbc:3.0.0")

//	implementation("io.projectreactor:reactor-core:3.4.24")
	// test dependencies
	runtimeOnly("org.postgresql:postgresql")



//	implementation("io.r2dbc:r2dbc-h2:0.8.3.RELEASE")
//	implementation("org.springframework.boot:spring-boot-starter-data-r2dbc:2.4.0-M1")
//	implementation("org.project-lombok:lombok:1.18.8")
	// https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-kotlin
	runtimeOnly("org.springdoc:springdoc-openapi-kotlin:1.6.14")
//	runtimeOnly("io.r2dbc:r2dbc-postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
