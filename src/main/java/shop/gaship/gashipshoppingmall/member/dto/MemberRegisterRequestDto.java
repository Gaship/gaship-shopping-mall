package shop.gaship.gashipshoppingmall.member.dto;

import java.time.LocalDate;
import lombok.Getter;

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
}
