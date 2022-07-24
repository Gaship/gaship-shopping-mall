package shop.gaship.gashipshoppingmall.member.dto;

import javax.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * 멤버의 이메일 정보를 찾기 위해서 필요한 닉네임 정보를 포함한 클래스입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Getter
public class FindMemberEmailRequest {
    @NotBlank(message = "이메일을 찾기 위해서는 닉네임이 필요합니다.")
    private String nickname;
}
