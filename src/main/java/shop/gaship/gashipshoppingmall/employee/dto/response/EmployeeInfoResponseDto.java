package shop.gaship.gashipshoppingmall.employee.dto.response;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import shop.gaship.gashipshoppingmall.employee.entity.Employee;

/**
 *packageName    : shop.gaship.gashipshoppingmall.employee.dto
 * fileName       : GetEmployee
 * author         : 유호철
 * date           : 2022/07/10
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/10        유호철       최초 생성
 */
@Getter
public class EmployeeInfoResponseDto {

    @NotNull
    @Length(min = 1,max = 20)
    private String name;

    @Email
    @NotNull
    private String email;

    @NotNull
    private String phoneNo;

    public EmployeeInfoResponseDto(Employee employee) {
        this.name = employee.getName();
        this.email = employee.getEmail();
        this.phoneNo = employee.getPhoneNo();
    }

    public EmployeeInfoResponseDto(String name, String email, String phoneNo) {
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
    }
}
