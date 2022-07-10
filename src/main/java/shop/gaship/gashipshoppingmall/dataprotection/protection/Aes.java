package shop.gaship.gashipshoppingmall.dataprotection.protection;

import java.nio.charset.StandardCharsets;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;
import shop.gaship.gashipshoppingmall.exception.EncodeFailureException;

/**
 * packageName    : shop.gaship.gashipshoppingmall.dataprotection.protection <br/>
 * fileName       : Aes <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/10 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/10           김민수               최초 생성                         <br/>
 */
@Component
public class Aes {
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    private final SecretKeySpec secretKeySpec;

    public Aes(String userInformationProtectionValue) {
        this.secretKeySpec = new SecretKeySpec(
            userInformationProtectionValue.getBytes(StandardCharsets.UTF_8),
            "AES"
        );
    }


    public String aesECBEncode(String plainText) {
        try {
            Cipher c = Cipher.getInstance(TRANSFORMATION);
            c.init(Cipher.ENCRYPT_MODE, this.secretKeySpec);

            byte[] encrpytionByte = c.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

            return Base64.encodeBase64String(encrpytionByte);
        } catch (Exception e){
            throw new EncodeFailureException("정보 암호화에 실패했습니다.");
        }
    }

    public String aesECBDecode(String encodedText) {
        try {
            Cipher c = Cipher.getInstance(TRANSFORMATION);
            c.init(Cipher.DECRYPT_MODE, this.secretKeySpec);

            byte[] decodedByte = Base64.decodeBase64(encodedText);

            return new String(c.doFinal(decodedByte), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new EncodeFailureException("정보 복호화에 실패했습니다.");
        }
    }
}
