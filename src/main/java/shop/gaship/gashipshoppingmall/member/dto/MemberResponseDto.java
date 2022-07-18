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
 * 멤버 정보 수정을 위한 정보를 담는 dto 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Getter
public class MemberResponseDto {
    private String recommendMemberNickname;
    private String email;
    private String password;
    private String phoneNumber;
    private String name;
    private LocalDate birthDate;
    private String nickname;
    private String gender;
    private Long accumulatePurchaseAmount;
    private LocalDate nextRenewalGradeDate;
    private LocalDateTime registerDatetime;
    private LocalDateTime modifyDatetime;
    private Boolean isBlackMember;

    /**
     * Instantiates a new Member response dto.
     *
     * @param recommendMemberNickname  the recommend member nickname
     * @param email                    the email
     * @param password                 the password
     * @param phoneNumber              the phone number
     * @param name                     the name
     * @param birthDate                the birth date
     * @param nickname                 the nickname
     * @param gender                   the gender
     * @param accumulatePurchaseAmount the accumulate purchase amount
     * @param nextRenewalGradeDate     the next renewal grade date
     * @param registerDatetime         the register datetime
     * @param modifyDatetime           the modify datetime
     * @param isBlackMember            the is black member
     */
    @Builder
    public MemberResponseDto(String recommendMemberNickname, String email, String password, String phoneNumber, String name, LocalDate birthDate, String nickname, String gender, Long accumulatePurchaseAmount, LocalDate nextRenewalGradeDate, LocalDateTime registerDatetime, LocalDateTime modifyDatetime, Boolean isBlackMember) {
        this.recommendMemberNickname = recommendMemberNickname;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.birthDate = birthDate;
        this.nickname = nickname;
        this.gender = gender;
        this.accumulatePurchaseAmount = accumulatePurchaseAmount;
        this.nextRenewalGradeDate = nextRenewalGradeDate;
        this.registerDatetime = registerDatetime;
        this.modifyDatetime = modifyDatetime;
        this.isBlackMember = isBlackMember;
    }
}
