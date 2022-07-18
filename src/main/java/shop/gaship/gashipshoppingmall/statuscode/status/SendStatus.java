package shop.gaship.gashipshoppingmall.statuscode.status;

import lombok.Getter;

/**.
 * 쿠폰에 사용되는 쿠폰 발송 상태값
 *
 * @author : 김세미
 * @since 1.0
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