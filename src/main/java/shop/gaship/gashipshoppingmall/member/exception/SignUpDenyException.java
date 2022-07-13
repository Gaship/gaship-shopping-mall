package shop.gaship.gashipshoppingmall.member.exception;

/**
 * packageName    : shop.gaship.gashipshoppingmall.member.exception <br/>
 * fileName       : SignUpDenyException <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/13 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/13           김민수               최초 생성                         <br/>
 */
public class SignUpDenyException extends RuntimeException {
    public SignUpDenyException(String message) {
        super(message);
    }
}
