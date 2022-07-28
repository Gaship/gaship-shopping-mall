package shop.gaship.gashipshoppingmall.dataprotection.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import javax.crypto.spec.SecretKeySpec;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.config.DataProtectionConfig;
import shop.gaship.gashipshoppingmall.dataprotection.exception.DecodeFailureException;
import shop.gaship.gashipshoppingmall.dataprotection.exception.EncodeFailureException;

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
@EnableConfigurationProperties(value = DataProtectionConfig.class)
@TestPropertySource(value = {"classpath:application.properties", "classpath:application-dev.properties"})
@Import(Aes.class)
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AesTest {
    @Autowired
    Aes aes;

    @Test
    @DisplayName("개인정보 암호화 및 복호화 후 같은지 비교")
    @Order(1)
    void compareSameAesECBEncodeAndAesECBDecode() {
        String text = "01012345678";
        String encodedText = aes.aesEcbEncode(text);
        String decodedText = aes.aesEcbDecode(encodedText);

        assertThat(decodedText).isEqualTo(text);
    }


    @Test
    @DisplayName("개인정보 복호화 실패 오류 확인")
    @Order(2)
    void aesEcbDecodeFailure() {
        ReflectionTestUtils.setField(aes, "secretKeySpec",
            new SecretKeySpec("1234".repeat(30).getBytes(), "AES"));
        String text = "01012345678";
        assertThatThrownBy(() -> aes.aesEcbDecode(text))
            .isInstanceOf(DecodeFailureException.class);
    }

    @Test
    @DisplayName("개인정보 암호화 실패 오류 확인")
    @Order(3)
    void aesEcbEncodeFailure() {
        ReflectionTestUtils.setField(aes, "secretKeySpec",
            new SecretKeySpec("1234".repeat(30).getBytes(), "AES"));
        String text = "01012345678";
        assertThatThrownBy(() -> aes.aesEcbEncode(text))
            .isInstanceOf(EncodeFailureException.class);
    }

}
