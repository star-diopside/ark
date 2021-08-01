dependencies {
    implementation(project(":ark-projects:ark-web-controller"))
    implementation(project(":ark-projects:ark-web-view"))
    implementation("org.springframework.session:spring-session-data-redis")
    runtimeOnly("redis.clients:jedis")
}
