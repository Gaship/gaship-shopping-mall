package shop.gaship.gashipshoppingmall.dataprotection.dto;

import lombok.Getter;


/**
 * NHN Secure Key Manager에서 Secure Key의 응답 메세지 타입입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Getter
public class SecureKeyResponse {
    private Header header;
    private Body body;


    /**
     * Secure Manager Key의 json응답의 header property의 구조를 역직렬화 시킬 구조입니다.
     *
     * @author 김민수
     * @since 1.0
     */
    @Getter
    public static class Header {
        private Integer resultCode;
        private String resultMessage;
        private Boolean isSuccessful;
    }

    /**
     * Secure Manager Key의 json응답의 body property의 구조를 역직렬화 시킬 구조입니다.
     *
     * @author 김민수
     * @since 1.0
     */
    @Getter
    public static class Body {
        private String secret;
    }
}
