package shop.gaship.gashipshoppingmall.employee.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shop.gaship.gashipshoppingmall.addressLocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.addressLocal.repository.AddressLocalRepository;
import shop.gaship.gashipshoppingmall.dayLabor.entity.DayLabor;
import shop.gaship.gashipshoppingmall.dayLabor.repository.DayLaborRepository;
import shop.gaship.gashipshoppingmall.employee.entity.Employee;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;

/**
 * packageName    : shop.gaship.gashipshoppingmall.employee.repository
 * fileName       : EmployeeRepositoryTest
 * author         : 유호철
 * date           : 2022/07/10
 * description    :
 * =========================================================== DATE              AUTHOR
 * NOTE ----------------------------------------------------------- 2022/07/10        유호철       최초
 * 생성
 */
@DataJpaTest
class EmployeeRepositoryTest {

    private Employee employee;

    private AddressLocal addressLocal;

    private StatusCode code;

    private DayLabor labor;

    @Autowired
    EmployeeRepository repository;

    @Autowired
    AddressLocalRepository localRepository;

    @Autowired
    StatusCodeRepository codeRepository;

    @Autowired
    DayLaborRepository laborRepository;

    @BeforeEach
    void setUp() {
        labor = new DayLabor(1, 10);

        addressLocal = new AddressLocal(null,"마산턱별시",1,true,labor,null,null);

        code = new StatusCode("test", 1, "test", "tt");

        employee = new Employee(null, code, addressLocal, "잠온다", "test@naver.com", "password",
            "01011111111");

        labor.setAddressLocal(addressLocal);

        addressLocal.setDayLabor(labor);
    }

    @DisplayName("이메일 중복검증을 위한 테스트")
    @Test
    void findByEmail() {
        //given
        laborRepository.save(labor);
        localRepository.save(addressLocal);
        codeRepository.save(code);
        repository.save(employee);
        //when & then
        Employee employee1 = repository.findByEmail("test@naver.com").get();

        assertThat(employee1.getEmployeeNo()).isEqualTo(employee.getEmployeeNo());
    }

}