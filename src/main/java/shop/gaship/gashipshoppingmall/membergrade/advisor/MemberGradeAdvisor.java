package shop.gaship.gashipshoppingmall.membergrade.advisor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.gaship.gashipshoppingmall.membergrade.advisor.message.ErrorResponse;
import shop.gaship.gashipshoppingmall.membergrade.controller.MemberGradeRestController;

/**
 * memberGrade 관련 예외처리.
 *
 * @author : 김세미
 * @since 1.0
 */
@RestControllerAdvice(basePackageClasses = MemberGradeRestController.class)
public class MemberGradeAdvisor {

    /**
     * 예외처리 담당 ExceptionHandler.
     *
     * @param e Exception
     * @return response entity
     * @author 김세미
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorResponse(e.getMessage()));
    }
}