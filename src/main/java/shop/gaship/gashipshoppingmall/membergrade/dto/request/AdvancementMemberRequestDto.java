package shop.gaship.gashipshoppingmall.membergrade.dto.request;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 회원 등급 승급시 회원 정보에 있는
 * 회원 등급 및 다음 승급 일자 정보
 * 갱신 요청 data transfer object.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
@Setter
public class AdvancementMemberRequestDto {
    @NotBlank(message = "memberNo 는 필수값 입니다.")
    private Integer memberNo;
    @NotBlank(message = "memberGradeNo 는 필수값 입니다.")
    private Integer memberGradeNo;
    @NotNull(message = "nextRenewalGradeDate 는 필수값입니다.")
    private LocalDate nextRenewalGradeDate;
}
