package shop.gaship.gashipshoppingmall.addresslist.exception;

/**
 * 배송지 목록이 존재하지 않을 때 발생시키는 예외입니다.
 *
 * @author 최정우
 * @since 1.0
 */
public class NotFoundAddressListException extends RuntimeException {
    public static final String MESSAGE = "해당 배송지를 찾을 수 없습니다";

    public NotFoundAddressListException() {
        super(MESSAGE);
    }
}
