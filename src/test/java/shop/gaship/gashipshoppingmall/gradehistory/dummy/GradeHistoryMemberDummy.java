package shop.gaship.gashipshoppingmall.gradehistory.dummy;

import shop.gaship.gashipshoppingmall.gradehistory.repository.GradeHistoryRepository;
import shop.gaship.gashipshoppingmall.member.dummy.MemberDummy;
import shop.gaship.gashipshoppingmall.member.dummy.StatusCodeDummy;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.membergrade.dummy.MemberGradeDtoDummy;
import shop.gaship.gashipshoppingmall.membergrade.dummy.MemberGradeDummy;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

import java.time.LocalDate;

/**
 * 등급이력 관련 멤버 dummy data.
 *
 * @author : 김세미
 * @since 1.0
 */
public class GradeHistoryMemberDummy {
    private GradeHistoryMemberDummy(){}

    public static Member dummy() {
        StatusCode status = StatusCode.builder()
                .statusCodeName("ex")
                .groupCodeName("ex")
                .priority(1)
                .build();

        return MemberDummy.builder()
                .recommendMember(null)
                .memberStatusCodes(status)
                .memberGrades(MemberGradeDummy.dummy(
                        MemberGradeDtoDummy.requestDummy("name",0L),
                        StatusCodeDummy.dummy())
                )
                .email("example@nhn.com")
                .password("password")
                .name("example")
                .nickname("example nickname")
                .birthDate(LocalDate.now())
                .phoneNumber("01012341234")
                .gender("남")
                .accumulatePurchaseAmount(100000L)
                .nextRenewalGradeDate(LocalDate.of(2022, 9, 16))
                .build();
    }
}
