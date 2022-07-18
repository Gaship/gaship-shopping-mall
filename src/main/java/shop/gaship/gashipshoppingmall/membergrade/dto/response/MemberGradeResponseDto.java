package shop.gaship.gashipshoppingmall.membergrade.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 * 회원등급 응답 Data Transfer Object.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
@Setter
public class MemberGradeResponseDto {
    private Integer no;
    private String name;
    private Long accumulateAmount;
    private String renewalPeriodStatusCode;
}
