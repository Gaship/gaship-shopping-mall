package shop.gaship.gashipshoppingmall.employee.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.gaship.gashipshoppingmall.employee.dto.CreateEmployeeDto;
import shop.gaship.gashipshoppingmall.employee.dto.ModifyEmployeeDto;

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
    //Todo 1: 공통코드 들어오면 아래 권한 번호, 주소번호 수정

    @Id
    @Column(name = "employee_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeNo;

    @Column(name = "employee_authority_no")
    private Integer authorityNo;

    @Column(name = "responsibility_address_no")
    private Integer addressNo;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private String phoneNo;

    public Employee(CreateEmployeeDto dto) {
        this.authorityNo = dto.getAuthorityNo();
        this.addressNo = dto.getAddressNo();
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
