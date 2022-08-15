package shop.gaship.gashipshoppingmall.error;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * API 서버에서 예외상황으로 넘어온경우에 발생시킬 예외클래스입니다.
 *
 * @author 김보민
 * @author 최겸준
 * @since 1.0
 */
@NoArgsConstructor
public class RequestFailureThrow extends RuntimeException {
    private HttpStatus statusCode;

    /**
     * status code 비교가 필요없이 예외를 발생시키면 되는 경우에 message 파라미터 하나만 받아서 예외를 생성시키는 생성자입니다.
     *
     * @param message 예외 메세지를 저장합니다.
     */
    public RequestFailureThrow(String message) {
        super(message);
    }

    /**
     * status code 비교가 필요한경우 매개변수로 status 코드까지 받는 생성자입니다.
     *
     * @param message 예외 메세지를 저장합니다.
     * @param statusCode 예외에 대한 상태코드를 저장합니다.
     */
    public RequestFailureThrow(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    /**
     * statusCode를 반환하는 기능입니다. message는 부모클래스에서 getter기능을 제공합니다.
     *
     * @return Http Status 코드를 반환합니다.
     */
    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
