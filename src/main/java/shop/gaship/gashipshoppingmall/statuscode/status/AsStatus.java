package shop.gaship.gashipshoppingmall.statuscode.status;

import lombok.Getter;

/**.
 * GashipCare+ 에 사용되는 AS 접수 상태값
 *
 * @author : 김세미
 * @since 1.0
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