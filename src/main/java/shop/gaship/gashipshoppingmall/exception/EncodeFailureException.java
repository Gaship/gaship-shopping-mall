package shop.gaship.gashipshoppingmall.exception;

/**
 * packageName    : shop.gaship.gashipshoppingmall.exception <br/>
 * fileName       : EncodeFailureException <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/10 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/10           김민수               최초 생성                         <br/>
 */
public class EncodeFailureException extends RuntimeException {
    public EncodeFailureException(String message) {
        super(message);
    }
}
