package shop.gaship.gashipshoppingmall.dataprotection.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
class AesTest {
    @Autowired
    Aes aes;

    @Test
    void compareSameAesECBEncodeAndAesECBDecode() {
        String text = "01012345678";
        String encodedText = aes.aesECBEncode(text);
        String decodedText = aes.aesECBDecode(encodedText);

        assertThat(decodedText).isEqualTo(text);
    }
}
