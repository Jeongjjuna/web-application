package request;

import com.yjh.request.HttpHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("[HttpHeaders] 단위테스트")
class HttpHeadersTest {

    @DisplayName("헤더값을통해 로그인 되어있는지 알 수 있다.")
    @ParameterizedTest(name = "{0} 이라면 로그인 상태는 {1} 이다.")
    @CsvSource({"logined=true, true", "logined=false, false"})
    void 헤더값을통해_로그인여부를_알수있다(String value, boolean expected) {
        // given
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", value);

        // when
        boolean isLogined = headers.isLogined();

        // then
        assertThat(isLogined).isEqualTo(expected);
    }
}