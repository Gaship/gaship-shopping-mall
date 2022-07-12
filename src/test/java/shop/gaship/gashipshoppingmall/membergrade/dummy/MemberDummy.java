package shop.gaship.gashipshoppingmall.membergrade.dummy;

import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * packageName    : shop.gaship.gashipshoppingmall.membergrade.dummy
 * fileName       : MemberDummy
 * author         : Semi Kim
 * date           : 2022/07/12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/12        Semi Kim       최초 생성
 */
public class MemberDummy {
    private MemberDummy() {
    }

    public static Member dummy(StatusCode testStatusCode, MemberGrade testMemberGrade) {
        return Member.builder()
                .memberNo(1)
                .recommendMember(null)
                .memberStatusCodes(testStatusCode)
                .memberGrades(testMemberGrade)
                .email("example@nhn.com")
                .password("password")
                .phoneNumber("01022220000")
                .name("example")
                .birthDate(LocalDate.now())
                .nickname("hi")
                .gender("남")
                .accumulatePurchaseAmount(100000L)
                .registerDatetime(LocalDateTime.now())
                .nextRenewalGradeDate(LocalDate.of(2022, 9, 16))
                .isBooleanMember(false)
                .build();
    }
}
