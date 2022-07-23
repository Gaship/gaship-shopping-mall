package shop.gaship.gashipshoppingmall.member.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * 관리자가 수장 힐 수 있는 멤버의 닉네임, 상태입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Getter
@Builder
public class MemberModifyByAdminDto {
    private Integer memberNo;
    private String nickname;
    private String status;
}
