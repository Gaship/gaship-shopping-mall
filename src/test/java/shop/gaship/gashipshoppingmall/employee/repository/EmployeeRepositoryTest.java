package shop.gaship.gashipshoppingmall.employee.repository;

import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import shop.gaship.gashipshoppingmall.addresslocal.dummy.AddressLocalDummy;
import shop.gaship.gashipshoppingmall.addresslocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.addresslocal.repository.AddressLocalRepository;
import shop.gaship.gashipshoppingmall.dataprotection.util.Aes;
import shop.gaship.gashipshoppingmall.dataprotection.util.Sha512;
import shop.gaship.gashipshoppingmall.daylabor.dummy.DayLaboyDummy;
import shop.gaship.gashipshoppingmall.daylabor.entity.DayLabor;
import shop.gaship.gashipshoppingmall.daylabor.repository.DayLaborRepository;
import shop.gaship.gashipshoppingmall.employee.dummy.EmployeeDummy;
import shop.gaship.gashipshoppingmall.employee.entity.Employee;
import shop.gaship.gashipshoppingmall.member.dto.response.SignInUserDetailsDto;
import shop.gaship.gashipshoppingmall.statuscode.dummy.StatusCodeDummy;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

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
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class EmployeeRepositoryTest {
    @Autowired
    EmployeeRepository repository;
    @Autowired
    AddressLocalRepository localRepository;
    @Autowired
    StatusCodeRepository codeRepository;
    @Autowired
    DayLaborRepository laborRepository;
    @MockBean
    Sha512 sha512;
    @MockBean
    Aes aes;
    private Employee employee;
    private AddressLocal addressLocal;
    private StatusCode code;
    private DayLabor labor;

    @BeforeEach
    void setUp() {
        employee = EmployeeDummy.dummy();

        labor = DayLaboyDummy.dummy1();

        addressLocal = AddressLocalDummy.dummy1();

        code = StatusCodeDummy.dummy();

        labor = new DayLabor(1, 10);

        addressLocal.registerDayLabor(labor);

        labor.fixLocation(addressLocal);
    }

    @DisplayName("이메일 중복검증을 위한 테스트")
    @Test
    @org.junit.jupiter.api.Order(4)
    void findByEmail() {
        //given
        employee.fixCode(code);
        employee.fixLocation(addressLocal);
        laborRepository.save(labor);
        localRepository.save(addressLocal);
        codeRepository.save(code);
        repository.save(employee);
        //when & then
        Employee employee1 = repository.findByEmail("test@naver.com").orElse(null);

        assertThat(Objects.requireNonNull(employee1).getEmployeeNo()).isEqualTo(employee.getEmployeeNo());
        assertThat(employee1.getAddressLocal()).isEqualTo(addressLocal);
        assertThat(employee1.getStatusCode()).isEqualTo(code);
    }

    @Test
    @DisplayName("직원의 이메일을 통해서 직원 계정 정보를 가져옵니다. : 성공")
    @org.junit.jupiter.api.Order(2)
    void findSignInEmployeeUserDetailCaseSuccess() {
        employee.fixCode(code);
        employee.fixLocation(addressLocal);
        laborRepository.save(labor);
        localRepository.save(addressLocal);
        codeRepository.save(code);
        repository.save(employee);

        given(sha512.encryptPlainText(anyString()))
            .willReturn(employee.getEmail());
        given(aes.aesEcbDecode(anyString()))
            .willReturn(employee.getEmail());

        SignInUserDetailsDto loginEmployee =
            repository.findSignInEmployeeUserDetail((employee.getEmail())).orElse(null);

        assert loginEmployee != null;
        assertThat(Objects.requireNonNull(loginEmployee.getMemberNo()))
            .isEqualTo(employee.getEmployeeNo());
        assertThat(loginEmployee.getEmail()).isEqualTo("test@naver.com");
        assertThat(loginEmployee.getAuthorities()).isEqualTo(List.of(code.getStatusCodeName()));
        assertThat(loginEmployee.getHashedPassword()).isEqualTo(employee.getPassword());
    }

    @Test
    @DisplayName("직원의 이메일을 통해서 직원 계정 정보를 가져옵니다. : 실패")
    @org.junit.jupiter.api.Order(3)
    void findSignInEmployeeUserDetailCaseFailure() {
        SignInUserDetailsDto loginEmployee =
            repository.findSignInEmployeeUserDetail("test@naver.com").orElse(null);

        assertThat(loginEmployee).isNull();
    }
}
