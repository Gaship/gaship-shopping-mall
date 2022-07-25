package shop.gaship.gashipshoppingmall.employee.dto.response;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

/**
 * 직원정보에대한 반환값이 담겨져있는 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
@Getter
public class EmployeeInfoResponseDto {

    @NotNull
    @Length(min = 1, max = 20)
    private final String name;

    @Email
    @NotNull
    private final String email;

    @NotNull
    private final String phoneNo;


    public EmployeeInfoResponseDto(String name, String email, String phoneNo) {
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
    }
}
