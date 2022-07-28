package shop.gaship.gashipshoppingmall.employee.dto.response;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import shop.gaship.gashipshoppingmall.employee.entity.Employee;

/**
 * 직원정보에대한 반환값이 담겨져있는 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class EmployeeInfoResponseDto {

    @NotNull
    @Length(min = 1, max = 20)
    private final String name;

    @Email
    @NotNull
    private final String email;

    @NotNull
    private final String phoneNo;

    @NotBlank
    private final String address;

    /**
     * 직원을 반환값인 dto 로 변경합니다.
     *
     * @param employee the employee
     */
    public EmployeeInfoResponseDto(Employee employee) {
        this.name = employee.getName();
        this.email = employee.getEmail();
        this.phoneNo = employee.getPhoneNo();
        this.address = employee.getAddressLocal().getAddressName();
    }
}
