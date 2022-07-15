package shop.gaship.gashipshoppingmall.membergrade.advisor.message;

/**
 * packageName    : shop.gaship.gashipshoppingmall.membergrade.advisor.message
 * fileName       : ErrorResponse
 * author         : Semi Kim
 * date           : 2022/07/15
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/15        Semi Kim       최초 생성
 */
public class ErrorResponse {
    private final String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
