package shop.gaship.gashipshoppingmall.member.advisor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.gaship.gashipshoppingmall.member.dto.FailureRequestDto;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;
import shop.gaship.gashipshoppingmall.member.exception.SignUpDenyException;

/**
 * packageName    : shop.gaship.gashipshoppingmall.member.advisor <br/>
 * fileName       : MemberAdvisor <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/13 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/13           김민수               최초 생성                         <br/>
 */
@RestControllerAdvice
public class MemberAdvisor {
    @ExceptionHandler({SignUpDenyException.class, MemberNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FailureRequestDto memberExceptionAdvice(RuntimeException exception){
        return new FailureRequestDto("failure", exception.getMessage());
    }
}
