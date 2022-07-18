package shop.gaship.gashipshoppingmall.statuscode.status;

import lombok.Getter;

/**.
 * 배송지 목록에서 사용되는 배송지 상태값
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
public enum AddressStatus {
    USE("사용"), DELETE("삭제");

    public static final String GROUP = "배송지상태";

    private final String value;

    AddressStatus(String value) {
        this.value = value;
    }
}
