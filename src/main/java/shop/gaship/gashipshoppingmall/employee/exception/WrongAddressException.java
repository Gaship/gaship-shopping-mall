package shop.gaship.gashipshoppingmall.employee.exception;

/**
 * packageName    : shop.gaship.gashipshoppingmall.employee.exception fileName       :
 * WrongAddressException author         : 유호철 date           : 2022/07/11 description    : 주소기입이
 * 잘못되었을시 예외 =========================================================== DATE              AUTHOR
 *          NOTE ----------------------------------------------------------- 2022/07/11        유호철
 * 최초 생성
 */
public class WrongAddressException extends RuntimeException {

    public WrongAddressException() {
        super("주소가 잘못기입되었습니다");
    }
}
