package shop.gaship.gashipshoppingmall.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 비밀번호 재발급을 위해 사용자가 제공하는 필수 정보입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class ReissuePasswordRequest {
    private String email;
    private String name;
}
