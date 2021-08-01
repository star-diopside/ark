dependencies {
    api("org.springframework.security:spring-security-core")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.apache.commons:commons-lang3")
    compileOnly("org.eclipse.persistence:org.eclipse.persistence.jpa")
    runtimeOnly("org.liquibase:liquibase-core")
    runtimeOnly("javax.xml.bind:jaxb-api")
    testImplementation(project(":ark-projects:ark-core"))
    testImplementation(project(":ark-projects:ark-test-support"))
}
