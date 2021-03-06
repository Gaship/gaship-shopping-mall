package shop.gaship.gashipshoppingmall.employee.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 직원의 정보를 수정하기위한 정보가 담겨있는 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class ModifyEmployeeRequestDto {
    @Min(1)
    @NotNull(message = "직원번호를 입력해주세요")
    private Integer employeeNo;

    @NotNull(message = "이름을 입력해주세요")
    private String name;

    @Email
    @NotNull(message = "이메일을 입력해주세요")
    private String email;

    @NotNull(message = "휴대폰번호를 입력해주세요")
    private String phoneNo;

}
