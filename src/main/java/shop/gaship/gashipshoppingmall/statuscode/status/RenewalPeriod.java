package shop.gaship.gashipshoppingmall.statuscode.status;

import lombok.Getter;

/**
 * packageName    : shop.gaship.gashipshoppingmall.statuscode.enumm
 * fileName       : Status
 * author         : Semi Kim
 * date           : 2022/07/16
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/16        Semi Kim       최초 생성
 */
@Getter
public enum RenewalPeriod {
    PERIOD("갱신기간");

    public static final String GROUP = "회원상태";

    private final String value;

    RenewalPeriod(String value) {
        this.value = value;
    }
}
