package shop.gaship.gashipshoppingmall.member.memberTestDummy;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.member.dto.request.MemberModifyRequestDto;
import shop.gaship.gashipshoppingmall.member.dto.response.MemberPageResponseDto;
import shop.gaship.gashipshoppingmall.member.dto.response.MemberResponseDto;
import shop.gaship.gashipshoppingmall.member.dummy.StatusCodeDummy;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.entity.MembersRole;
import shop.gaship.gashipshoppingmall.membergrade.dummy.MemberGradeDtoDummy;
import shop.gaship.gashipshoppingmall.membergrade.dummy.MemberGradeDummy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * packageName    : shop.gaship.gashipshoppingmall.member.memberTestUtils
 * fileName       : MemberTestUtils
 * author         : choijungwoo
 * date           : 2022/07/13
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/13        choijungwoo       최초 생성
 */
public class MemberTestDummy {
    private static final Integer memberNo = 1;
    private static final String recommendMemberNickname = "최정우친구";
    private static final String email = "abcd1010@naver.com";
    private static final String password = "qwer1234!";
    private static final String phoneNumber = "01053171234";
    private static final String name = "최정우";
    private static final LocalDate birthDate = LocalDate.now();
    private static final String nickname = "정우";
    private static final String gender = "남";
    private static final Long accumulatePurchaseAmount = 1L;
    private static final LocalDate nextRenewalGradeDate = LocalDate.now();
    private static final LocalDateTime registerDatetime = LocalDateTime.now();
    private static final LocalDateTime modifyDatetime = LocalDateTime.now();

    public static MemberModifyRequestDto memberModifyRequestDto() {
        MemberModifyRequestDto dummy = new MemberModifyRequestDto();
        ReflectionTestUtils.setField(dummy, "memberNo", memberNo);
        ReflectionTestUtils.setField(dummy, "password", password);
        ReflectionTestUtils.setField(dummy, "phoneNumber", phoneNumber);
        ReflectionTestUtils.setField(dummy, "name", name);
        ReflectionTestUtils.setField(dummy, "nickname", nickname);
        ReflectionTestUtils.setField(dummy, "gender", gender);

        return dummy;
    }

    public static MemberResponseDto memberResponseDto() {

        return MemberResponseDto.builder()
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .name(name)
                .birthDate(birthDate)
                .nickname(nickname)
                .gender(gender)
                .accumulatePurchaseAmount(accumulatePurchaseAmount)
                .nextRenewalGradeDate(nextRenewalGradeDate)
                .registerDatetime(registerDatetime)
                .modifyDatetime(modifyDatetime)
                .build();
    }

    public static List<Member> CreateTestMemberEntityList() {
        List<Member> list = new ArrayList<>();
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder().recommendMember(null)
                    .memberStatusCodes(StatusCodeDummy.dummy())
                    .memberGrades(null)
                    .email("jwoo1016" + i + "@naver.com")
                    .password("qwer1234")
                    .phoneNumber("010531783" + (i - 1) / 10 + (i - 1) % 10)
                    .name("최정우")
                    .birthDate(LocalDate.now())
                    .nickname(String.valueOf(i))
                    .gender("남")
                    .accumulatePurchaseAmount(0L)
                    .nextRenewalGradeDate(LocalDate.now())
                    .build();

            list.add(member);
        });
        return list;
    }

    public static MemberPageResponseDto<MemberResponseDto,Member> CreateTestMemberPageResponseDto(){
        Pageable pageable = PageRequest.of(0,10);
        Function<Member, MemberResponseDto> fn = (Member member)-> MemberResponseDto.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .phoneNumber(member.getPhoneNumber())
                .name(member.getName())
                .birthDate(member.getBirthDate())
                .nickname(member.getNickname())
                .gender(member.getGender())
                .accumulatePurchaseAmount(member.getAccumulatePurchaseAmount())
                .nextRenewalGradeDate(member.getNextRenewalGradeDate())
                .registerDatetime(member.getRegisterDatetime())
                .modifyDatetime(member.getModifyDatetime())
                .build();
        Page<Member> page = new PageImpl<>(MemberTestDummy.CreateTestMemberEntityList(), pageable, 100);

        return new MemberPageResponseDto<>(page, fn);
    }

    public static Member member1() {

        return Member.builder()
                .memberNo(0)
                .recommendMember(null)
                .memberStatusCodes(StatusCodeDummy.dummy())
                .memberGrades(MemberGradeDummy.dummy(
                        MemberGradeDtoDummy.requestDummy("일반", 0L),
                        StatusCodeDummy.dummy()
                ))
                .roleSet(List.of(MembersRole.ROLE_USER))
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .name(name)
                .birthDate(birthDate)
                .nickname(nickname)
                .gender(gender)
                .accumulatePurchaseAmount(accumulatePurchaseAmount)
                .nextRenewalGradeDate(nextRenewalGradeDate)
                .build();
    }

    public static Member memberEntityNotFlushed() {

        return Member.builder()
                .memberNo(0)
                .recommendMember(null)
                .memberStatusCodes(StatusCodeDummy.dummy())
                .memberGrades(MemberGradeDummy.dummy(
                        MemberGradeDtoDummy.requestDummy("일반", 0L),
                        StatusCodeDummy.dummy()
                ))
                .roleSet(List.of(MembersRole.ROLE_USER))
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .name(name)
                .birthDate(birthDate)
                .nickname(nickname)
                .gender(gender)
                .accumulatePurchaseAmount(accumulatePurchaseAmount)
                .nextRenewalGradeDate(nextRenewalGradeDate)
                .isSocial(false)
                .build();
    }
}
