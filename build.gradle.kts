plugins {
    id("java")
}

group = "yjh"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

subprojects {
    apply(plugin = "java")

    repositories {
        mavenCentral()
    }

    dependencies {
        // 로깅 라이브러리 - slf4j -> logback
        implementation("ch.qos.logback:logback-classic:1.4.14")
        implementation("ch.qos.logback:logback-core:1.4.14")
        implementation("org.slf4j:slf4j-api:2.0.11")

        // 테스트 라이브러리 - junit, assertj
        testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
        testImplementation("org.assertj:assertj-core:3.26.3")
    }

    tasks.test {
        useJUnitPlatform()
    }
}
