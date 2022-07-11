package shop.gaship.gashipshoppingmall.exception;

/**
 * packageName    : shop.gaship.gashipshoppingmall.exception <br/>
 * fileName       : MemberNotFoundException <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/11 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/11           김민수               최초 생성                         <br/>
 */
public class MemberNotFoundException extends RuntimeException{
    public MemberNotFoundException() {
        super("찿고있는 회원의 정보가 존재하지않습니다.");
    }
}
