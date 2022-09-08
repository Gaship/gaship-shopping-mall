package shop.gaship.gashipshoppingmall.member.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 재발급된 비밀번호를 이메일에 전달받기위한 클래스입니다. 
 *
 * @author 김민수
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReissuePasswordReceiveEmailDto {
    @Email(message = "이메일 형식에 맞지 않습니다.")
    @NotBlank(message = "memberNo 는 필수 입력값입니다.")
    private String email;
}
