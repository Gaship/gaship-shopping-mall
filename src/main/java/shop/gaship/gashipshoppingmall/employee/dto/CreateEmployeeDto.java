package shop.gaship.gashipshoppingmall.employee.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 *packageName    : shop.gaship.gashipshoppingmall.employee.dto
 * fileName       : CreateEmployeeDto
 * author         : 유호철
 * date           : 2022/07/10
 * description    : Employee 생성을 위한 dto
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/10        유호철       최초 생성
 */
@Data
@AllArgsConstructor
public class CreateEmployeeDto {
    // TODO 2 : 공통코드 추가시 수정

    @NotNull
    private Integer authorityNo;

    @NotNull
    private Integer addressNo;

    @NotNull
    @Length(min = 1,max = 20)
    private String name;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String phoneNo;
}
