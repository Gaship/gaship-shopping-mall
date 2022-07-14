package shop.gaship.gashipshoppingmall.addressLocal.exception;

/**
 * packageName    : shop.gaship.gashipshoppingmall.addressLocal.exception fileName       :
 * NotExistAddressLocal author         : 유호철 date           : 2022/07/14 description    :
 * =========================================================== DATE              AUTHOR
 * NOTE ----------------------------------------------------------- 2022/07/14       유호철       최초
 * 생성
 */
public class NotExistAddressLocal extends RuntimeException {

    public NotExistAddressLocal() {
        super("주소가 존재하지 않습니다");
    }
}
