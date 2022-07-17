package shop.gaship.gashipshoppingmall.statuscode.status;

import lombok.Getter;

/**
 * packageName    : shop.gaship.gashipshoppingmall.statuscode.status
 * fileName       : SendStatus
 * author         : Semi Kim
 * date           : 2022/07/17
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/17        Semi Kim       최초 생성
 */
@Getter
public enum SendStatus {
    AWAITING("발송대기"), COMPLETE("발송완료");

    public static final String GROUP = "발송상태";

    private final String value;

    SendStatus(String value) {
        this.value = value;
    }
}
