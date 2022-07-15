package shop.gaship.gashipshoppingmall.member.advisor;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.gaship.gashipshoppingmall.member.dto.FailureRequestDto;
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
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FailureRequestDto memberExceptionAdvice(RuntimeException exception){
        return new FailureRequestDto("failure", exception.getMessage());
    }

    /**
     * Validation과 관련한 예외를 처리하는 메서드입니다.
     *
     * @param ex Validation 예외 객체입니다.
     * @return 예외의 메세지가 들어있는 객체를 반환합니다.
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
        MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * 예상치 못한 에러, 예외가 발생했을 때 처리할 에러핸들러입니다.
     *
     * @param exception 알 수 없는 예외, 에러 객체입니다.
     * @return 요청을 보낸 클라이언트에게 던져줄 예외 결과 값입니다.
     */
    @ExceptionHandler({RuntimeException.class, Exception.class})
    public ResponseEntity<ErrorResponse> anyExceptionAdvisor(Exception exception){
        log.error("VerifyAdvisor error cause : {0}, {1}", exception.getCause());
        log.error("VerifyAdvisor error message : {}", exception.getMessage());
        return ResponseEntity.internalServerError().body(new ErrorResponse(exception.getMessage()));
    }
}
