plugins {
    id("org.springframework.boot")
}

dependencies {
    implementation(project(":ark-projects:ark-web"))
    runtimeOnly("org.postgresql:postgresql")
}

springBoot {
    mainClass.set("jp.gr.java_conf.stardiopside.ark.web.servlet.Server")
}
