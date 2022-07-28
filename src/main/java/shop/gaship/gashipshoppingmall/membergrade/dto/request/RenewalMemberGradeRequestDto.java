package shop.gaship.gashipshoppingmall.membergrade.dto.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import shop.gaship.gashipshoppingmall.gradehistory.dto.request.GradeHistoryAddRequestDto;

/**
 * 회원의 등급 갱신 요청 data request dto.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
@Setter
public class RenewalMemberGradeRequestDto {
    @NotNull(message = "등급이력추가 요청 dto 는 필수 값입니다.")
    private GradeHistoryAddRequestDto gradeHistoryAddRequestDto;
    @NotNull(message = "회원의 승급 요청 dto 는 필수 값입니다.")
    private AdvancementMemberRequestDto advancementMemberRequestDto;
}
