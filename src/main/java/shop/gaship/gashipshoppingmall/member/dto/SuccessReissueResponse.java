package shop.gaship.gashipshoppingmall.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 비밀번호 재발급 요청에 대한 응답객체입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SuccessReissueResponse {
    private String email;
    private String reissuedPassword;
}
