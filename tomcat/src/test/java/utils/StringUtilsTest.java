package utils;

import com.yjh.utils.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


@DisplayName("[StringUtils] 단위테스트")
class StringUtilsTest {

    @DisplayName("[trim] 문자열 양쪽 공백 제거")
    @Test
    void 문자_맨앞_공백삭제() {
        // given
        String input = " 문자 ";

        // when
        String result = StringUtils.trim(input);

        // then
        assertThat(result).isEqualTo("문자");
    }

    @DisplayName("[split] 올바른 문자로 파싱")
    @Test
    void 올바른_문자로_파싱() {
        // given
        String input = "안녕 나는 지훈";

        // when
        String[] result = StringUtils.split(input, " ");

        // then
        assertAll(
                () -> assertThat(result.length).isEqualTo(3),
                () -> assertThat(result[0]).isEqualTo("안녕"),
                () -> assertThat(result[1]).isEqualTo("나는"),
                () -> assertThat(result[2]).isEqualTo("지훈")
        );
    }

}