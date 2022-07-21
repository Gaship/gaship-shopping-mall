package shop.gaship.gashipshoppingmall.addresslist.advisor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.gaship.gashipshoppingmall.addresslist.controller.AddressListController;
import shop.gaship.gashipshoppingmall.membergrade.advisor.message.ErrorResponse;

/**
 * 예외처리 담당 ExceptionHandler.
 *
 * @param e Exception
 * @author 최정우
 * @return 에러 핸들링 된 ErrorResponse 객체를 반환합니다.
 */
@RestControllerAdvice(basePackageClasses = AddressListController.class)
public class AddressListAdvisor {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorResponse(e.getMessage()));
    }
}
