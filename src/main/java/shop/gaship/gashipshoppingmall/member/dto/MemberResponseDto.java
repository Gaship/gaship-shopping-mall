package shop.gaship.gashipshoppingmall.member.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;


/**
 * 멤버 정보 수정을 위한 정보를 담는 dto 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Getter
public class MemberResponseDto {
    private final String recommendMemberNickname;
    private final String email;
    private final String password;
    private final String phoneNumber;
    private final String name;
    private final LocalDate birthDate;
    private final String nickname;
    private final String gender;
    private final Long accumulatePurchaseAmount;
    private final LocalDate nextRenewalGradeDate;
    private final LocalDateTime registerDatetime;
    private final LocalDateTime modifyDatetime;

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
     */
    @Builder
    public MemberResponseDto(String recommendMemberNickname, String email, String password,
                             String phoneNumber, String name, LocalDate birthDate, String nickname,
                             String gender, Long accumulatePurchaseAmount,
                             LocalDate nextRenewalGradeDate, LocalDateTime registerDatetime,
                             LocalDateTime modifyDatetime, Boolean isBlackMember) {
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
    }
}
