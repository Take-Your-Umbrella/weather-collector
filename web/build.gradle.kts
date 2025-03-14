import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("kotlin-spring-boot-convention")
}

val jar: Jar by tasks
val bootJar: BootJar by tasks

jar.enabled = false
bootJar.enabled = true

dependencies {
    implementation(project(":domain"))
    runtimeOnly(project(":infra"))
    implementation("org.springframework.boot:spring-boot-starter-web")
}
