package shop.gaship.gashipshoppingmall.employee.dto.response;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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

    @NotBlank
    private final String address;


    /**
     * 암호화된 직원정보를 입력받기위한 메서드입니다.
     *
     * @param name    암호화된 이름
     * @param email   암호화된 이메일
     * @param phoneNo 암호화된 번호
     * @param address 지역주소
     */
    public EmployeeInfoResponseDto(String name,
                                   String email,
                                   String phoneNo,
                                   String address) {
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
        this.address = address;
    }
}
