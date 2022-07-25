package shop.gaship.gashipshoppingmall.member.dummy;

import lombok.Getter;

/**
 * @author 최정우
 * @since 1.0
 */
@Getter
public enum MemberStatus {
    ACTIVATION("활성"), DORMANCY("휴면");

    public static final String GROUP = "회원상태";

    private final String value;

    MemberStatus(String value) {
        this.value = value;
    }
}
