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

    /**
     * 오브젝트 스토리지 접근 정보 객체입니다.
     */
    @Getter
    public class Access {
        private Token token;
        private User user;
    }
}
