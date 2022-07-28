package shop.gaship.gashipshoppingmall.employee.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipshoppingmall.addressLocal.dummy.AddressLocalDummy;
import shop.gaship.gashipshoppingmall.addressLocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.addressLocal.repository.AddressLocalRepository;
import shop.gaship.gashipshoppingmall.dayLabor.dummy.DayLaboyDummy;
import shop.gaship.gashipshoppingmall.dayLabor.entity.DayLabor;
import shop.gaship.gashipshoppingmall.employee.dto.request.CreateEmployeeRequestDto;
import shop.gaship.gashipshoppingmall.employee.dto.request.ModifyEmployeeRequestDto;
import shop.gaship.gashipshoppingmall.employee.dto.response.EmployeeInfoResponseDto;
import shop.gaship.gashipshoppingmall.employee.dummy.CreateEmployeeDtoDummy;
import shop.gaship.gashipshoppingmall.employee.dummy.ModifyEmployeeDtoDummy;
import shop.gaship.gashipshoppingmall.employee.entity.Employee;
import shop.gaship.gashipshoppingmall.employee.exception.EmployeeNotFoundException;
import shop.gaship.gashipshoppingmall.employee.exception.WrongAddressException;
import shop.gaship.gashipshoppingmall.employee.exception.WrongStatusCodeException;
import shop.gaship.gashipshoppingmall.employee.repository.EmployeeRepository;
import shop.gaship.gashipshoppingmall.employee.service.impl.EmployeeServiceImpl;
import shop.gaship.gashipshoppingmall.member.dto.response.SignInUserDetailsDto;
import shop.gaship.gashipshoppingmall.statuscode.dummy.StatusCodeDummy;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * packageName    : shop.gaship.gashipshoppingmall.employee.service
 * fileName:EmployeeServiceTest
 * author         : 유호철
 * date           : 2022/07/10
 * description    : EmployeeService 테스트를 위한 클래스
 * ===========================================================
 * DATE              AUTHOR     NOTE
 * -----------------------------------------------------------
 * 2022/07/10        유호철       최초생성
 */

@ExtendWith(SpringExtension.class)
@Import(EmployeeServiceImpl.class)
class EmployeeServiceTest {

    @MockBean
    EmployeeRepository repository;

    @MockBean
    StatusCodeRepository codeRepository;

    @MockBean
    AddressLocalRepository localRepository;

    @Autowired
    EmployeeService service;

    CreateEmployeeRequestDto dto;

    Employee employee;

    ModifyEmployeeRequestDto modifyEmployeeDto;

    EmployeeInfoResponseDto getEmployee;
    ArgumentCaptor<Employee> captor;

    DayLabor labor;

    AddressLocal addressLocal;

    StatusCode code;

    @BeforeEach
    void setUp() {
        dto = CreateEmployeeDtoDummy.dummy();

        employee = new Employee();

        employee.registerEmployee(dto);

        modifyEmployeeDto = ModifyEmployeeDtoDummy.dummy();

        captor = ArgumentCaptor.forClass(Employee.class);

        getEmployee = new EmployeeInfoResponseDto(employee.getName(), employee.getEmail(), employee.getPhoneNo());
        labor = DayLaboyDummy.dummy1();

        addressLocal = AddressLocalDummy.dummy1();

        code = StatusCodeDummy.dummy();

        labor.fixLocation(addressLocal);

        addressLocal.registerDayLabor(labor);

    }

    @DisplayName("공통 코트 잘못입력 해서 생성했을경우")
    @Test
    void WrongStatusCodeException_createEmployeeTest() {
        //when
        given(codeRepository.findById(dto.getAuthorityNo()))
                .willReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> service.addEmployee(dto)).isInstanceOf(
                WrongStatusCodeException.class);
    }

    @DisplayName("지역이 없는 지역이여서 생기는 에러")
    @Test
    void WrongAddressException_createEmployeeTest() {
        //when
        given(codeRepository.findById(dto.getAuthorityNo()))
                .willReturn(Optional.of(code));
        given(localRepository.findById(dto.getAddressNo()))
                .willReturn(Optional.empty());
        //then
        assertThatThrownBy(() -> service.addEmployee(dto)).isInstanceOf(
                WrongAddressException.class);
    }

    @DisplayName("직원 생성 테스트")
    @Test
    void createEmployeeTest() {
        //given
        employee.fixCode(code);
        employee.fixLocation(addressLocal);

        given(repository.save(any()))
                .willReturn(employee);

        given(codeRepository.findById(any()))
                .willReturn(Optional.of(code));

        given(localRepository.findById(any()))
                .willReturn(Optional.of(addressLocal));

        //when
        service.addEmployee(dto);

        //then
        verify(repository, timeout(1))
                .save(captor.capture());

        Employee test = captor.getValue();
        assertThat(dto.getEmail()).isEqualTo(test.getEmail());
        assertThat(dto.getName()).isEqualTo(test.getName());
        assertThat(dto.getPassword()).isEqualTo(test.getPassword());
        assertThat(dto.getAuthorityNo()).isEqualTo(1);
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
        service.modifyEmployee(modifyEmployeeDto);

        //then
        verify(repository, times(1))
                .findById(any());

    }

    @DisplayName("직원 정보 수정 테스트 실패")
    @Test
    void failModifyEmployeeAndFailGetEmployeeTest() {
        //given
        given(repository.findById(any()))
                .willReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() -> service.modifyEmployee(modifyEmployeeDto))
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

        given(codeRepository.findById(any()))
                .willReturn(Optional.of(code));

        given(localRepository.findById(any()))
                .willReturn(Optional.of(addressLocal));
        //when
        service.addEmployee(dto);
        EmployeeInfoResponseDto test = service.findEmployee(employee.getEmployeeNo());

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
        Employee employee1 = new Employee(code, addressLocal, "t", "t@naver.com", "aaaa",
                "01010101");
        List<Employee> list = new ArrayList<>();

        list.add(employee);
        list.add(employee1);

        given(repository.findAll())
                .willReturn(list);

        //when
        List<EmployeeInfoResponseDto> allEmployees = service.findEmployees();

        //then
        verify(repository, times(1))
                .findAll();

        assertThat(allEmployees.get(0).getEmail()).isEqualTo(getEmployee.getEmail());
        assertThat(allEmployees.get(1).getName()).isEqualTo(employee1.getName());

    }

    @Test
    @DisplayName("로그인하는 직원의 계정 정보를 얻어온다. : 성공")
    void findSignInEmployeeFromEmailCaseSuccess() {
        SignInUserDetailsDto dto =
            new SignInUserDetailsDto(employee.getEmployeeNo(),
                employee.getEmail(),
                employee.getPassword(),
                false,
                List.of("ROLE_ADMIN")
                );

        given(repository.findSignInEmployeeUserDetail(anyString()))
            .willReturn(Optional.of(dto));

        SignInUserDetailsDto result =
            service.findSignInEmployeeFromEmail("exam@nhn.com");


        assertThat(result).isNotNull().isEqualTo(dto);
    }

    @Test
    @DisplayName("로그인하는 직원의 계정 정보를 얻어온다. : 실패")
    void findSignInEmployeeFromEmailCaseFailure() {
        given(repository.findSignInEmployeeUserDetail(anyString()))
            .willThrow(new EmployeeNotFoundException());


        assertThatThrownBy(() -> repository.findSignInEmployeeUserDetail("exam@nhn.com"))
            .isInstanceOf(EmployeeNotFoundException.class)
            .hasMessage("직원이 존재하지 않습니다.");


    }
}
