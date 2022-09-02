package shop.gaship.gashipshoppingmall.employee.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.gaship.gashipshoppingmall.addresslocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.employee.dto.request.CreateEmployeeRequestDto;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;


/**
 * 데이터베이스에 있는 직원에대한 정보를 사용하기위한 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */

@Getter
@NoArgsConstructor
@Table(name = "employees")
@Entity
public class Employee {
    @Id
    @Column(name = "employee_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_authority_no")
    @NotNull
    private StatusCode statusCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsibility_address_no")
    @NotNull
    private AddressLocal addressLocal;

    @Column(name = "name", unique = true)
    @NotNull
    private String name;

    @Column(name = "email", unique = true)
    @NotNull
    private String email;

    @Column(name = "password")
    @NotNull
    private String password;

    @Column(name = "phone_number", unique = true)
    @NotNull
    private String phoneNo;

    private String encodedEmailForSearch;

    /**
     * 직원에대한 지역을 수정하기위한 메서드입니다.
     *
     * @param addressLocal 수정되어야할 지역정보가 담겨져있습니다.
     * @author 유호철
     */
    public void fixLocation(AddressLocal addressLocal) {
        this.addressLocal = addressLocal;
    }


    /**
     * 직원 정보를 수정하기위한 메서드입니다.
     *
     * @param name    암호화된 이름
     * @param phoneNo 암호화된 번호
     */
    public void modifyEmployee(String name,
                               String phoneNo) {
        this.name = name;
        this.phoneNo = phoneNo;
    }

    /**
     * 직원에 있는 공통코드의 권한을 변경하기위한 메서드입니다.
     *
     * @param code 변경되어야할 권한이 들어옵니다.
     * @author 유호철
     */
    public void fixCode(StatusCode code) {
        this.statusCode = code;
    }

    /**
     * 직원생성을 위한 메서드니다.
     *
     * @param dto 직원생성을위한 정보들이 담겨있습니다.
     * @author 유호철
     */
    public void registerEmployee(CreateEmployeeRequestDto dto) {
        this.name = dto.getName();
        this.phoneNo = dto.getPhoneNo();
        this.email = dto.getEmail();
        this.password = dto.getPassword();
    }

    /**
     * 직원을 생성하기위한 builder 입니다.
     *
     * @param statusCode            the status code
     * @param addressLocal          the address local
     * @param name                  the name
     * @param email                 the email
     * @param password              the password
     * @param phoneNo               the phone no
     * @param encodedEmailForSearch the encoded email for search
     */
    @Builder
    public Employee(StatusCode statusCode,
                    AddressLocal addressLocal,
                    String name, String email,
                    String password, String phoneNo,
                    String encodedEmailForSearch) {
        this.statusCode = statusCode;
        this.addressLocal = addressLocal;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNo = phoneNo;
        this.encodedEmailForSearch = encodedEmailForSearch;
    }
}
