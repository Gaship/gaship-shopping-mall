package shop.gaship.gashipshoppingmall.employee.dto.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * 직원을생성하기위한 정보들이 담겨져있는 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
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
