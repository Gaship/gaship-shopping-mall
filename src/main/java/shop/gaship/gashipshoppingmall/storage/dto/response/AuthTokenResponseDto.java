package shop.gaship.gashipshoppingmall.storage.dto.response;

import lombok.Getter;

/**
 * object storage 의 인증 token 요청에 대한 응답 dto 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
public class AuthTokenResponseDto {
    private Access access;

    @Getter
    public class Access {
        private Token token;
        private User user;
    }
}
