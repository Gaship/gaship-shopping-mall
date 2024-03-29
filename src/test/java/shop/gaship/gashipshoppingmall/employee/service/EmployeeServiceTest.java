package shop.gaship.gashipshoppingmall.employee.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.addresslocal.dummy.AddressLocalDummy;
import shop.gaship.gashipshoppingmall.addresslocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.addresslocal.repository.AddressLocalRepository;
import shop.gaship.gashipshoppingmall.dataprotection.util.Aes;
import shop.gaship.gashipshoppingmall.dataprotection.util.Sha512;
import shop.gaship.gashipshoppingmall.daylabor.dummy.DayLaboyDummy;
import shop.gaship.gashipshoppingmall.daylabor.entity.DayLabor;
import shop.gaship.gashipshoppingmall.employee.dto.request.CreateEmployeeRequestDto;
import shop.gaship.gashipshoppingmall.employee.dto.request.ModifyEmployeeRequestDto;
import shop.gaship.gashipshoppingmall.employee.dto.response.EmployeeInfoResponseDto;
import shop.gaship.gashipshoppingmall.employee.dto.response.InstallOrderResponseDto;
import shop.gaship.gashipshoppingmall.employee.dummy.CreateEmployeeDtoDummy;
import shop.gaship.gashipshoppingmall.employee.dummy.EmployeeDummy;
import shop.gaship.gashipshoppingmall.employee.dummy.GetEmployeeDummy;
import shop.gaship.gashipshoppingmall.employee.dummy.ModifyEmployeeDtoDummy;
import shop.gaship.gashipshoppingmall.employee.entity.Employee;
import shop.gaship.gashipshoppingmall.employee.exception.EmployeeNotFoundException;
import shop.gaship.gashipshoppingmall.employee.exception.WrongAddressException;
import shop.gaship.gashipshoppingmall.employee.exception.WrongStatusCodeException;
import shop.gaship.gashipshoppingmall.employee.repository.EmployeeRepository;
import shop.gaship.gashipshoppingmall.employee.service.impl.EmployeeServiceImpl;
import shop.gaship.gashipshoppingmall.member.dto.response.SignInUserDetailsDto;
import shop.gaship.gashipshoppingmall.order.dummy.OrderDummy;
import shop.gaship.gashipshoppingmall.order.entity.Order;
import shop.gaship.gashipshoppingmall.order.repository.OrderRepository;
import shop.gaship.gashipshoppingmall.orderproduct.dummy.OrderProductDummy;
import shop.gaship.gashipshoppingmall.orderproduct.entity.OrderProduct;
import shop.gaship.gashipshoppingmall.statuscode.dummy.StatusCodeDummy;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;
import shop.gaship.gashipshoppingmall.statuscode.status.DeliveryType;
import shop.gaship.gashipshoppingmall.statuscode.status.OrderStatus;
import shop.gaship.gashipshoppingmall.util.PageResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

    @MockBean
    OrderRepository orderRepository;

    @MockBean
    Aes aes;

    @MockBean
    Sha512 sha512;

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

        getEmployee = new EmployeeInfoResponseDto(employee.getName(), employee.getEmail(),
            employee.getPhoneNo(), "마산", 1);
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
        given(aes.aesEcbDecode(anyString()))
            .willReturn(employee.getEmail());
        given(sha512.encryptPlainText(anyString()))
            .willReturn(employee.getEmail());
        //when
        service.addEmployee(dto);

        //then
        verify(repository, timeout(1))
            .save(captor.capture());

        Employee test = captor.getValue();
        assertThat(dto.getEmail()).isEqualTo(test.getEncodedEmailForSearch());
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
        given(localRepository.findById(anyInt()))
            .willReturn(Optional.of(addressLocal));
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
        given(repository.existsByEncodedEmailForSearch(anyString()))
            .willReturn(true);
        given(codeRepository.findById(any()))
            .willReturn(Optional.of(code));
        given(localRepository.findById(any()))
            .willReturn(Optional.of(addressLocal));
        given(aes.aesEcbDecode(employee.getEmail()))
            .willReturn(employee.getEmail());
        given(aes.aesEcbDecode(employee.getName()))
            .willReturn(employee.getName());
        given(aes.aesEcbDecode(employee.getPhoneNo()))
            .willReturn(employee.getPhoneNo());
        employee.fixLocation(addressLocal);

        //when
        service.addEmployee(dto);

        EmployeeInfoResponseDto test = service.findEmployee(employee.getEmployeeNo());

        //then
        verify(repository, times(1))
            .save(captor.capture());
        verify(repository, times(1))
            .findById(any());

        Employee value = captor.getValue();

        assertThat(employee.getEmployeeNo()).isEqualTo(test.getEmployeeNo());
        assertThat(employee.getAddressLocal().getAddressName()).isEqualTo(value.getAddressLocal().getAddressName());
        assertThat(employee.getEmail()).isEqualTo(test.getEmail());
        assertThat(employee.getName()).isEqualTo(test.getName());
        assertThat(employee.getPhoneNo()).isEqualTo(test.getPhoneNo());
    }

    @Test
    void getAllEmployees() {
        //given
        EmployeeInfoResponseDto e1 = GetEmployeeDummy.dummy();
        EmployeeInfoResponseDto e2 = GetEmployeeDummy.dummy2();
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<EmployeeInfoResponseDto> list = new ArrayList<>();
        list.add(e1);
        list.add(e2);
        given(aes.aesEcbDecode(e1.getEmail()))
            .willReturn(e1.getEmail());
        given(aes.aesEcbDecode(e1.getName()))
            .willReturn(e1.getName());
        given(aes.aesEcbDecode(e1.getPhoneNo()))
            .willReturn(e1.getPhoneNo());
        given(aes.aesEcbDecode(e2.getEmail()))
            .willReturn(e2.getEmail());
        given(aes.aesEcbDecode(e2.getName()))
            .willReturn(e2.getName());
        given(aes.aesEcbDecode(e2.getPhoneNo()))
            .willReturn(e2.getPhoneNo());
        PageImpl<EmployeeInfoResponseDto> page = new PageImpl<>(list, pageRequest, pageRequest.getPageSize());
        given(repository.findAllEmployees(pageRequest))
            .willReturn(page);

        //when
        PageResponse<EmployeeInfoResponseDto> allEmployees = service.findEmployees(pageRequest);

        //then
        verify(repository, times(1))
            .findAllEmployees(pageRequest);

        assertThat(allEmployees.getContent().get(0).getAddress()).isEqualTo(e1.getAddress());
        assertThat(allEmployees.getContent().get(0).getEmail()).isEqualTo(e1.getEmail());
        assertThat(allEmployees.getContent().get(0).getName()).isEqualTo(e1.getName());
        assertThat(allEmployees.getContent().get(0).getPhoneNo()).isEqualTo(e1.getPhoneNo());
        assertThat(allEmployees.getContent().get(1).getAddress()).isEqualTo(e2.getAddress());
        assertThat(allEmployees.getContent().get(1).getEmail()).isEqualTo(e2.getEmail());
        assertThat(allEmployees.getContent().get(1).getName()).isEqualTo(e2.getName());
        assertThat(allEmployees.getContent().get(1).getPhoneNo()).isEqualTo(e2.getPhoneNo());
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
        given(sha512.encryptPlainText(anyString()))
            .willReturn("a");
        given(aes.aesEcbDecode(anyString()))
            .willReturn("b");
        given(repository.findSignInEmployeeUserDetail(anyString()))
            .willReturn(Optional.of(dto));

        SignInUserDetailsDto result =
            service.findSignInEmployeeFromEmail("exam@nhn.com");


        assertThat(result).isNotNull().isEqualTo(dto);
    }

    @Test
    @DisplayName("로그인하는 직원의 계정 정보를 얻어온다. : 실패")
    void findSignInEmployeeFromEmailCaseFailure() {
        given(repository.findSignInEmployeeUserDetail(anyString())).willThrow(
            new EmployeeNotFoundException());


        assertThatThrownBy(() -> repository.findSignInEmployeeUserDetail("exam@nhn.com"))
            .isInstanceOf(EmployeeNotFoundException.class)
            .hasMessage("직원이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("직원들의 위치를 기반으로 설치형 주문 검색")
    void findOrderBasedOnEmployeeLocationTest() {
        List<Order> orders = new ArrayList<>();

        Order order = OrderDummy.createOrderDummy();

        orders.add(order);
        orders.add(order);
        orders.add(order);
        orders.add(order);

        ReflectionTestUtils.setField(
            order.getAddressList().getAddressLocal(),
            "upperLocal",
            AddressLocalDummy.dummy1());

        Page<Order> response = new PageImpl<>(orders, PageRequest.of(0, 10), 1);

        given(repository.findOrderBasedOnEmployeeLocation(any(Pageable.class), anyInt()))
            .willReturn(response);

        Page<InstallOrderResponseDto> result =
            service.findInstallOrdersFromEmployeeLocation(PageRequest.of(0, 10), 1);

        assertThat(result.getContent()).hasSize(4);
        assertThat(result.getContent().get(0).getAddress()).isEqualTo("부산광역시 부산광역시 경기도 안양시 비산동");
    }

    @Test
    @DisplayName("직원의 시공 설치 제품들 배송 수락시 수행")
    void acceptInstallOrder() {
        Order orderDummy = OrderDummy.createOrderDummy();
        OrderProduct orderProductDummy = OrderProductDummy.dummy();
        ReflectionTestUtils.setField(orderDummy, "orderProducts", List.of(orderProductDummy));

        given(repository.findById(anyInt()))
            .willReturn(Optional.of(EmployeeDummy.dummy()));
        given(orderRepository.findById(anyInt()))
            .willReturn(Optional.of(orderDummy));
        given(codeRepository.findByStatusCodeName(anyString()))
            .willReturn(Optional.of(orderProductDummy.getProduct().getDeliveryType()));

        service.acceptInstallOrder(1, 1);

        then(repository).should(times(1)).findById(1);
        then(orderRepository).should(times(1)).findById(1);
        then(codeRepository).should(times(1)).findByStatusCodeName(DeliveryType.CONSTRUCTION.getValue());
    }

    @Test
    @DisplayName("직원의 시공 설치 배송 완료시 수행")
    void completeDeliveryTest() {
        Order orderDummy = OrderDummy.createOrderDummy();
        OrderProduct orderProductDummy = OrderProductDummy.dummy();
        ReflectionTestUtils.setField(orderDummy, "orderProducts", List.of(orderProductDummy));

        given(repository.findById(anyInt()))
            .willReturn(Optional.of(EmployeeDummy.dummy()));
        given(orderRepository.findById(anyInt()))
            .willReturn(Optional.of(orderDummy));
        given(codeRepository.findByStatusCodeName(DeliveryType.CONSTRUCTION.getValue()))
            .willReturn(Optional.of(orderProductDummy.getProduct().getDeliveryType()));
        given(codeRepository.findByStatusCodeName(OrderStatus.DELIVERY_COMPLETE.getValue()))
            .willReturn(Optional.of(StatusCode.builder()
                .statusCodeName("배송완료")
                .explanation("")
                .priority(1)
                .groupCodeName("배송")
                .build()));

        service.completeDelivery(1, 1);

        then(repository).should(times(1)).findById(1);
        then(orderRepository).should(times(1)).findById(1);
        then(codeRepository).should(times(2)).findByStatusCodeName(anyString());
    }
}
