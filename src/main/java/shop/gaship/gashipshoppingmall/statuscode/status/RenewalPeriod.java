package shop.gaship.gashipshoppingmall.statuscode.status;

import lombok.Getter;

/**.
 * 설명작성
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
