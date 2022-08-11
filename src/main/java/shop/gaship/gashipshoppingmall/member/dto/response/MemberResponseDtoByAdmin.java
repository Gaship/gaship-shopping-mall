package shop.gaship.gashipshoppingmall.member.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 관리자가 조회하려는 멤버의 정보를 담은 dto.
 *
 * @author 최정우
 * @since 1.0
 */
@Getter
@Builder
@AllArgsConstructor
public class MemberResponseDtoByAdmin {
    private Integer memberNo;
    private String recommendMemberName;
    private String memberStatus;
    private String memberGrade;
    private String email;
    private String phoneNumber;
    private String nickname;
    private String gender;
    private LocalDate birthDate;
    private Long accumulatePurchaseAmount;
    private LocalDate nextRenewalGradeDate;
    private LocalDateTime registerDatetime;
    private LocalDateTime modifyDatetime;
    private Boolean social;
}
