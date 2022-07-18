package shop.gaship.gashipshoppingmall.member.advisor.message;

/**
 * 에러가 발생했을 시 responseEntity<ErrorResponse> 에 담길 객체를 정의해놓은 클래스입니다.
 *
 * @author 최정우
 * @since 1.0
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
