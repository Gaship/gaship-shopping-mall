package shop.gaship.gashipshoppingmall.statuscode.status;

import lombok.Getter;

/**
 * 사용자의 권환 확인 및 구분을 위해 사용되는 사용자 권한값.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
public enum UserAuthority {
    MEMBER("ROLE_MEMBER"), ADMIN("ROLE_ADMIN"), MANAGER("ROLE_MANAGER");

    public static final String GROUP = "사용자권한";

    private final String value;

    UserAuthority(String value) {
        this.value = value;
    }
}
