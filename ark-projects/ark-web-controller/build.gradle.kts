dependencies {
    api(project(":ark-projects:ark-service"))
    api("org.springframework.boot:spring-boot-starter-web")
    implementation(project(":ark-projects:ark-support"))
    implementation(files("lib/kaptcha-2.3.2.jar"))
    implementation("jp.gr.java_conf.stardiopside.silver.commons:silver-commons-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.apache.commons:commons-lang3")
    testImplementation(project(":ark-projects:ark-test-support"))
    testImplementation("org.springframework.security:spring-security-test")
}
