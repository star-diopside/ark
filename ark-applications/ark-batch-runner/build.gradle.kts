apply(plugin = "org.springframework.boot")

dependencies {
    implementation(project(":ark-projects:ark-batch"))
    runtimeOnly("org.postgresql:postgresql")
}
