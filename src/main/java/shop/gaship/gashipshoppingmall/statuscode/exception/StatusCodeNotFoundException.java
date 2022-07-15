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
    private static final String MESSAGE = "해당 상태 코드를 찾을 수 없습니다.";

    public StatusCodeNotFoundException() {
        super(MESSAGE);
    }
}
