package shop.gaship.gashipshoppingmall.dataprotection.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.util.ReflectionUtils.findMethod;

import java.lang.reflect.Method;
import javax.crypto.spec.SecretKeySpec;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.ReflectionUtils;
import shop.gaship.gashipshoppingmall.dataprotection.exception.DecodeFailureException;
import shop.gaship.gashipshoppingmall.dataprotection.exception.EncodeFailureException;

@SpringBootTest
class AesTest {
    @Autowired
    Aes aes;

    @Test
    @DisplayName("개인정보 암호화 및 복호화 후 같은지 비교")
    void compareSameAesECBEncodeAndAesECBDecode() {
        String text = "01012345678";
        String encodedText = aes.aesECBEncode(text);
        String decodedText = aes.aesECBDecode(encodedText);

        assertThat(decodedText).isEqualTo(text);
    }


    @Test
    @DisplayName("개인정보 복호화 실패 오류 확인")
    void aesECBDecodeFailure() {
        Method method = findMethod(aes.getClass(), "aesECBDecode", String.class);
        ReflectionUtils.declaresException(method, DecodeFailureException.class);
        String text = "01012345678";
        assertThatThrownBy(() -> aes.aesECBDecode(text))
            .isInstanceOf(DecodeFailureException.class);
    }

    @Test
    @DisplayName("개인정보 암호화 실패 오류 확인")
    @Disabled
    void aesECBEncodeFailure() {
        Method method = findMethod(aes.getClass(), "aesECBEncode", String.class);
        ReflectionTestUtils.setField(aes, "secretKeySpec",
            new SecretKeySpec("1234".repeat(30).getBytes(), "AES"));
        String text = "01012345678";
        assertThatThrownBy(() -> aes.aesECBEncode(text))
            .isInstanceOf(EncodeFailureException.class);
    }

}
