package shop.gaship.gashipshoppingmall.member.dto.request;

import lombok.Builder;
import lombok.Getter;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

/**
 * 멤버 정보 수정을 위한 정보를 담는 dto 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Getter
@Builder
public class MemberModifyRequestDto {
    private Integer memberNo;
    private StatusCode statusCode;
    private MemberGrade memberGrade;
    private String email;
    private String password;
    private String phoneNumber;
    private String name;
    private String nickname;
    private String gender;
}
