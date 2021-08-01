apply(plugin = "org.springframework.boot")

dependencies {
    implementation(project(":ark-projects:ark-web"))
    runtimeOnly("org.postgresql:postgresql")
}

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    mainClass.set("jp.gr.java_conf.stardiopside.ark.web.servlet.Server")
}
