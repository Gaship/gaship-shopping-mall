package shop.gaship.gashipshoppingmall.member.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

import java.time.LocalDate;

/**
 * 멤버 정보 수정을 위한 정보를 담는 dto 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Getter
public class MemberModifyRequestDto {
    private Integer memberNo;
    private StatusCode statusCode;
    private MemberGrade memberGrade;
    private String email;
    private String password;
    private String phoneNumber;
    private String name;
    private String nickname;
    private String gender;

    /**
     * Instantiates a new Member modify request dto.
     *
     * @param memberNo    the member no
     * @param statusCode  the status code
     * @param memberGrade the member grade
     * @param email       the email
     * @param password    the password
     * @param phoneNumber the phone number
     * @param name        the name
     * @param nickname    the nickname
     * @param gender      the gender
     */
    @Builder
    public MemberModifyRequestDto(Integer memberNo, StatusCode statusCode, MemberGrade memberGrade, String email, String password, String phoneNumber, String name, String nickname, String gender) {
        this.memberNo = memberNo;
        this.statusCode = statusCode;
        this.memberGrade = memberGrade;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.nickname = nickname;
        this.gender = gender;
    }
}
