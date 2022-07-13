package shop.gaship.gashipshoppingmall.employee.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *packageName    : shop.gaship.gashipshoppingmall.employee.dto
 * fileName       : ModifyEmployeeDto
 * author         : 유호철
 * date           : 2022/07/10
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/10        유호철       최초 생성
 */
@Getter
@AllArgsConstructor
public class ModifyEmployeeRequestDto {

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
