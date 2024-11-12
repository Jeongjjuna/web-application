package request;

import org.apache.coyote.http11.request.HttpRequestParser;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("[HttpRequestParser] 단위테스트")
class HttpRequestParserTest {

    /**
     * TODO(BufferedReader 외부입력을 테스트하기 위한 방법 고민)
     */
    @DisplayName("[getHttpRequest] 리퀘스트를 올바르게 파싱하고, 생성하는지 확인")
    @Test
    @Disabled
    void 리퀘스트를_올바르게_파싱하고_생성하는지_확인() {
        // given

        // when

        // then
    }

    @DisplayName("url 에서 path 경로를 가져올 수 있다.")
    @ParameterizedTest(name = "{0}의 path 를 가져온다.")
    @CsvSource({
            "/user/create",
            "/user/create?userId=gildong&password=password&name=hongildong"
    })
    void url에서_path를_가져올수있다(String url) {
        // given & when
        String path = HttpRequestParser.getPath(url);

        // then
        assertThat(path).isEqualTo("/user/create");
    }
}