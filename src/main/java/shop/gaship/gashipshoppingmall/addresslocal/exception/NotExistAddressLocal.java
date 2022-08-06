package shop.gaship.gashipshoppingmall.addresslocal.exception;

/**
 * 주소지가 존재하지않을경우 예외를 보낼 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
public class NotExistAddressLocal extends RuntimeException {

    public NotExistAddressLocal() {
        super("주소가 존재하지 않습니다");
    }
}
