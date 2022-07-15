package shop.gaship.gashipshoppingmall.membergrade.dto.response;

import lombok.Data;

/**
 * packageName    : shop.gaship.gashipshoppingmall.membergrade.dto
 * fileName       : MemberGradeDto
 * author         : Semi Kim
 * date           : 2022/07/11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/11        Semi Kim       최초 생성
 */
@Data
public class MemberGradeResponseDto {
    private Integer no;
    private String name;
    private Long accumulateAmount;
    private String renewalPeriodStatusCode;
}