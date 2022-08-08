package shop.gaship.gashipshoppingmall.error;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.gaship.gashipshoppingmall.inquiry.exception.AlreadyCompleteInquiryAnswerException;
import shop.gaship.gashipshoppingmall.inquiry.exception.DifferentEmployeeWriterAboutInquiryAnswerException;
import shop.gaship.gashipshoppingmall.inquiry.exception.DifferentInquiryException;
import shop.gaship.gashipshoppingmall.inquiry.exception.InquiryNotFoundException;
import shop.gaship.gashipshoppingmall.inquiry.exception.InquirySearchBadRequestException;
import shop.gaship.gashipshoppingmall.inquiry.exception.NoRegisteredAnswerException;
import shop.gaship.gashipshoppingmall.member.exception.DuplicatedNicknameException;
import shop.gaship.gashipshoppingmall.member.exception.InvalidReissueQualificationException;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;
import shop.gaship.gashipshoppingmall.member.exception.SignUpDenyException;

/**
 * 예외를 잡기위한 Advice 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
@Slf4j
@RestControllerAdvice
public class ExceptionAdviceController {
    /**
     * gaship 개발자들이 선언하고, 예상되는 예외들을 핸들링하기 위해서 제작한 에러 핸들 메서드입니다.
     *
     * @param exception 개발자들이 예상하는 예외 객체입니다.
     * @return 예상되는 예외들의 각 메세지를 담아 응답 객체를 반환합니다.
     */
    @ExceptionHandler(value = {SignUpDenyException.class, MemberNotFoundException.class,
        InvalidReissueQualificationException.class, DuplicatedNicknameException.class,
        AlreadyCompleteInquiryAnswerException.class,
        DifferentEmployeeWriterAboutInquiryAnswerException.class,
        InquiryNotFoundException.class, NoRegisteredAnswerException.class,
        DifferentInquiryException.class, InquirySearchBadRequestException.class
    })
    public ResponseEntity<ErrorResponse> declaredExceptionAdvice(RuntimeException exception) {
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON)
            .body(new ErrorResponse(exception.getMessage()));
    }

    /**
     * 컨트롤러에서 발생한 유효성문제를 잡기위한 클래스입니다.
     *
     * @param ex 적합한 값이 아닐경우 들어오게됩니다.
     * @return response entity
     * @author 유호철
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> validException(
            MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .reduce("", (accumulateMsg, nextMessage)  -> accumulateMsg + "\n" + nextMessage)
                .trim();

        return ResponseEntity.badRequest().body(new ErrorResponse(message));
    }

    /**
     * 예상치 못한 예외가 발생시 핸들링해주는 에러핸들 메서드입니다.
     *
     * @param exception 예기치 못한 예외 객체입니다.
     * @return 예기치 못한 예외의 내용을 응답합니다.
     */
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> otherExceptionAdvice(Exception exception) {
        log.error("error : {}", ExceptionUtils.getStackTrace(exception));

        return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON)
            .body(new ErrorResponse(exception.getMessage()));
    }
}
