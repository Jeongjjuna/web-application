dependencies {
    // 서블릿
    implementation("jakarta.servlet:jakarta.servlet-api:6.1.0")

    // 서블릿 컨테이너 구현체 : 톰캣
    implementation("org.apache.tomcat.embed:tomcat-embed-core:11.0.1")
    implementation("org.apache.tomcat.embed:tomcat-embed-jasper:11.0.1")

    // 동적 템플릿으로 타임리프 사용
    implementation("org.thymeleaf:thymeleaf:3.1.2.RELEASE")

    // 객체 JSON 변환을 위한 잭슨 라이브러리
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")

    // 리플렉션 라이브러리
    implementation("org.reflections:reflections:0.10.2")
}