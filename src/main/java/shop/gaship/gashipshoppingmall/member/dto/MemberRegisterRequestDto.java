package shop.gaship.gashipshoppingmall.member.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * packageName    : shop.gaship.gashipshoppingmall.member.dto
 * fileName       : MemberRequestDto
 * author         : choijungwoo
 * date           : 2022/07/11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/11        choijungwoo       최초 생성
 */
@Getter
public class MemberRegisterRequestDto {
    private String recommendMemberNickname;
    private String email;
    private String password;
    private String phoneNumber;
    private String name;
    private LocalDate birthDate;
    private String nickname;
    private String gender;

    @Builder
    public MemberRegisterRequestDto(String recommendMemberNickname, String email, String password, String phoneNumber, String name, LocalDate birthDate, String nickname, String gender) {
        this.recommendMemberNickname = recommendMemberNickname;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.birthDate = birthDate;
        this.nickname = nickname;
        this.gender = gender;
    }
}
