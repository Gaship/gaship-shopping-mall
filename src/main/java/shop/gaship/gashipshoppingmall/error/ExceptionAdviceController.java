package shop.gaship.gashipshoppingmall.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 예외를 잡기위한 Advice 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */

@RestControllerAdvice
public class ExceptionAdviceController {

    /**
     * 컨트롤러에서 발생한 유효성문제를 잡기위한 클래스입니다.
     *
     * @param ex 적합한 값이 아닐경우 들어오게됩니다.
     * @return response entity
     * @throws MethodArgumentNotValidException 유효성 값이 맞지않을시 예외
     * @author 유호철
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> validException(
            MethodArgumentNotValidException ex) {
        ErrorResponse response = new ErrorResponse("유효성 검사를 실패했습니다 : " + ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
