package shop.gaship.gashipshoppingmall.membergrade.dto;

import lombok.Data;

/**
 * packageName    : shop.gaship.gashipshoppingmall.membergrade.request
 * fileName       : MemberGradeAddRequest
 * author         : Semi Kim
 * date           : 2022/07/09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/09        Semi Kim       최초 생성
 */
@Data
public class MemberGradeRequestDto {
    private String name;
    private Long accumulateAmount;
}
