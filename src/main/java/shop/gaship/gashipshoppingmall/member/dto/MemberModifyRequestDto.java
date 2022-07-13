package shop.gaship.gashipshoppingmall.member.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

/**
 * packageName    : shop.gaship.gashipshoppingmall.member.dto
 * fileName       : MemberModifyDto
 * author         : choijungwoo
 * date           : 2022/07/11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/11        choijungwoo       최초 생성
 */
@Getter
public class MemberModifyRequestDto {
    private Integer memberNo;
    private String email;
    private String password;
    private String phoneNumber;
    private String name;
    private String nickname;
    private String gender;

    @Builder
    public MemberModifyRequestDto(Integer memberNo, String email, String password, String phoneNumber, String name, String nickname, String gender) {
        this.memberNo = memberNo;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.nickname = nickname;
        this.gender = gender;
    }
}
