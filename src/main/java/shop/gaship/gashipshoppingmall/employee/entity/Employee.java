package shop.gaship.gashipshoppingmall.employee.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.gaship.gashipshoppingmall.addressLocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.employee.dto.request.CreateEmployeeRequestDto;
import shop.gaship.gashipshoppingmall.employee.dto.request.ModifyEmployeeRequestDto;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

import javax.persistence.*;

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
    @JoinColumn(name = "employee_authority_no")
    private StatusCode statusCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsibility_address_no")
    private AddressLocal addressLocal;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number", unique = true)
    private String phoneNo;

    public Employee(StatusCode statusCode, AddressLocal addressLocal,
                    String name,
                    String email, String password, String phoneNo) {
        this.statusCode = statusCode;
        this.addressLocal = addressLocal;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNo = phoneNo;
    }

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
     * 직원자체의 정보를 수정하기위한 메서드입니다.
     *
     * @param dto 수정하기위한 기본정보들이 담겨져있습니다
     * @author 유호철
     */
    public void modifyEmployee(ModifyEmployeeRequestDto dto) {
        this.name = dto.getName();
        this.email = dto.getEmail();
        this.phoneNo = dto.getPhoneNo();
    }

    /**
     * 직원에 있는 공통코드의 권한을 변경하기위한 메서드입니다.
     *
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
}
