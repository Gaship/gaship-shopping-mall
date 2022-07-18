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
 * member에 관련된 에러가 발생하면 에러를 가로채 대신 응답을 해주는 클래스입니다.
 *
 * @author 최정우
 * @since 1.0
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
