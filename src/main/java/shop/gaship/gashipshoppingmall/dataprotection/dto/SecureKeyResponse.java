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


    @Getter
    public static class Header {
        private Integer resultCode;
        private String resultMessage;
        private Boolean isSuccessful;
    }

    @Getter
    public static class Body {
        private String secret;
    }
}
