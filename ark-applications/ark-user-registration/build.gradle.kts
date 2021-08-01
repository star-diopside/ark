apply(plugin = "org.springframework.boot")

dependencies {
    implementation(project(":ark-projects:ark-service"))
    implementation("commons-cli:commons-cli")
    implementation("com.google.guava:guava")
    runtimeOnly("org.postgresql:postgresql")
}
