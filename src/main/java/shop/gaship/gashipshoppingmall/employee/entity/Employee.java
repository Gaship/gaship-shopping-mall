package shop.gaship.gashipshoppingmall.employee.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.gaship.gashipshoppingmall.addressLocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.employee.dto.CreateEmployeeDto;
import shop.gaship.gashipshoppingmall.employee.dto.ModifyEmployeeDto;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

/**
 *packageName    : shop.gaship.gashipshoppingmall.employee.entity
 * fileName       : Employee
 * author         : 유호철
 * date           : 2022/07/10
 * description    : 직원 엔티티
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/10        유호철       최초 생성
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

    @ManyToOne
    @JoinColumn(name = "employee_authority_no")
    private StatusCode statusCode;

    @ManyToOne
    @JoinColumn(name = "responsibility_address_no")
    private AddressLocal addressLocal;

    @Column(name = "name",unique = true)
    private String name;

    @Column(name = "email",unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number",unique = true)
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
     * methodName : registerLocal
     * author : 유호철
     * description :
     *
     * @param addressLocal AddressLocal
     */
    public void fixLocation(AddressLocal addressLocal) {
        this.addressLocal = addressLocal;
    }

    /**
     * methodName : modifyEmployee
     * author : 유호철
     * description : Employee 정보수정
     *
     * @param dto ModifyEmployeeDto
     */
    public void modifyEmployee(ModifyEmployeeDto dto){
        this.name = dto.getName();
        this.email = dto.getEmail();
        this.phoneNo = dto.getPhoneNo();
    }

    /**
     * methodName : registerCode
     * author : 유호철
     * description : code 변경시 주입
     *
     * @param code StatusCode
     */
    public void fixCode(StatusCode code) {
        this.statusCode = code;
    }

    /**
     * methodName : registerEmployee
     * author : 유호철
     * description : employee 생성시 주입
     *
     * @param dto CreateEmployeeDto
     */
    public void registerEmployee(CreateEmployeeDto dto){
        this.name = dto.getName();
        this.phoneNo = dto.getPhoneNo();
        this.email = dto.getEmail();
        this.password = dto.getPassword();
    }
}
