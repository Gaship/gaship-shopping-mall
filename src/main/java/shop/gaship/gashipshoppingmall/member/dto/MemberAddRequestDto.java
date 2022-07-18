package shop.gaship.gashipshoppingmall.member.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;


/**
 * 멤버 등록을 위한 정보를 담는 dto 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Getter
public class MemberAddRequestDto {
    private String recommendMemberNickname;
    private String email;
    private String password;
    private String phoneNumber;
    private String name;
    private LocalDate birthDate;
    private String nickname;
    private String gender;

    /**
     * Instantiates a new Member add request dto.
     *
     * @param recommendMemberNickname the recommend member nickname
     * @param email                   the email
     * @param password                the password
     * @param phoneNumber             the phone number
     * @param name                    the name
     * @param birthDate               the birth date
     * @param nickname                the nickname
     * @param gender                  the gender
     */
    @Builder
    public MemberAddRequestDto(String recommendMemberNickname, String email, String password, String phoneNumber, String name, LocalDate birthDate, String nickname, String gender) {
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
