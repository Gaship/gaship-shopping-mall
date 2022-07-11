package shop.gaship.gashipshoppingmall.employee.exception;

/**
 * packageName    : shop.gaship.gashipshoppingmall.employee.exception fileName       :
 * WrongStatusCodeException author         : 유호철 date           : 2022/07/11 description    :
 * =========================================================== DATE              AUTHOR NOTE
 * ----------------------------------------------------------- 2022/07/11        유호철       최초 생성
 */
public class WrongStatusCodeException extends RuntimeException {

    public WrongStatusCodeException() {
        super("상태 코드 값이 잘못 기입되었습니다.");
    }
}
