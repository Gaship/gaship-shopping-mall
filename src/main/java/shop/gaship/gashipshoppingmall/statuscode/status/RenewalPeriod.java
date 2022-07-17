package shop.gaship.gashipshoppingmall.statuscode.status;

import lombok.Getter;

/**.
 * 회원등급에 사용되는 갱신기간 값
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
public enum RenewalPeriod {
    PERIOD("기간");

    public static final String GROUP = "갱신기간";

    private final String value;

    RenewalPeriod(String value) {
        this.value = value;
    }
}
