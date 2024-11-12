package model;

import com.yjh.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("[User] 단위테스트")
class UserTest {

    @DisplayName("회원 비밀번호의 일치 여부를 알 수 있다.")
    @ParameterizedTest(name = "\"{0}\" 를 입력하면 비밀번호 일치여부는 \"{1}\" 이다.")
    @CsvSource({"password, true", "wrong password, false"})
    void isSamePassword(String password, boolean expected) {
        // given
        User user = getTestUser();

        // when
        boolean isSamePassword = user.isSamePassword(password);

        // then
        assertThat(isSamePassword).isEqualTo(expected);
    }

    private User getTestUser() {
        return new User("username", "email@11111111", "password");
    }
}