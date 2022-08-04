package shop.gaship.gashipshoppingmall.member.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
    private Integer memberNo;
    private String memberStatus;
    private String email;
    private List<String> authorities;
    private String password;
    private String nickname;
    private String name;
    private String gender;
    private String phoneNumber;
    private LocalDate birthDate;
    private Long accumulatePurchaseAmount;
    private LocalDate nextRenewalGradeDate;
    private LocalDateTime registerDatetime;
    private LocalDateTime modifyDatetime;
    private Boolean social;
}
