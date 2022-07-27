package shop.gaship.gashipshoppingmall.member.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

/**
 * 비밀번호 재발급을 위해 사용자가 제공하는 필수 정보입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class ReissuePasswordRequest {
    @NotBlank
    @Length(max = 255)
    @Email
    private String email;

    @NotBlank
    @Length(max = 100)
    @Pattern(regexp = "^[가-힣]+", message = "이름을 정확히 입력하여주십시오.")
    private String name;
}
