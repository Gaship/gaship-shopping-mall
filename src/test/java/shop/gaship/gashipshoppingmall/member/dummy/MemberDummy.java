package shop.gaship.gashipshoppingmall.member.dummy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import shop.gaship.gashipshoppingmall.member.entity.Member;

/**
 * packageName    : shop.gaship.gashipshoppingmall.member <br/>
 * fileName       : MemberDummy <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/10 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/10           김민수               최초 생성                         <br/>
 */
public class MemberDummy extends Member {
    private MemberDummy() {
    }

    public static Member dummy() {
        return MemberDummy.builder()
            .memberNo(1L)
            .recommendMemberNo(1)
            .statusNo(1)
            .gradeNo(2)
            .email("example@nhn.com")
            .password("password")
            .name("example")
            .nickName("example nickName")
            .birthDate(LocalDate.now())
            .phoneNumber("01012341234")
            .gender("남")
            .totalPurchaseAmount(100000L)
            .registerDateTime(LocalDateTime.now())
            .nextRenewalGradeDate(LocalDate.of(2022, 9, 16))
            .build();
    }
}
