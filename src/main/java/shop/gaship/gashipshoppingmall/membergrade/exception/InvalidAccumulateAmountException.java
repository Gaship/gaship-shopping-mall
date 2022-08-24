package shop.gaship.gashipshoppingmall.membergrade.exception;

/**
 * 회원등급과 관련해서 유효하지 않은 등급갱신기준 누적구매금액 요청이 들어왔을 때 발생할 수 있는 exception 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
public class InvalidAccumulateAmountException extends RuntimeException {
    public static final String MESSAGE =
            "유효하지 않은 등금 갱신 기준에 대한 누적 구매금액입니다.";

    public InvalidAccumulateAmountException() {
        super(MESSAGE);
    }
}
