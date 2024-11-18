dependencies {
    // 서블릿
    implementation("jakarta.servlet:jakarta.servlet-api:5.0.0")

    // 서블릿 컨테이너 구현체 : 톰캣
    implementation("org.apache.tomcat.embed:tomcat-embed-core:11.0.1")
    implementation("org.apache.tomcat.embed:tomcat-embed-jasper:11.0.1")
}