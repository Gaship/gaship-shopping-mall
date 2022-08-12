package shop.gaship.gashipshoppingmall.storage.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Object storage 인증 token 요청 dto 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@Setter
public class AuthTokenRequestDto {
    private Auth auth = new Auth();

    /**
     * 인증 token 정보를 담는 auth class 입니다.
     */
    @Getter
    @Setter
    @NoArgsConstructor
    public class Auth {
        private String tenantId;
        private PasswordCredentials passwordCredentials = new PasswordCredentials();
    }

    /**
     * 인증 token 발급을 위한 계정 정보를 담는 class 입니다.
     */
    @Getter
    @Setter
    @NoArgsConstructor
    public class PasswordCredentials {
        private String username;
        private String password;
    }
}
