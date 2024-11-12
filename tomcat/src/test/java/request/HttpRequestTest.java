package request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


@DisplayName("[HttpRequest] 단위테스트")
class HttpRequestTest {

    @DisplayName("GET요청의 요청정보를 알 수 있다.")
    @Test
    void GET요청의_요청정보를알수있다() throws IOException {
        // given
        String request = """
                GET /user/create?userId=gildong&password=password&name=hongildong HTTP/1.1
                Host: localhost:8080
                Connection: keep-alive
                Accept: */*
                
                """;
        InputStream in = new ByteArrayInputStream(request.getBytes(StandardCharsets.UTF_8));

        // when
        HttpRequest httpRequest = HttpRequest.of(in);

        // then
        assertAll(
                () -> assertThat(httpRequest.isGetRequest()).isTrue(),
                () -> assertThat(httpRequest.getHttpMethod()).isEqualTo(HttpMethod.GET),
                () -> assertThat(httpRequest.getPath()).isEqualTo("/user/create"),
                () -> assertThat(httpRequest.getHeader("Connection")).isEqualTo("keep-alive")
        );
    }

    @DisplayName("POST요청의 요청정보를 알 수 있다.")
    @Test
    void POST요청의_요청정보를알수있다() throws IOException {
        // given
        String request = """
                POST /user/create HTTP/1.1
                Host: localhost:8080
                Connection: keep-alive
                Content-Length: 46
                Content-Type: application/x-www-form-urlencoded
                Accept: */*
                
                userId=gildong&password=password&name=hongildong
                """;
        InputStream in = new ByteArrayInputStream(request.getBytes(StandardCharsets.UTF_8));

        // when
        HttpRequest httpRequest = HttpRequest.of(in);

        // then
        assertAll(
                () -> assertThat(httpRequest.isPostRequest()).isTrue(),
                () -> assertThat(httpRequest.getHttpMethod()).isEqualTo(HttpMethod.POST),
                () -> assertThat(httpRequest.getPath()).isEqualTo("/user/create"),
                () -> assertThat(httpRequest.getHeader("Connection")).isEqualTo("keep-alive"),
                () -> assertThat(httpRequest.getParameter("userId")).isEqualTo("gildong"),
                () -> assertThat(httpRequest.getParameter("password")).isEqualTo("password")
        );

    }
}