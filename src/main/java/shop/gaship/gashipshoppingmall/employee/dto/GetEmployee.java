package shop.gaship.gashipshoppingmall.employee.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
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
@Setter
public class GetEmployee {
    //TODO : 주소지 추가시 담당지역들어가야한다

    @NotNull
    @Length(min = 1,max = 20)
    private String name;

    @Email
    @NotNull
    private String email;

    @NotNull
    private String phoneNo;

    public GetEmployee(Employee employee) {
        this.name = employee.getName();
        this.email = employee.getEmail();
        this.phoneNo = employee.getPhoneNo();
    }
}
