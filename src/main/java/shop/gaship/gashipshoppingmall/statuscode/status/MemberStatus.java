package shop.gaship.gashipshoppingmall.statuscode.status;

import lombok.Getter;

/**
 * packageName    : shop.gaship.gashipshoppingmall.statuscode.status
 * fileName       : MemberStatus
 * author         : Semi Kim
 * date           : 2022/07/17
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/17        Semi Kim       최초 생성
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
