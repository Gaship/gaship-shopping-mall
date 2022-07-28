package shop.gaship.gashipshoppingmall.statuscode.advisor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.gaship.gashipshoppingmall.error.ErrorResponse;
import shop.gaship.gashipshoppingmall.statuscode.controller.StatusCodeRestController;
import shop.gaship.gashipshoppingmall.statuscode.exception.StatusCodeNotFoundException;

/**
 * StatusCode 관련 예외처리.
 *
 * @author : 김세미
 * @since 1.0
 */
@RestControllerAdvice(basePackageClasses = {StatusCodeRestController.class})
public class StatusCodeAdvisor {
    /**
     * 예외처리 담당 ExceptionHandler.
     *
     * @param e StatusCodeNotFoundException의 예외 객체입니다.
     * @return StatusCode context
     * @author 김세미
     */
    @ExceptionHandler(StatusCodeNotFoundException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception e) {
        return ResponseEntity.badRequest()
            .contentType(MediaType.APPLICATION_JSON)
            .body(new ErrorResponse(e.getMessage()));
    }
}
