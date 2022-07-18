package shop.gaship.gashipshoppingmall.statuscode.status;

import lombok.Getter;

/**.
 * 상품에 사용되는 판매 상태값
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
public enum SalesStatus {
    SALE("판매중"), SOLD_OUT("품절"), HIDING("숨김");

    public static final String GROUP = "판매상태";

    private final String value;

    SalesStatus(String value) {
        this.value = value;
    }
}