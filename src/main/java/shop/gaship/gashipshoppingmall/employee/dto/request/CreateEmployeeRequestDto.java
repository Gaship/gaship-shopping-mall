package shop.gaship.gashipshoppingmall.employee.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

/**
 * 직원을생성하기위한 정보들이 담겨져있는 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class CreateEmployeeRequestDto {
    @Min(value = 1, message = "authority 는 0 이하일 수 없습니다.")
    @NotNull(message = "권한번호를 기입해주세요")
    private Integer authorityNo;
    @Min(value = 1, message = "addressNo 는 0 이하일 수 없습니다.")
    @NotNull(message = "지역번호를 입력해주세요")
    private Integer addressNo;

    @NotNull(message = "이름을 입력해주세요")
    @Length(min = 1, max = 20, message = "name 의 길이는 최소 1 최대 20 입니다.")
    private String name;

    @NotNull(message = "이메일을 입력해주세요")
    @Email(message = "이메일 형식에 맞지 않습니다")
    private String email;

    @NotNull(message = "비밀번호를 입력해주세요")
    private String password;

    @NotNull(message = "휴대전화번호를 입력해주세요")
    private String phoneNo;

}
