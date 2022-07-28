package shop.gaship.gashipshoppingmall.member.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
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
    @Email
    @NotNull
    private String email;
}
