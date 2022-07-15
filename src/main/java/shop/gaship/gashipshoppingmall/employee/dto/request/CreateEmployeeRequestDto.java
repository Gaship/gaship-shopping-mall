package shop.gaship.gashipshoppingmall.employee.dto.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * packageName    : shop.gaship.gashipshoppingmall.employee.dto
 * fileName       : CreateEmployeeDto
 * author         : 유호철
 * date           : 2022/07/10
 * description    : Employee 생성을 위한 dto
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/10        유호철       최초 생성
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class CreateEmployeeRequestDto {

    @NotNull
    private Integer authorityNo;

    @NotNull
    private Integer addressNo;

    @NotNull
    @Length(min = 1, max = 20)
    private String name;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String phoneNo;

}
