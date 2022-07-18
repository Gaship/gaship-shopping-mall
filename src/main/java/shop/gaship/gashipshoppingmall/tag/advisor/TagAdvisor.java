package shop.gaship.gashipshoppingmall.tag.advisor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.gaship.gashipshoppingmall.tag.advisor.message.ErrorResponse;
import shop.gaship.gashipshoppingmall.tag.controller.TagController;

/**
 * packageName    : shop.gaship.gashipshoppingmall.tag.advisor
 * fileName       : ErrorResponse
 * author         : choijungwoo
 * date           : 2022/07/17
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/17        choijungwoo       최초 생성
 */
@RestControllerAdvice(basePackageClasses = TagController.class)
public class TagAdvisor {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorResponse(e.getMessage()));
    }
}
