package shop.gaship.gashipshoppingmall.dataprotection.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.stereotype.Component;
import shop.gaship.gashipshoppingmall.dataprotection.exception.EncodeFailureException;

/**
 * db에 aes로 암호화된 email을 찾는 방법이 없어 email 중복 검사가 힘든 이유로
 * SHA-512로 암호화 하여 값을 db에 저장 후 email 중복검사시 넘겨온 값을 SHA-512로 암호화 하여
 * db의 SHA-512로 암호화된 정보와 비교하기 위해 SHA-512를 이용한 암호화를 위한 클래스.
 *
 * @author : 조재철
 * @since 1.0
 */
@Component
public class Sha512 {
    private static final String ENCODE_ERROR_MESSAGE = "정보 암호화에 실패했습니다.";

    /**
     * SHA-512 암호화 알고리즘으로 평문을 암호화 시키는 메서드.
     *
     * @param text 암호화 시킬 평문입니다.
     * @return 암호화가 완료된 문자열을 반환합니다.
     * @throws NoSuchAlgorithmException 올바르지 않은 알고리즘을 설정 하였을 때 예외를 던집니다.
     */
    public String encryptPlainText(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(text.getBytes());
            return bytesToHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new EncodeFailureException(ENCODE_ERROR_MESSAGE);
        }
    }

    /**
     * byte 단위의 암호화 값을 16진법 수로 변경하기 위한 메서드.
     *
     * @param digest 암호화 된 해시값입니다.
     * @return 해시값을 16진수로 변환하여 반환합니다.
     */
    private String bytesToHex(byte[] digest) {
        StringBuilder builder = new StringBuilder();

        for (byte b : digest) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}
