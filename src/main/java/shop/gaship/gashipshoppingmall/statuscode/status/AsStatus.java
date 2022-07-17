package shop.gaship.gashipshoppingmall.statuscode.status;

import lombok.Getter;

/**
 * packageName    : shop.gaship.gashipshoppingmall.statuscode.status
 * fileName       : AsStatus
 * author         : Semi Kim
 * date           : 2022/07/17
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/17        Semi Kim       최초 생성
 */
@Getter
public enum AsStatus {
    RECEPTION_WAITING("접수대기"), RECEPTION_COMPLETE("접수완료"),
    UNREPAIRABLE("수리불가"), REPAIR_COMPLETE("수리완료");

    public static final String GROUP = "AS상태";

    private final String value;

    AsStatus(String value) {
        this.value = value;
    }
}
