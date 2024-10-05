package request;

import exception.BaseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("[HttpMethod] 테스트")
class HttpMethodTest {

    @DisplayName("HTTP_METHOD 올바른 생성 테스트")
    @Test
    void HTTP_METHOD_생성테스트() {
        // given & when
        HttpMethod result_1 = HttpMethod.create("GET");
        HttpMethod result_2 = HttpMethod.create("POST");

        // then
        assertAll(
                () -> assertEquals(HttpMethod.GET, result_1),
                () -> assertEquals(HttpMethod.POST, result_2)
        );
    }

    @DisplayName("HTTP_METHOD 생성시 지원하지 않는 타입에러")
    @Test
    void HTTP_METHOD_지원하지_않는_타입에러() {
        // given
        String invalid_input_1 = "GGGET";
        String invalid_input_2 = "&#$$@";

        // when & then
        assertAll(
                () -> assertThrows(BaseException.class, () -> HttpMethod.create(invalid_input_1)),
                () -> assertThrows(BaseException.class, () -> HttpMethod.create(invalid_input_2))
        );
    }
}