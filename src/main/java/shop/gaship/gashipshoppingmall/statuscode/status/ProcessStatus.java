package shop.gaship.gashipshoppingmall.statuscode.status;

import lombok.Getter;

/**.
 * 문의에 사용되는 문의 답변 상태값
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
public enum ProcessStatus {
    WAITING("답변대기"), COMPLETE("답변완료");

    public static final String GROUP = "처리상태";

    private final String value;

    ProcessStatus(String value) {
        this.value = value;
    }
}
