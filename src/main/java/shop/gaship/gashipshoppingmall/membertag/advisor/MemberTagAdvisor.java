package shop.gaship.gashipshoppingmall.membertag.advisor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.gaship.gashipshoppingmall.membertag.controller.MemberTagController;
import shop.gaship.gashipshoppingmall.tag.advisor.message.ErrorResponse;

/**
 * MemberTag 에 관련된 에러가 발생하면 에러를 가로채 대신 응답을 해주는 클래스입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@RestControllerAdvice(basePackageClasses = MemberTagController.class)
public class MemberTagAdvisor {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorResponse(e.getMessage()));
    }
}
