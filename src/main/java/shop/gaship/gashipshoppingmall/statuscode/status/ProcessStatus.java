package shop.gaship.gashipshoppingmall.statuscode.status;

import lombok.Getter;

/**
 * packageName    : shop.gaship.gashipshoppingmall.statuscode.status
 * fileName       : ProcessStatus
 * author         : Semi Kim
 * date           : 2022/07/17
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/17        Semi Kim       최초 생성
 */
@Getter
public enum ProcessStatus {
    WAITING("답변대기"), COMPLETE("답변완료");

    public static final String GROUP = "처리상태";

    private final String value;

    ProcessStatus(String value) {
        this.value = value;
    }
}
