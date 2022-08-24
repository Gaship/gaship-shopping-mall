package shop.gaship.gashipshoppingmall.addresslocal.exception;

/**
 * 주소지가 존재하지않을경우 예외를 보낼 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
public class NotExistAddressLocal extends RuntimeException {
    public static final String MESSAGE = "주소가 존재하지않습니다.";

    public NotExistAddressLocal() {
        super(MESSAGE);
    }
}
