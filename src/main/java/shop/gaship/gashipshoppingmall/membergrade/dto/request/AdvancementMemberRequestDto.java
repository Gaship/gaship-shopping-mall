package shop.gaship.gashipshoppingmall.membergrade.dto.request;

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
    private Integer memberNo;
    private Integer memberGradeNo;
    private String nextRenewalGradeDate;
}
