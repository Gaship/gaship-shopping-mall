package shop.gaship.gashipshoppingmall.member.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 로그인을 시도하는 사용자의 정보 객체입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Getter
@Builder
@AllArgsConstructor
public class SignInUserDetailsDto {
    private Integer identifyNo;
    private String email;
    private String hashedPassword;
    private List<String> authorities;
}
