package shop.gaship.gashipshoppingmall.employee.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
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

    public Employee(CreateEmployeeDto dto) {
        this.name = dto.getName();
        this.email = dto.getEmail();
        this.password = dto.getPassword();
        this.phoneNo = dto.getPhoneNo();
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
}
