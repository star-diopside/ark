plugins {
    `java-library`
    checkstyle
    jacoco
    id("org.springframework.boot") version "2.5.3" apply false
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("com.github.spotbugs") version "4.7.2"
}

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "checkstyle")
    apply(plugin = "com.github.spotbugs")
    apply(plugin = "jacoco")

    group = "jp.gr.java_conf.stardiopside.ark"
    version = "0.2.0-SNAPSHOT"

    repositories {
        mavenCentral()
        mavenLocal()
    }

    dependencyManagement {
        imports {
            mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
        }
        dependencies {
            val silverCommonsVersion = "1.0.2-SNAPSHOT"
            dependency("jp.gr.java_conf.stardiopside.silver.commons:silver-commons-support:${silverCommonsVersion}")
            dependency("jp.gr.java_conf.stardiopside.silver.commons:silver-commons-batch:${silverCommonsVersion}")
            dependency("jp.gr.java_conf.stardiopside.silver.commons:silver-commons-web:${silverCommonsVersion}")
            dependency("jp.gr.java_conf.stardiopside.silver.commons:silver-commons-test:${silverCommonsVersion}")
            dependency("javax.inject:javax.inject:1")
            dependency("org.eclipse.persistence:org.eclipse.persistence.jpa:2.7.3")
            dependency("org.webjars:normalize.css:5.0.0")
            dependency("org.webjars:bootstrap:3.3.7-1")
            dependency("commons-cli:commons-cli:1.4")
            dependency("com.google.guava:guava:27.0-jre")
        }
    }

    configurations {
        all {
            exclude(group = "commons-logging", module = "commons-logging")
        }
    }

    dependencies {
        compileOnly("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok")
        testCompileOnly("org.projectlombok:lombok")
        testAnnotationProcessor("org.projectlombok:lombok")
        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    }

    java {
        withSourcesJar()
        withJavadocJar()
    }

    checkstyle {
        configFile = file("${rootDir}/config/checkstyle/checkstyle.xml")
        isIgnoreFailures = true
    }

    spotbugs {
        ignoreFailures.set(true)
    }

    tasks.compileJava {
        options.encoding = "UTF-8"
    }

    tasks.javadoc {
        options.encoding = "UTF-8"
    }

    tasks.spotbugsMain {
        reports {
            create("html")
        }
    }
}
