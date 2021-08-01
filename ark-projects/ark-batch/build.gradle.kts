dependencies {
    api(project(":ark-projects:ark-service"))
    implementation("jp.gr.java_conf.stardiopside.silver.commons:silver-commons-batch")
    implementation("org.springframework.boot:spring-boot-starter-batch")
    testImplementation(project(":ark-projects:ark-test-support"))
    testImplementation("org.springframework.batch:spring-batch-test")
    testImplementation("com.h2database:h2")
}
