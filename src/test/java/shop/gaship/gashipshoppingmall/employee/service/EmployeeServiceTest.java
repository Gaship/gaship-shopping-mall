package shop.gaship.gashipshoppingmall.employee.service;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static reactor.core.publisher.Mono.when;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.h2.api.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import shop.gaship.gashipshoppingmall.employee.dto.CreateEmployeeDto;
import shop.gaship.gashipshoppingmall.employee.dto.GetEmployee;
import shop.gaship.gashipshoppingmall.employee.dto.ModifyEmployeeDto;
import shop.gaship.gashipshoppingmall.employee.entity.Employee;
import shop.gaship.gashipshoppingmall.employee.exception.EmployeeNotFoundException;
import shop.gaship.gashipshoppingmall.employee.repository.EmployeeRepository;
import shop.gaship.gashipshoppingmall.employee.service.impl.EmployeeServiceImpl;

/**
 * packageName    : shop.gaship.gashipshoppingmall.employee.service fileName       :
 * EmployeeServiceTest author         : 유호철 date           : 2022/07/10 description    :
 * =========================================================== DATE              AUTHOR
 * NOTE ----------------------------------------------------------- 2022/07/10        유호철       최초
 * 생성
 */

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository repository;

    @InjectMocks
    private EmployeeServiceImpl service;

    CreateEmployeeDto dto;

    Employee employee;

    ModifyEmployeeDto modifyEmployeeDto;

    GetEmployee getEmployee;
    ArgumentCaptor<Employee> captor;
    @BeforeEach
    void setUp() {
        dto = new CreateEmployeeDto(1, 1, "test", "test@naver.com", "asdfasdf",
            "10000");

        employee = new Employee(dto);

        modifyEmployeeDto = new ModifyEmployeeDto("test", "test@naver.com", "10000");

        captor = ArgumentCaptor.forClass(Employee.class);

        getEmployee = new GetEmployee(employee);
    }

    @DisplayName("직원 생성 테스트")
    @Test
    void createEmployeeTest() {
        //given
        given(repository.save(any()))
            .willReturn(employee);

        //when
        service.createEmployee(dto);

        //then
        verify(repository,timeout(1))
            .save(captor.capture());

        Employee test = captor.getValue();
        assertThat(dto.getEmail()).isEqualTo(test.getEmail());
        assertThat(dto.getName()).isEqualTo(test.getName());
        assertThat(dto.getPassword()).isEqualTo(test.getPassword());
    }

    @DisplayName("직원 정보 수정 테스트 성공")
    @Test
    void successModifyEmployeeTest() {
        //given
        given(repository.save(any()))
            .willReturn(employee);
        given(repository.findById(any()))
            .willReturn(Optional.of(employee));
        //when
        service.modifyEmployee(employee.getEmployeeNo(), modifyEmployeeDto);

        //then
        verify(repository, times(1))
            .save(captor.capture());
        Employee employee1 = captor.getValue();

        assertThat(employee1.getEmail()).isEqualTo(modifyEmployeeDto.getEmail());
        assertThat(employee1.getPhoneNo()).isEqualTo(modifyEmployeeDto.getPhoneNo());
        assertThat(employee1.getName()).isEqualTo(modifyEmployeeDto.getName());
    }

    @DisplayName("직원 정보 수정 테스트 실패")
    @Test
    void failModifyEmployeeAndFailGetEmployeeTest() {
        //given
        given(repository.findById(any()))
            .willReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() -> service.modifyEmployee(any(), modifyEmployeeDto))
            .isInstanceOf(EmployeeNotFoundException.class);
    }

    @DisplayName("직원 가져오기 단건")
    @Test
    void successGetEmployeeTest() {
        //given
        given(repository.save(any()))
            .willReturn(employee);
        given(repository.findById(any()))
            .willReturn(Optional.of(employee));

        //when
        service.createEmployee(dto);
        GetEmployee test = service.getEmployee(employee.getEmployeeNo());

        //then
        verify(repository, times(1))
            .save(captor.capture());
        verify(repository, times(1))
            .findById(any());

        Employee value = captor.getValue();
        assertThat(test.getEmail()).isEqualTo(value.getEmail());
        assertThat(test.getName()).isEqualTo(value.getName());
        assertThat(test.getPhoneNo()).isEqualTo(value.getPhoneNo());

    }

    @Test
    void getAllEmployees() {
        //given
        Employee employee1 = new Employee(null, 1, 1, "t", "t@naver.com", "aaaa", "01010101");
        List<Employee> list = new ArrayList<>();

        list.add(employee);
        list.add(employee1);

        given(repository.findAll())
            .willReturn(list);

        //when
        List<GetEmployee> allEmployees = service.getAllEmployees();

        //then
        verify(repository, times(1))
            .findAll();

        assertThat(allEmployees.get(0).getEmail()).isEqualTo(getEmployee.getEmail());
        assertThat(allEmployees.get(1).getName()).isEqualTo(employee1.getName());

    }
}