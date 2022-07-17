package shop.gaship.gashipshoppingmall.statuscode.status;

import lombok.Getter;

/**.
 * Gaship Care + 에 사용되는 수리분류 상태값
 *
 * @author : 김세미
 * @since 1.0
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
