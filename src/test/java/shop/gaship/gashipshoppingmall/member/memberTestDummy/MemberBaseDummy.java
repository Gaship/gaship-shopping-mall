package shop.gaship.gashipshoppingmall.member.memberTestDummy;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.member.dto.request.MemberModifyByAdminDto;
import shop.gaship.gashipshoppingmall.member.dto.request.MemberModifyRequestDto;
import shop.gaship.gashipshoppingmall.member.dto.response.MemberResponseDto;
import shop.gaship.gashipshoppingmall.member.dummy.MemberStatus;
import shop.gaship.gashipshoppingmall.member.dummy.StatusCodeDummy;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.entity.MembersRole;
import shop.gaship.gashipshoppingmall.membergrade.dummy.MemberGradeDtoDummy;
import shop.gaship.gashipshoppingmall.membergrade.dummy.MemberGradeDummy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import shop.gaship.gashipshoppingmall.response.PageResponse;

public class MemberBaseDummy {
    private static final Integer memberNo = 1;
    private static final String recommendMemberNickname = "최정우친구";
    private static final String email = "abcd1010@naver.com";
    private static final String memberStatus = MemberStatus.ACTIVATION.getValue();
    private static final List<String> authorities = List.of("1","2");
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
    private static final Boolean social = false;

    public static MemberModifyRequestDto memberModifyRequestDtoDummy() {
        MemberModifyRequestDto dummy = new MemberModifyRequestDto();
        ReflectionTestUtils.setField(dummy, "memberNo", memberNo);
        ReflectionTestUtils.setField(dummy, "password", password);
        ReflectionTestUtils.setField(dummy, "phoneNumber", phoneNumber);
        ReflectionTestUtils.setField(dummy, "name", name);
        ReflectionTestUtils.setField(dummy, "nickname", nickname);
        ReflectionTestUtils.setField(dummy, "gender", gender);

        return dummy;
    }

    public static MemberModifyByAdminDto memberModifyByAdminDto() {
        MemberModifyByAdminDto dummy = new MemberModifyByAdminDto();
        ReflectionTestUtils.setField(dummy, "memberNo", memberNo);
        ReflectionTestUtils.setField(dummy, "status", MemberStatus.ACTIVATION.getValue());

        return dummy;
    }

    public static MemberResponseDto memberResponseDto() {

        return MemberResponseDto.builder()
                .memberNo(memberNo)
                .memberStatus(memberStatus)
                .email(email)
                .authorities(authorities)
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
                .social(social)
                .build();
    }

    public static List<MemberResponseDto> MemberResponseDtoDummy() {
        List<MemberResponseDto> list = new ArrayList<>();
        IntStream.rangeClosed(1, 100).forEach(i -> {
            MemberResponseDto dto = MemberResponseDto.builder()
                    .memberNo(i + 1)
                    .memberStatus(MemberStatus.ACTIVATION.getValue())
                    .email("jwoo1016" + i + "@naver.com")
                    .authorities(List.of("1","2"))
                    .password("qwer1234")
                    .nickname(String.valueOf(i))
                    .name("최정우")
                    .gender("남")
                    .phoneNumber("010531783" + (i - 1) / 10 + (i - 1) % 10)
                    .birthDate(LocalDate.now())
                    .accumulatePurchaseAmount(0L)
                    .nextRenewalGradeDate(LocalDate.now())
                    .registerDatetime(LocalDateTime.now())
                    .modifyDatetime(LocalDateTime.now())
                    .social(false)
                    .build();

            list.add(dto);
        });
        return list;
    }

    public static PageResponse<MemberResponseDto> createTestMemberPageResponseDto(){
        Pageable pageable = PageRequest.of(0,10);
        List<MemberResponseDto> dtoList = List.of(memberResponseDto(), memberResponseDto(), memberResponseDto());
        Page<MemberResponseDto> page = new PageImpl<>(dtoList, pageable, 100);

        return new PageResponse<>(page);
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
