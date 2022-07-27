package shop.gaship.gashipshoppingmall.member.advisor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.gaship.gashipshoppingmall.member.MemberMarker;
import shop.gaship.gashipshoppingmall.member.exception.DuplicatedNicknameException;
import shop.gaship.gashipshoppingmall.member.exception.InvalidReissueQualificationException;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;
import shop.gaship.gashipshoppingmall.member.exception.SignUpDenyException;
import shop.gaship.gashipshoppingmall.message.ErrorResponse;

/**
 * 회원과 관련된 예외가 발생될 시 해당 클래스에서 예외를 처리합니다.
 *
 * @author 김민수
 * @author 최겸준
 * @since 1.0
 */
@RestControllerAdvice(basePackageClasses = MemberMarker.class)
@Slf4j
public class MemberAdvisor {
    /**
     * 회원과 관련하여 예상된 예외를 처리하는 메서드입니다.
     *
     * @param exception 예외 객체입니다.
     * @return 예외의 메세지가 들어있는 객체를 반환합니다.
     */
    @ExceptionHandler({SignUpDenyException.class, MemberNotFoundException.class,
        InvalidReissueQualificationException.class, DuplicatedNicknameException.class})
    public ResponseEntity<ErrorResponse> memberExceptionAdvice(RuntimeException exception) {
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON)
            .body(new ErrorResponse(exception.getMessage()));
    }

    /**
     * 회원과 관련하여 예상된 예외를 처리하는 메서드입니다.
     * 소셜로그인시에 위의 조건인 400으로 반환된경우에는 회원가입을시키고 그외 서버문제로
     * 500인 경우에는 일반 예외를 발생시켜야함으로
     * 해당 API서버의 member에 대하여 전체 예외를 잡는 advice를 만들었습니다.
     * 또한 꼭 이런경우가 아니더라도 위의 두 예외 외의 또다른 예외가 일어날수 있기때문에 해당 핸들러를 추가했습니다.
     *
     * @param exception 예외 객체입니다.
     * @return 예외의 메세지가 들어있는 객체를 반환합니다.
     */
    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<ErrorResponse> otherExceptionAdvice(Exception exception) {
        log.error("error : {}", ExceptionUtils.getStackTrace(exception));

        return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON)
            .body(new ErrorResponse(exception.getMessage()));
    }
}
