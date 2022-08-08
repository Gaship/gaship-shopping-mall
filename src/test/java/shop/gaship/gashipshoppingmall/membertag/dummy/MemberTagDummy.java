package shop.gaship.gashipshoppingmall.membertag.dummy;

import java.time.LocalDate;
import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.memberTestDummy.MemberTestDummy;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.membertag.dto.request.MemberTagRequestDto;
import shop.gaship.gashipshoppingmall.membertag.dto.response.MemberTagResponseDto;
import shop.gaship.gashipshoppingmall.membertag.entity.MemberTag;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.status.AddressStatus;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author 최정우
 * @since 1.0
 */
public class MemberTagDummy {
    public static MemberTagRequestDto memberTagRequestDtoDummy(Integer memberNo, List<Integer> tagIds) {
        MemberTagRequestDto memberTagRequestDto = new MemberTagRequestDto();
        ReflectionTestUtils.setField(memberTagRequestDto,"memberNo",memberNo);
        ReflectionTestUtils.setField(memberTagRequestDto,"tagIds",tagIds);
        
        return memberTagRequestDto;
        
    }

    public static List<MemberTagResponseDto> memberTagResponseDtoListDummy() {
        List<MemberTagResponseDto> list = new ArrayList();
        IntStream.rangeClosed(1, 5).forEach(i -> {
            MemberTagResponseDto dto = MemberTagResponseDto.builder()
                    .tagNo(i)
                    .title("title.." + i)
                    .build();
            list.add(dto);
        });
        return list;
    }

    public static List<MemberTag> memberTagListDummy() {
        List<MemberTag> memberTagList = new ArrayList<>();
        IntStream.rangeClosed(1, 5).forEach(i -> {
            MemberTag memberTag = MemberTag.builder()
                    .member(memberDummy(statusCodeDummy(),memberGradeDummy(statusCodeDummy())))
                    .tag(Tag.builder().tagNo(i).title("title....." + i).build())
                    .build();
            memberTagList.add(memberTag);
        });
        return memberTagList;
    }

    public static List<MemberTag> memberTagList100SizeDummy() {
        List<MemberTag> memberTagList = new ArrayList<>();
        IntStream.rangeClosed(1, 100).forEach(i -> {
            MemberTag memberTag = MemberTag.builder()
                    .member(memberDummy(statusCodeDummy(),memberGradeDummy(statusCodeDummy())))
                    .tag(Tag.builder().tagNo(i).title("title" + i).build())
                    .build();
            memberTagList.add(memberTag);
        });
        return memberTagList;
    }

    public static Member memberDummy(StatusCode statusCode, MemberGrade memberGrade) {
        Member member = new Member();
        ReflectionTestUtils.setField(member, "memberNo", 1);
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

        return member;
    }

    public static StatusCode statusCodeDummy() {
        StatusCode statusCode = new StatusCode();
        ReflectionTestUtils.setField(statusCode, "statusCodeNo", 1);
        ReflectionTestUtils.setField(statusCode, "statusCodeName", AddressStatus.USE.getValue());
        ReflectionTestUtils.setField(statusCode, "isUsed", true);
        ReflectionTestUtils.setField(statusCode, "priority", 4);
        ReflectionTestUtils.setField(statusCode, "groupCodeName", AddressStatus.GROUP);
        ReflectionTestUtils.setField(statusCode, "explanation", "설명");
        return statusCode;
    }

    public static MemberGrade memberGradeDummy(StatusCode statusCode) {
        MemberGrade memberGrade = new MemberGrade();
        ReflectionTestUtils.setField(memberGrade, "no", 1);
        ReflectionTestUtils.setField(memberGrade, "renewalPeriodStatusCode", statusCode);
        ReflectionTestUtils.setField(memberGrade, "name", "콤틴");
        ReflectionTestUtils.setField(memberGrade, "accumulateAmount", 0L);
        ReflectionTestUtils.setField(memberGrade, "isDefault", true);
        return memberGrade;
    }

}
