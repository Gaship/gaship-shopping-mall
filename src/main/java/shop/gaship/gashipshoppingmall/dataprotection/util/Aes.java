package shop.gaship.gashipshoppingmall.dataprotection.util;

import java.nio.charset.StandardCharsets;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import shop.gaship.gashipshoppingmall.dataprotection.exception.DecodeFailureException;
import shop.gaship.gashipshoppingmall.dataprotection.exception.EncodeFailureException;

/**
 * Secure Key Manager에서 응답이 제대로 받지 못하거나, 잘못 요청했을때 발생하는 예외입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Component
public class Aes {
    public static final String ENCODE_ERROR_MESSAGE = "정보 암호화에 실패했습니다.";
    public static final String DECODE_ERROR_MESSAGE = "정보 복호화에 실패했습니다.";
    private final SecretKeySpec secretKeySpec;
    private final String transformation;

    /**
     * Aes 객체를 생성하기 위한 생성자입니다.
     *
     * @param userInformationProtectionValue salt를 위한 일반 텍스트 문자열입니다.
     */
    public Aes(String userInformationProtectionValue,
               @Value("${aes.algorithm}") String transformation) {
        this.secretKeySpec =
            new SecretKeySpec(userInformationProtectionValue.getBytes(StandardCharsets.UTF_8),
                "AES");
        this.transformation = transformation;
    }


    /**
     * Aes-256 알고리즘으로 평문을 암호화하는 메서드입니다.
     *
     * @param plainText 평문입니다.
     * @return 암호화된 문자열입니다.
     * @throws EncodeFailureException EncodeFailureException
     */
    public String aesEcbEncode(String plainText) {
        try {
            Cipher c = Cipher.getInstance(transformation);
            c.init(Cipher.ENCRYPT_MODE, this.secretKeySpec);

            byte[] encrpytionByte = c.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

            return Base64.encodeBase64String(encrpytionByte);
        } catch (Exception e) {
            throw new EncodeFailureException(ENCODE_ERROR_MESSAGE);
        }
    }

    /**
     * Aes-256 알고리즘으로 암호화된 문자열을 평문을 복호화하는 메서드입니다.
     *
     * @param encodedText 암호화된 문자열입니다.
     * @return 복호화 된 평문입니다.
     * @throws DecodeFailureException DecodeFailureException
     */
    public String aesEcbDecode(String encodedText) {
        try {
            Cipher c = Cipher.getInstance(transformation);
            c.init(Cipher.DECRYPT_MODE, this.secretKeySpec);

            byte[] decodedByte = Base64.decodeBase64(encodedText);

            return new String(c.doFinal(decodedByte), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new DecodeFailureException(DECODE_ERROR_MESSAGE);
        }
    }
}
