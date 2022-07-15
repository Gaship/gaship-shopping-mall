package shop.gaship.gashipshoppingmall.membergrade.advisor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.gaship.gashipshoppingmall.membergrade.advisor.message.ErrorResponse;
import shop.gaship.gashipshoppingmall.membergrade.controller.MemberGradeRestController;

/**
 * packageName    : shop.gaship.gashipshoppingmall.membergrade.advisor
 * fileName       : MemberGradeAdvisor
 * author         : Semi Kim
 * date           : 2022/07/15
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/15        Semi Kim       최초 생성
 */
@RestControllerAdvice(basePackageClasses = MemberGradeRestController.class)
public class MemberGradeAdvisor {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorResponse(e.getMessage()));
    }
}