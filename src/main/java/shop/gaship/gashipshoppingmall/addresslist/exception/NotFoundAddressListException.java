package shop.gaship.gashipshoppingmall.addresslist.exception;

/**
 * @author 최정우
 * @since 1.0
 */
public class NotFoundAddressListException extends RuntimeException{
    public static final String MESSAGE = "해당 배송지를 찾을 수 없습니다";

    public NotFoundAddressListException() {
        super(MESSAGE);
    }
}
