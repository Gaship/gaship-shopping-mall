package shop.gaship.gashipshoppingmall.orderproduct.exception;

/**
 * 쿠폰 서버로의 쿠폰 관련 처리 요청중의 Issue 로 인해 발생하는 exception 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
public class CouponProcessException extends RuntimeException {
    public static final String MESSAGE = "쿠폰 처리 과정에서 문제가 발생하였습니다.";

    public CouponProcessException() {
        super(MESSAGE);
    }
}
