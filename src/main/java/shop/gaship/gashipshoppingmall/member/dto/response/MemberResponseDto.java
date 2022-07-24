package shop.gaship.gashipshoppingmall.member.dto.response;

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
@Builder
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
}
