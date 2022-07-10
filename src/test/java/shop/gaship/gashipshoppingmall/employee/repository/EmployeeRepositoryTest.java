package shop.gaship.gashipshoppingmall.employee.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shop.gaship.gashipshoppingmall.employee.entity.Employee;

/**
 * packageName    : shop.gaship.gashipshoppingmall.employee.repository fileName       :
 * EmployeeRepositoryTest author         : 유호철 date           : 2022/07/10 description    :
 * =========================================================== DATE              AUTHOR
 * NOTE ----------------------------------------------------------- 2022/07/10        유호철       최초
 * 생성
 */
@DataJpaTest
class EmployeeRepositoryTest {

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee(null, 1, 1, "잠온다", "test@naver.com", "password", "01011111111");
    }

    @Autowired
    private EmployeeRepository repository;

    @DisplayName("이메일 중복검증을 위한 테스트")
    @Test
    void findByEmail() {
        //given
        repository.save(employee);
        //when & then
        Employee employee1 = repository.findByEmail("test@naver.com").get();

        assertThat(employee1.getEmployeeNo()).isEqualTo(employee.getEmployeeNo());
    }

}