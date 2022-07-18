package shop.gaship.gashipshoppingmall.dataprotection.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.springframework.util.ReflectionUtils.findMethod;

import java.lang.reflect.Method;
import javax.crypto.spec.SecretKeySpec;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.ReflectionUtils;
import shop.gaship.gashipshoppingmall.config.DataProtectionConfig;
import shop.gaship.gashipshoppingmall.dataprotection.exception.DecodeFailureException;
import shop.gaship.gashipshoppingmall.dataprotection.exception.EncodeFailureException;
import org.springframework.test.context.TestPropertySource;

/**
 * packageName    : shop.gaship.gashipshoppingmall.dataprotection.protection <br/>
 * fileName       : AesTest <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/10 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/10           김민수               최초 생성                         <br/>
 */
@SpringBootTest
@EnableConfigurationProperties(value = DataProtectionConfig.class)
@TestPropertySource(value = "classpath:application-dev.properties")
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
        ReflectionTestUtils.setField(aes, "secretKeySpec",
            new SecretKeySpec("1234".repeat(30).getBytes(), "AES"));
        String text = "01012345678";
        assertThatThrownBy(() -> aes.aesECBDecode(text))
            .isInstanceOf(DecodeFailureException.class);
    }

    @Test
    @DisplayName("개인정보 암호화 실패 오류 확인")
    void aesECBEncodeFailure() {
        ReflectionTestUtils.setField(aes, "secretKeySpec",
            new SecretKeySpec("1234".repeat(30).getBytes(), "AES"));
        String text = "01012345678";
        assertThatThrownBy(() -> aes.aesECBEncode(text))
            .isInstanceOf(EncodeFailureException.class);
    }

}
