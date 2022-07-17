package shop.gaship.gashipshoppingmall.statuscode.status;

import lombok.Getter;

/**
 * packageName    : shop.gaship.gashipshoppingmall.statuscode.status
 * fileName       : RepairClassification
 * author         : Semi Kim
 * date           : 2022/07/17
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/17        Semi Kim       최초 생성
 */
@Getter
public enum RepairClassification {
    DAMAGE("파손"), POLLUTION("오염"), NOISE("소음"), OTHER("기타");

    public static final String GROUP = "수리분류";

    private final String value;

    RepairClassification(String value) {
        this.value = value;
    }
}
