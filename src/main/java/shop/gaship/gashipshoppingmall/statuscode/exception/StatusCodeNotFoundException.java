package shop.gaship.gashipshoppingmall.statuscode.exception;

/**
 * packageName    : shop.gaship.gashipshoppingmall.statuscode.exception
 * fileName       : StatusCodeNotFoundException
 * author         : Semi Kim
 * date           : 2022/07/09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/09        Semi Kim       최초 생성
 */
public class StatusCodeNotFoundException extends RuntimeException {
    public StatusCodeNotFoundException() {
        super("상태번호 결과가 존재하지않습니다.");
    }
}
