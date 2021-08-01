plugins {
    war
}

dependencies {
    implementation(project(":ark-projects:ark-web"))
    providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
}

tasks.war {
    archiveFileName.set("ark-web.war")
}
