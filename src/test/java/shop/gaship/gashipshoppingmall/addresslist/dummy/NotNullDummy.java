package shop.gaship.gashipshoppingmall.addresslist.dummy;

import java.time.LocalDate;
import lombok.EqualsAndHashCode;
import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.addresslocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.daylabor.entity.DayLabor;
import shop.gaship.gashipshoppingmall.member.dummy.MemberStatus;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.status.AddressStatus;
import shop.gaship.gashipshoppingmall.statuscode.status.RenewalPeriod;

/**
 * @author 최정우
 * @since 1.0
 */
@EqualsAndHashCode
public class NotNullDummy {
    public static AddressLocal notNullAddressUpperLocalDummy() {
        AddressLocal addressLocal = new AddressLocal();
        ReflectionTestUtils.setField(addressLocal, "addressName", "안양");
        ReflectionTestUtils.setField(addressLocal, "level", 1);
        ReflectionTestUtils.setField(addressLocal, "allowDelivery", true);
        ReflectionTestUtils.setField(addressLocal, "upperLocal", null);
        return addressLocal;
    }

    public static AddressLocal notNullAddressLocalDummy(AddressLocal addressLocal1) {
        AddressLocal addressLocal = new AddressLocal();
        ReflectionTestUtils.setField(addressLocal, "addressName", "안양");
        ReflectionTestUtils.setField(addressLocal, "level", 2);
        ReflectionTestUtils.setField(addressLocal, "allowDelivery", true);
        ReflectionTestUtils.setField(addressLocal, "upperLocal", addressLocal1);
        return addressLocal;
    }

    public static DayLabor notNullDayLaborDummy(AddressLocal addressLocal) {
        DayLabor dayLabor = new DayLabor();
        ReflectionTestUtils.setField(dayLabor, "addressLocal", addressLocal);
        ReflectionTestUtils.setField(dayLabor, "maxLabor", 10);

        return dayLabor;
    }

    public static MemberGrade notNullMemberGradeDummy(StatusCode statusCode) {
        MemberGrade memberGrade = new MemberGrade();
        ReflectionTestUtils.setField(memberGrade, "renewalPeriodStatusCode", statusCode);
        ReflectionTestUtils.setField(memberGrade, "name", "콤틴");
        ReflectionTestUtils.setField(memberGrade, "accumulateAmount", 0L);
        ReflectionTestUtils.setField(memberGrade, "isDefault", true);
        return memberGrade;
    }

    public static StatusCode notNullMemberStatusUseDummy() {
        StatusCode statusCode = new StatusCode();
        ReflectionTestUtils.setField(statusCode, "statusCodeName", MemberStatus.ACTIVATION.getValue());
        ReflectionTestUtils.setField(statusCode, "isUsed", true);
        ReflectionTestUtils.setField(statusCode, "priority", 1);
        ReflectionTestUtils.setField(statusCode, "groupCodeName", MemberStatus.GROUP);
        ReflectionTestUtils.setField(statusCode, "explanation", "설명");
        return statusCode;
    }

    public static StatusCode notNullMemberStatusDeleteDummy() {
        StatusCode statusCode = new StatusCode();
        ReflectionTestUtils.setField(statusCode, "statusCodeName", MemberStatus.ACTIVATION.getValue());
        ReflectionTestUtils.setField(statusCode, "isUsed", true);
        ReflectionTestUtils.setField(statusCode, "priority", 2);
        ReflectionTestUtils.setField(statusCode, "groupCodeName", MemberStatus.GROUP);
        ReflectionTestUtils.setField(statusCode, "explanation", "설명");
        return statusCode;
    }

    public static StatusCode notNullRenewalPeriodStatusDummy() {
        StatusCode statusCode = new StatusCode();
        ReflectionTestUtils.setField(statusCode, "statusCodeName", RenewalPeriod.PERIOD.getValue());
        ReflectionTestUtils.setField(statusCode, "isUsed", true);
        ReflectionTestUtils.setField(statusCode, "priority", 3);
        ReflectionTestUtils.setField(statusCode, "groupCodeName", RenewalPeriod.GROUP);
        ReflectionTestUtils.setField(statusCode, "explanation", "설명");
        return statusCode;
    }

    public static StatusCode notNullAddressListUseStatusDummy() {
        StatusCode statusCode = new StatusCode();
        ReflectionTestUtils.setField(statusCode, "statusCodeName", AddressStatus.USE.getValue());
        ReflectionTestUtils.setField(statusCode, "isUsed", true);
        ReflectionTestUtils.setField(statusCode, "priority", 4);
        ReflectionTestUtils.setField(statusCode, "groupCodeName", AddressStatus.GROUP);
        ReflectionTestUtils.setField(statusCode, "explanation", "설명");
        return statusCode;
    }

    public static StatusCode notNullAddressListDeleteStatusDummy() {
        StatusCode statusCode = new StatusCode();
        ReflectionTestUtils.setField(statusCode, "statusCodeName", AddressStatus.DELETE.getValue());
        ReflectionTestUtils.setField(statusCode, "isUsed", true);
        ReflectionTestUtils.setField(statusCode, "priority", 5);
        ReflectionTestUtils.setField(statusCode, "groupCodeName", AddressStatus.GROUP);
        ReflectionTestUtils.setField(statusCode, "explanation", "설명");
        return statusCode;
    }

    public static Member notNullRecommendedMemberDummy(StatusCode statusCode, MemberGrade memberGrade) {
        Member member = new Member();
        ReflectionTestUtils.setField(member, "recommendMember", null);
        ReflectionTestUtils.setField(member, "memberStatusCodes", statusCode);
        ReflectionTestUtils.setField(member, "memberGrades", memberGrade);
        ReflectionTestUtils.setField(member, "email", "jwoo1015@naver.com");
        ReflectionTestUtils.setField(member, "password", "1234567");
        ReflectionTestUtils.setField(member, "phoneNumber", "01012341234");
        ReflectionTestUtils.setField(member, "name", "최정우");
        ReflectionTestUtils.setField(member, "birthDate", LocalDate.now());
        ReflectionTestUtils.setField(member, "nickname", "회원1");
        ReflectionTestUtils.setField(member, "gender", "남");
        ReflectionTestUtils.setField(member, "accumulatePurchaseAmount", 0L);
        ReflectionTestUtils.setField(member, "nextRenewalGradeDate", LocalDate.now());
        ReflectionTestUtils.setField(member, "isSocial", false);
        ReflectionTestUtils.setField(member, "encodedEmailForSearch", "???");

        return member;
    }

    public static Member notNullMemberDummy1(Member member1, StatusCode statusCode, MemberGrade memberGrade) {
        Member member = new Member();
        ReflectionTestUtils.setField(member, "recommendMember", member1);
        ReflectionTestUtils.setField(member, "memberStatusCodes", statusCode);
        ReflectionTestUtils.setField(member, "memberGrades", memberGrade);
        ReflectionTestUtils.setField(member, "email", "jwoo1016@naver.com");
        ReflectionTestUtils.setField(member, "password", "1234567");
        ReflectionTestUtils.setField(member, "phoneNumber", "01012341234");
        ReflectionTestUtils.setField(member, "name", "최정우");
        ReflectionTestUtils.setField(member, "birthDate", LocalDate.now());
        ReflectionTestUtils.setField(member, "nickname", "회원2");
        ReflectionTestUtils.setField(member, "gender", "남");
        ReflectionTestUtils.setField(member, "accumulatePurchaseAmount", 0L);
        ReflectionTestUtils.setField(member, "nextRenewalGradeDate", LocalDate.now());
        ReflectionTestUtils.setField(member, "isSocial", false);

        return member;
    }

    public static Member notNullMemberDummy2(Member member1, StatusCode statusCode, MemberGrade memberGrade) {
        Member member = new Member();
        ReflectionTestUtils.setField(member, "recommendMember", member1);
        ReflectionTestUtils.setField(member, "memberStatusCodes", statusCode);
        ReflectionTestUtils.setField(member, "memberGrades", memberGrade);
        ReflectionTestUtils.setField(member, "userAuthorityNo", statusCode);
        ReflectionTestUtils.setField(member, "email", "jwoo1017@naver.com");
        ReflectionTestUtils.setField(member, "password", "1234567");
        ReflectionTestUtils.setField(member, "phoneNumber", "01012341234");
        ReflectionTestUtils.setField(member, "name", "최정우");
        ReflectionTestUtils.setField(member, "birthDate", LocalDate.now());
        ReflectionTestUtils.setField(member, "nickname", "회원3");
        ReflectionTestUtils.setField(member, "gender", "남");
        ReflectionTestUtils.setField(member, "accumulatePurchaseAmount", 0L);
        ReflectionTestUtils.setField(member, "nextRenewalGradeDate", LocalDate.now());
        ReflectionTestUtils.setField(member, "isSocial", false);

        return member;
    }

}
