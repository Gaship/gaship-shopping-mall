package shop.gaship.gashipshoppingmall.member.dummy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

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
        StatusCode status = StatusCode.builder()
            .statusCodeName("ex")
            .groupCodeName("ex")
            .priority(1)
            .build();

        return MemberDummy.builder()
            .memberNo(1)
            .recommendMember(null)
            .status(status)
            .grade(MemberGrade.builder()
                .name("name")
                .accumulateAmount(0L)
                .renewalPeriod(status)
                .build())
            .email("example@nhn.com")
            .password("password")
            .name("example")
            .nickname("example nickname")
            .birthDate(LocalDate.now())
            .phoneNumber("01012341234")
            .gender("남")
            .accumulatePurchaseAmount(100000L)
            .registerDatetime(LocalDateTime.now())
            .nextRenewalGradeDate(LocalDate.of(2022, 9, 16))
            .build();
    }
}
