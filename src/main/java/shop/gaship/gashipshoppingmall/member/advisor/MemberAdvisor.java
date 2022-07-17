package shop.gaship.gashipshoppingmall.member.advisor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.gaship.gashipshoppingmall.member.advisor.message.ErrorResponse;
import shop.gaship.gashipshoppingmall.member.controller.MemberController;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;

/**
 * packageName    : shop.gaship.gashipshoppingmall.member.advisor
 * fileName       : MemberAdvisor
 * author         : choijungwoo
 * date           : 2022/07/17
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/17        choijungwoo       최초 생성
 */
@RestControllerAdvice(basePackageClasses = MemberController.class)
public class MemberAdvisor {

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ErrorResponse> exception(Exception e){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorResponse(e.getMessage()));
    }
}
