package shop.gaship.gashipshoppingmall.member.memberTestUtils;

import shop.gaship.gashipshoppingmall.member.dto.MemberModifyRequestDto;
import shop.gaship.gashipshoppingmall.member.dto.MemberRegisterRequestDto;
import shop.gaship.gashipshoppingmall.member.dto.MemberResponseDto;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
public class MemberTestUtils {

    public static MemberRegisterRequestDto memberRegisterRequestDto(String recommendMemberNickname, String email, String password, String phoneNumber, String name, LocalDate birthDate, String nickname, String gender) {

        return MemberRegisterRequestDto.builder()
                .recommendMemberNickname(recommendMemberNickname)
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .name(name)
                .birthDate(birthDate)
                .nickname(nickname)
                .gender(gender)
                .build();
    }

    public static MemberModifyRequestDto memberModifyRequestDto(Integer memberNo, String email, String password, String phoneNumber, String name, String nickname, String gender) {
        return MemberModifyRequestDto.builder()
                .memberNo(memberNo)
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .name(name)
                .nickname(nickname)
                .gender(gender)
                .build();
    }

    public static MemberResponseDto memberResponseDto(String recommendMemberNickname, String email, String password, String phoneNumber, String name, LocalDate birthDate, String nickname, String gender, Long accumulatePurchaseAmount, LocalDate nextRenewGradeDate, LocalDateTime registerDatetime,LocalDateTime modifyDatetime,Boolean isBlackMember) {

        return MemberResponseDto.builder()
                .recommendMemberNickname(recommendMemberNickname)
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .name(name)
                .birthDate(birthDate)
                .nickname(nickname)
                .gender(gender)
                .accumulatePurchaseAmount(accumulatePurchaseAmount)
                .nextRenewalGradeDate(nextRenewGradeDate)
                .registerDatetime(registerDatetime)
                .modifyDatetime(modifyDatetime)
                .isBlackMember(isBlackMember)
                .build();
    }
}
