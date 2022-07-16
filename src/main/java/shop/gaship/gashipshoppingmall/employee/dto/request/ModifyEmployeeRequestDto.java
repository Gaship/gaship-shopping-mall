package shop.gaship.gashipshoppingmall.employee.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
    @NotNull
    private Integer employeeNo;

    @NotNull
    private String name;

    @Email
    @NotNull
    private String email;

    @NotNull
    private String phoneNo;

}
