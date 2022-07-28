package shop.gaship.gashipshoppingmall.member.dummy;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.entity.MembersRole;
import shop.gaship.gashipshoppingmall.membergrade.dummy.MemberGradeDtoDummy;
import shop.gaship.gashipshoppingmall.membergrade.dummy.MemberGradeDummy;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

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
            .memberStatusCodes(status)
            .memberGrades(MemberGradeDummy.dummy(
                MemberGradeDtoDummy.requestDummy("name", 0L),
                StatusCodeDummy.dummy())
            )
            .roleSet(List.of(MembersRole.ROLE_USER))
            .email("example@nhn.com") // TODO : 해당 email을 AES로 encoding을 해야함
            .password("password")
            .name("example")
            .nickname("example nickname")
            .birthDate(LocalDate.now())
            .phoneNumber("01012341234")
            .gender("남")
            .accumulatePurchaseAmount(100000L)
            .nextRenewalGradeDate(LocalDate.of(2022, 9, 16))
            .isSocial(false)
            .encodedEmailForSearch("example@nhn.com") // TODO : 해당 email을 SHA-512로 encoding 해야함
            .build();
    }
}
