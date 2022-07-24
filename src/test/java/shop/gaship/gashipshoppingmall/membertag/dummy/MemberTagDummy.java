package shop.gaship.gashipshoppingmall.membertag.dummy;

import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.addressLocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.dayLabor.entity.DayLabor;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.memberTestDummy.MemberTestDummy;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.membertag.dto.request.MemberTagRequestDto;
import shop.gaship.gashipshoppingmall.membertag.dto.response.MemberTagResponseDto;
import shop.gaship.gashipshoppingmall.membertag.entity.MemberTag;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.status.AddressStatus;
import shop.gaship.gashipshoppingmall.statuscode.status.MemberStatus;
import shop.gaship.gashipshoppingmall.statuscode.status.RenewalPeriod;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author 최정우
 * @since 1.0
 */
public class MemberTagDummy {
    public static MemberTagRequestDto memberTagRequestDtoDummy() {
        return MemberTagRequestDto
                .builder()
                .memberNo(1)
                .tagNoList(List.of(1, 2, 3, 4, 5))
                .build();
    }


    public static MemberTagResponseDto memberTagResponseDto(Member member,Tag tag) {
        return new MemberTagResponseDto(List.of(
                MemberTag.builder().memberTagNo(1).member(member).tag(tag).build(),
                MemberTag.builder().memberTagNo(2).member(member).tag(tag).build(),
                MemberTag.builder().memberTagNo(3).member(member).tag(tag).build(),
                MemberTag.builder().memberTagNo(4).member(member).tag(tag).build(),
                MemberTag.builder().memberTagNo(5).member(member).tag(tag).build()));
    }

    public static MemberTagResponseDto notNullMemberTagResponseDto() {
        return new MemberTagResponseDto(List.of(
                MemberTag.builder().memberTagNo(1).member(MemberDummy()).tag(TagDummy1()).build(),
                MemberTag.builder().memberTagNo(2).member(MemberDummy()).tag(TagDummy2()).build(),
                MemberTag.builder().memberTagNo(3).member(MemberDummy()).tag(TagDummy3()).build(),
                MemberTag.builder().memberTagNo(4).member(MemberDummy()).tag(TagDummy4()).build(),
                MemberTag.builder().memberTagNo(5).member(MemberDummy()).tag(TagDummy5()).build()));
    }

    public static Tag TagDummy1(){
        return Tag.builder().tagNo(1).title("1").build();
    }
    public static Tag TagDummy2(){
        return Tag.builder().tagNo(2).title("2").build();
    }
    public static Tag TagDummy3(){
        return Tag.builder().tagNo(3).title("3").build();
    }
    public static Tag TagDummy4(){
        return Tag.builder().tagNo(4).title("4").build();
    }
    public static Tag TagDummy5(){
        return Tag.builder().tagNo(5).title("5").build();
    }

    public static Member MemberDummy() {
        Member member = new Member();
        ReflectionTestUtils.setField(member,"memberNo",1);
        ReflectionTestUtils.setField(member, "recommendMember", null);
        ReflectionTestUtils.setField(member, "memberStatusCodes", MemberStatusUseDummy());
        ReflectionTestUtils.setField(member, "memberGrades", MemberGradeDummy(MemberStatusUseDummy()));
        ReflectionTestUtils.setField(member, "email", "jwoo1015@naver.com");
        ReflectionTestUtils.setField(member, "password", "1234567");
        ReflectionTestUtils.setField(member, "phoneNumber", "01012341234");
        ReflectionTestUtils.setField(member, "name", "최정우");
        ReflectionTestUtils.setField(member, "birthDate", LocalDate.now());
        ReflectionTestUtils.setField(member, "nickname", "회원1");
        ReflectionTestUtils.setField(member, "gender", "남");
        ReflectionTestUtils.setField(member, "accumulatePurchaseAmount", 0L);
        ReflectionTestUtils.setField(member, "nextRenewalGradeDate", LocalDate.now());

        return member;
    }



    public static StatusCode MemberStatusUseDummyUnFlushed() {
        StatusCode statusCode = new StatusCode();
        ReflectionTestUtils.setField(statusCode, "statusCodeName", MemberStatus.ACTIVATION.getValue());
        ReflectionTestUtils.setField(statusCode, "isUsed", true);
        ReflectionTestUtils.setField(statusCode, "priority", 1);
        ReflectionTestUtils.setField(statusCode, "groupCodeName", MemberStatus.GROUP);
        ReflectionTestUtils.setField(statusCode, "explanation", "설명");
        return statusCode;
    }

    public static StatusCode MemberStatusUseDummy() {
        StatusCode statusCode = new StatusCode();
        ReflectionTestUtils.setField(statusCode,"statusCodeNo",1);
        ReflectionTestUtils.setField(statusCode, "statusCodeName", MemberStatus.ACTIVATION.getValue());
        ReflectionTestUtils.setField(statusCode, "isUsed", true);
        ReflectionTestUtils.setField(statusCode, "priority", 1);
        ReflectionTestUtils.setField(statusCode, "groupCodeName", MemberStatus.GROUP);
        ReflectionTestUtils.setField(statusCode, "explanation", "설명");
        return statusCode;
    }

    public static MemberGrade MemberGradeDummyUnFlushed() {
        MemberGrade memberGrade = new MemberGrade();
        ReflectionTestUtils.setField(memberGrade, "renewalPeriodStatusCode", RenewalPeriodStatusDummy());
        ReflectionTestUtils.setField(memberGrade, "name", "콤틴");
        ReflectionTestUtils.setField(memberGrade, "accumulateAmount", 0L);
        ReflectionTestUtils.setField(memberGrade, "isDefault", true);
        return memberGrade;
    }

    public static MemberGrade MemberGradeDummy(StatusCode statusCode) {
        MemberGrade memberGrade = new MemberGrade();
        ReflectionTestUtils.setField(memberGrade,"no",1);
        ReflectionTestUtils.setField(memberGrade, "renewalPeriodStatusCode", statusCode);
        ReflectionTestUtils.setField(memberGrade, "name", "콤틴");
        ReflectionTestUtils.setField(memberGrade, "accumulateAmount", 0L);
        ReflectionTestUtils.setField(memberGrade, "isDefault", true);
        return memberGrade;
    }


    public static StatusCode RenewalPeriodStatusDummyUnFlushed() {
        StatusCode statusCode = new StatusCode();
        ReflectionTestUtils.setField(statusCode, "statusCodeName", RenewalPeriod.PERIOD.getValue());
        ReflectionTestUtils.setField(statusCode, "isUsed", true);
        ReflectionTestUtils.setField(statusCode, "priority", 3);
        ReflectionTestUtils.setField(statusCode, "groupCodeName", RenewalPeriod.GROUP);
        ReflectionTestUtils.setField(statusCode, "explanation", "설명");
        return statusCode;
    }

    public static StatusCode RenewalPeriodStatusDummy() {
        StatusCode statusCode = new StatusCode();
        ReflectionTestUtils.setField(statusCode,"statusCodeNo",2);
        ReflectionTestUtils.setField(statusCode, "statusCodeName", RenewalPeriod.PERIOD.getValue());
        ReflectionTestUtils.setField(statusCode, "isUsed", true);
        ReflectionTestUtils.setField(statusCode, "priority", 3);
        ReflectionTestUtils.setField(statusCode, "groupCodeName", RenewalPeriod.GROUP);
        ReflectionTestUtils.setField(statusCode, "explanation", "설명");
        return statusCode;
    }

    public static List<MemberTag> memberTagList() {
        List<MemberTag> memberTagList = new ArrayList<>();
        IntStream.rangeClosed(0, 4).forEach(i -> {
            MemberTag memberTag = MemberTag.builder()
                    .member(MemberTestDummy.memberEntityNotFlushed())
                    .tag(Tag.builder().tagNo(i + 1).title("title....." + i).build())
                    .build();
            memberTagList.add(memberTag);
        });
        return memberTagList;
    }

    public static List<MemberTag> memberTagList100Size() {
        List<MemberTag> memberTagList = new ArrayList<>();
        IntStream.rangeClosed(1, 100).forEach(i -> {
            MemberTag memberTag = MemberTag.builder()
                    .member(MemberTestDummy.member1())
                    .tag(Tag.builder().tagNo(i).title("title" + i).build())
                    .build();
            memberTagList.add(memberTag);
        });
        return memberTagList;
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

        return member;
    }

    public static Member notNullMemberDummy2(Member member1, StatusCode statusCode, MemberGrade memberGrade) {
        Member member = new Member();
        ReflectionTestUtils.setField(member, "recommendMember", member1);
        ReflectionTestUtils.setField(member, "memberStatusCodes", statusCode);
        ReflectionTestUtils.setField(member, "memberGrades", memberGrade);
        ReflectionTestUtils.setField(member, "email", "jwoo1017@naver.com");
        ReflectionTestUtils.setField(member, "password", "1234567");
        ReflectionTestUtils.setField(member, "phoneNumber", "01012341234");
        ReflectionTestUtils.setField(member, "name", "최정우");
        ReflectionTestUtils.setField(member, "birthDate", LocalDate.now());
        ReflectionTestUtils.setField(member, "nickname", "회원3");
        ReflectionTestUtils.setField(member, "gender", "남");
        ReflectionTestUtils.setField(member, "accumulatePurchaseAmount", 0L);
        ReflectionTestUtils.setField(member, "nextRenewalGradeDate", LocalDate.now());

        return member;
    }

    public static MemberTag notNullMemberTag(Member member,Tag tag){
        MemberTag memberTag = new MemberTag();
        ReflectionTestUtils.setField(member,"member",member);
        ReflectionTestUtils.setField(member,"tag",tag);
        return memberTag;
    }


    public static Tag notNullTagDummy1(){
        Tag tag = new Tag();
        ReflectionTestUtils.setField(tag,"title","1");

        return tag;
    }

    public static Tag notNullTagDummy2(){
        Tag tag = new Tag();
        ReflectionTestUtils.setField(tag,"title","2");

        return tag;
    }
    public static Tag notNullTagDummy3(){
        Tag tag = new Tag();
        ReflectionTestUtils.setField(tag,"title","3");

        return tag;
    }
    public static Tag notNullTagDummy4(){
        Tag tag = new Tag();
        ReflectionTestUtils.setField(tag,"title","4");

        return tag;
    }
    public static Tag notNullTagDummy5(){
        Tag tag = new Tag();
        ReflectionTestUtils.setField(tag,"title","5");

        return tag;
    }


}
