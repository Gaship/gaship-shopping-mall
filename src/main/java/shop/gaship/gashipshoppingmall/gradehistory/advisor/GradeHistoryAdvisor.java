package shop.gaship.gashipshoppingmall.gradehistory.advisor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.gaship.gashipshoppingmall.error.ErrorResponse;
import shop.gaship.gashipshoppingmall.gradehistory.controller.GradeHistoryRestController;

/**
 * GradeHistory 관련 예외처리.
 *
 * @author : 김세미
 * @since 1.0
 */
@RestControllerAdvice(basePackageClasses = GradeHistoryRestController.class)
public class GradeHistoryAdvisor {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(e.getMessage()));
    }
}
