package shop.gaship.gashipshoppingmall.statuscode.status;

import lombok.Getter;

/**.
 * 결제에 사용되는 결제 상태값
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
public enum PaymentStatus {
    FAIL("실패"), SUCCESS("성공");

    public static final String GROUP = "결제상태";

    private final String value;

    PaymentStatus(String value) {
        this.value = value;
    }
}