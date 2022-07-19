package shop.gaship.gashipshoppingmall.employee.dto.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 직원을생성하기위한 정보들이 담겨져있는 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class CreateEmployeeRequestDto {
    @Min(1)
    @NotNull(message = "권한번호를 기입해주세요")
    private Integer authorityNo;
    @Min(1)
    @NotNull(message = "지역번호를 입력해주세요")
    private Integer addressNo;

    @NotNull(message = "이름을 입력해주세요")
    @Length(min = 1, max = 20)
    private String name;

    @NotNull(message = "이메일을 입력해주세요")
    @Email
    private String email;

    @NotNull(message = "비밀번호를 입력해주세요")
    private String password;

    @NotNull(message = "휴대전화번호를 입력해주세요")
    private String phoneNo;

}
