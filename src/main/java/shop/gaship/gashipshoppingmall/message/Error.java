package shop.gaship.gashipshoppingmall.message;

import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : shop.gaship.gashipshoppingmall.message
 * fileName       : Error
 * author         : 김보민
 * date           : 2022-07-12
 * description    : 에러 시 보낼 response body
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-07-12        김보민       최초 생성
 */
@Getter
@Setter
public class Error {
    private String exception;
    private String message;

    public Error(String exception, String message) {
        this.exception = exception;
        this.message = message;
    }
}
