package shop.gaship.gashipshoppingmall.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;
import shop.gaship.gashipshoppingmall.member.exception.SignUpDenyException;

/**
 * 예외를 잡기위한 Advice 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
@Slf4j
@RestControllerAdvice
public class ExceptionAdviceController {
    private static final String CAUSE_GUIDE  = " & cause : ";

    /**
     * 컨트롤러에서 발생한 유효성문제를 잡기위한 클래스입니다.
     *
     * @param ex 적합한 값이 아닐경우 들어오게됩니다.
     * @return response entity
     * @author 유호철
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> validException(
            MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .reduce("", (accumulateMsg, nextMessage)  -> accumulateMsg + "\n" + nextMessage)
                .trim();
        log.error(message + CAUSE_GUIDE + ex.getCause());
        return ResponseEntity.badRequest().body(new ErrorResponse(message));
    }

    /**
     * Exception.class 예외처리 담당 ExceptionHandler.
     *
     * @param e Exception
     * @return responseEntity
     * @author 김세미
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception e) {
        log.error(e.getMessage() + CAUSE_GUIDE + e.getCause());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorResponse(e.getMessage()));
    }

    /**
     * MemberNotFoundException, SignUpDenyException 예외처리 담당 ExceptionHandler.
     *
     * @param e Exception
     * @return responseEntity
     * @author 김세미
     */
    @ExceptionHandler({MemberNotFoundException.class, SignUpDenyException.class})
    public ResponseEntity<ErrorResponse> memberNotFoundExceptionHandler(Exception e) {
        log.error(e.getMessage() + CAUSE_GUIDE + e.getCause());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorResponse(e.getMessage()));
    }
}
