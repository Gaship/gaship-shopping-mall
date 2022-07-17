package shop.gaship.gashipshoppingmall.member.advisor.message;

/**
 * packageName    : shop.gaship.gashipshoppingmall.member.advisor.message
 * fileName       : ErrorResponse
 * author         : choijungwoo
 * date           : 2022/07/17
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/17        choijungwoo       최초 생성
 */
public class ErrorResponse {
    private final String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
