plugins {
    id("org.springframework.boot")
}

dependencies {
    implementation(project(":ark-projects:ark-data"))
    implementation("org.springframework.boot:spring-boot-starter-batch")
    runtimeOnly("org.postgresql:postgresql")
}
