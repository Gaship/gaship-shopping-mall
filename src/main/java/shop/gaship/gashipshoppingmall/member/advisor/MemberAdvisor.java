package shop.gaship.gashipshoppingmall.member.advisor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;
import shop.gaship.gashipshoppingmall.member.exception.SignUpDenyException;
import shop.gaship.gashipshoppingmall.message.ErrorResponse;

/**
 * 회원과 관련된 예외가 발생될 시 해당 클래스에서 예외를 처리합니다.
 *
 * @author 김민수
 * @since 1.0
 */
@RestControllerAdvice
@Slf4j
public class MemberAdvisor {
    /**
     * 회원과 관련하여 예상된 예외를 처리하는 메서드입니다.
     *
     * @param exception 예외 객체입니다.
     * @return 예외의 메세지가 들어있는 객체를 반환합니다.
     */
    @ExceptionHandler({SignUpDenyException.class, MemberNotFoundException.class})
    public ResponseEntity<ErrorResponse> memberExceptionAdvice(RuntimeException exception) {
        return ResponseEntity
            .badRequest()
            .contentType(MediaType.APPLICATION_JSON)
            .body(new ErrorResponse(exception.getMessage()));
    }
}
