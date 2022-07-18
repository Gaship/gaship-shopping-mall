package shop.gaship.gashipshoppingmall.statuscode.advisor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.gaship.gashipshoppingmall.statuscode.advisor.message.ErrorResponse;
import shop.gaship.gashipshoppingmall.statuscode.controller.StatusCodeRestController;

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
     * @param e Exception
     * @return response entity
     * @author 김세미
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorResponse(e.getMessage()));
    }
}
