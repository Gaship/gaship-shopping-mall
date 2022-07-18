package shop.gaship.gashipshoppingmall.statuscode.status;

import lombok.Getter;

/**.
 * 회원에 사용되는 회원 상태값
 *
 * @author : 김세미
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
