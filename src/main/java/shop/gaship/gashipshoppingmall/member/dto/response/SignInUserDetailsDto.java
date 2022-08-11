package shop.gaship.gashipshoppingmall.member.dto.response;

import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
    private Integer memberNo;
    @Setter
    private String email;
    private String hashedPassword;
    private Collection<String> authorities;
}
