package shop.gaship.gashipshoppingmall.member.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

/**
 * 멤버정보 반환값을 담는 dto 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Getter
@Builder
public class MemberResponseDto {
    private Integer memberNo;
    private String recommendMemberName;
    private String memberStatus;
    private String memberGrade;
    private String email;
    private List<String> authorities;
    private String phoneNumber;
    private String nickname;
    private String name;
    private String gender;
    private LocalDate birthDate;
    private Long accumulatePurchaseAmount;
    private LocalDate nextRenewalGradeDate;
    private LocalDateTime registerDatetime;
    private Boolean social;
}
