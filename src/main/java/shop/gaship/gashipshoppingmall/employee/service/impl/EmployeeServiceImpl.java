package shop.gaship.gashipshoppingmall.employee.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import shop.gaship.gashipshoppingmall.addresslocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.addresslocal.repository.AddressLocalRepository;
import shop.gaship.gashipshoppingmall.dataprotection.util.Aes;
import shop.gaship.gashipshoppingmall.dataprotection.util.Sha512;
import shop.gaship.gashipshoppingmall.employee.dto.request.CreateEmployeeRequestDto;
import shop.gaship.gashipshoppingmall.employee.dto.request.ModifyEmployeeRequestDto;
import shop.gaship.gashipshoppingmall.employee.dto.response.EmployeeInfoResponseDto;
import shop.gaship.gashipshoppingmall.employee.dto.response.InstallOrderResponseDto;
import shop.gaship.gashipshoppingmall.employee.entity.Employee;
import shop.gaship.gashipshoppingmall.employee.exception.EmailAlreadyExistException;
import shop.gaship.gashipshoppingmall.employee.exception.EmployeeNotFoundException;
import shop.gaship.gashipshoppingmall.employee.exception.WrongAddressException;
import shop.gaship.gashipshoppingmall.employee.exception.WrongStatusCodeException;
import shop.gaship.gashipshoppingmall.employee.repository.EmployeeRepository;
import shop.gaship.gashipshoppingmall.employee.service.EmployeeService;
import shop.gaship.gashipshoppingmall.member.dto.response.SignInUserDetailsDto;
import shop.gaship.gashipshoppingmall.order.entity.Order;
import shop.gaship.gashipshoppingmall.order.exception.OrderNotFoundException;
import shop.gaship.gashipshoppingmall.order.repository.OrderRepository;
import shop.gaship.gashipshoppingmall.orderproduct.entity.OrderProduct;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.exception.StatusCodeNotFoundException;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;
import shop.gaship.gashipshoppingmall.statuscode.status.DeliveryType;
import shop.gaship.gashipshoppingmall.statuscode.status.OrderStatus;
import shop.gaship.gashipshoppingmall.util.PageResponse;

/**
 * 서비스레이어에서 직원에대한 요청을 사용하기위한 구현체 클래스입니다.
 *
 * @author : 유호철
 * @see EmployeeService
 * @since 1.0
 */

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository repository;

    private final StatusCodeRepository statusCodeRepository;

    private final AddressLocalRepository localRepository;

    private final OrderRepository orderRepository;

    private final Aes aes;

    private final Sha512 sha512;


    /**
     * {@inheritDoc}
     *
     * @param dto 직원을 생성하기위한 정보들이 담겨있습니다.
     * @throws WrongStatusCodeException 잘못된코드가들어갈경우.
     * @throws WrongAddressException    잘못된주소가들어갈경우.
     * @author 유호철
     */
    @Override
    @Transactional
    public void addEmployee(CreateEmployeeRequestDto dto) {

        StatusCode statusCode = statusCodeRepository.findById(dto.getAuthorityNo())
            .orElseThrow(WrongStatusCodeException::new);
        AddressLocal addressLocal = localRepository.findById(dto.getAddressNo())
            .orElseThrow(WrongAddressException::new);

        if (Boolean.TRUE.equals(repository.existsByEncodedEmailForSearch(
            sha512.encryptPlainText(dto.getEmail())))) {
            throw new EmailAlreadyExistException();
        }

        Employee employee = Employee.builder()
            .addressLocal(addressLocal)
            .statusCode(statusCode)
            .email(aes.aesEcbEncode(dto.getEmail()))
            .name(dto.getName())
            .password((dto.getPassword()))
            .encodedEmailForSearch(sha512.encryptPlainText(dto.getEmail()))
            .phoneNo(aes.aesEcbEncode(dto.getPhoneNo()))
            .build();

        repository.save(employee);
    }

    /**
     * {@inheritDoc}
     *
     * @param dto 수정할 직원의 정보들이 담겨져있습니다.
     * @throws EmployeeNotFoundException 직원이없을경우.
     * @author 유호철
     */
    @Override
    @Transactional
    public void modifyEmployee(ModifyEmployeeRequestDto dto) {
        Employee employee = repository.findById(dto.getEmployeeNo())
            .orElseThrow(EmployeeNotFoundException::new);
        employee.modifyEmployee(
            aes.aesEcbEncode(dto.getName()),
            aes.aesEcbEncode(dto.getPhoneNo()));
    }

    /**
     * {@inheritDoc}
     *
     * @param employeeNo 조회하기위한 직원번호입니다.
     * @return employeeInfoResponseDto 반환받게되는 직원정보들이 담겨있습니다.
     * @throws EmployeeNotFoundException 직원이 없을경우.
     * @author 유호철
     */
    @Override
    public EmployeeInfoResponseDto findEmployee(Integer employeeNo) {
        Employee employee = repository.findById(employeeNo)
            .orElseThrow(EmployeeNotFoundException::new);

        return new EmployeeInfoResponseDto(
            aes.aesEcbDecode(employee.getName()),
            aes.aesEcbDecode(employee.getEmail()),
            employee.getPassword(),
            employee.getAddressLocal().getAddressName(), 1);
    }

    /**
     * {@inheritDoc}
     *
     * @return list 직원의 정보들이 반환됩니다.
     * @author 유호철
     */
    @Override
    public PageResponse<EmployeeInfoResponseDto> findEmployees(Pageable pageable) {
        return repository.findAllEmployees(pageable);
    }

    /**
     * {@inheritDoc}
     *
     * @return 로그인 할 직원의 계정 상세정보를 반환합니다.
     * @author 김민수
     */
    @Override
    public SignInUserDetailsDto findSignInEmployeeFromEmail(String email) {
        SignInUserDetailsDto result = repository
            .findSignInEmployeeUserDetail(sha512.encryptPlainText(email))
            .orElseThrow(EmployeeNotFoundException::new);

        result.setEmail(aes.aesEcbDecode(result.getEmail()));

        return result;
    }

    @Override
    public Page<InstallOrderResponseDto> findInstallOrdersFromEmployeeLocation(
        Pageable pageable, Integer employeeNo) {
        Page<Order> orderPage = repository.findOrderBasedOnEmployeeLocation(pageable, employeeNo);
        List<InstallOrderResponseDto> installOrders = orderPage.getContent().stream()
            .map(order -> InstallOrderResponseDto.builder()
                .orderNo(order.getNo())
                .address(order.getAddressList()
                    .getAddressLocal()
                    .getUpperLocal()
                    .getAddressName().concat(" ")
                    .concat(order.getAddressList()
                        .getAddressLocal()
                        .getAddressName()).concat(" ")
                    .concat(order.getAddressList().getAddress()))
                .build())
            .collect(Collectors.toUnmodifiableList());

        return PageableExecutionUtils.getPage(installOrders, pageable, orderPage::getTotalPages);
    }

    /**
     * {@inheritDoc}
     *
     * @param employeeNo 직원 고유번호입니다.
     * @param orderNo    요청을 받을 주문 고유번호입니다.
     */
    @Override
    @Transactional
    public void acceptInstallOrder(Integer employeeNo, Integer orderNo) {
        Employee employee = repository.findById(employeeNo)
            .orElseThrow(EmployeeNotFoundException::new);
        Order order = orderRepository.findById(orderNo)
            .orElseThrow(OrderNotFoundException::new);
        StatusCode constructionStatusCode = statusCodeRepository
            .findByStatusCodeName(DeliveryType.CONSTRUCTION.getValue())
            .orElseThrow(StatusCodeNotFoundException::new);

        order.getOrderProducts().forEach(orderProduct -> {
            if (isEqualsDeliverType(constructionStatusCode, orderProduct)) {
                orderProduct.acceptedInstallEmployee(employee);
            }
        });
    }

    @Override
    public void completeDelivery(Integer employeeNo, Integer orderNo) {
        Employee employee = repository.findById(employeeNo)
            .orElseThrow(EmployeeNotFoundException::new);
        Order order = orderRepository.findById(orderNo)
            .orElseThrow(OrderNotFoundException::new);
        StatusCode constructionStatusCode = statusCodeRepository
            .findByStatusCodeName(DeliveryType.CONSTRUCTION.getValue())
            .orElseThrow(StatusCodeNotFoundException::new);
        StatusCode deliveryCompleteStatus = statusCodeRepository
            .findByStatusCodeName(OrderStatus.DELIVERY_COMPLETE.getValue())
            .orElseThrow(StatusCodeNotFoundException::new);

        order.getOrderProducts().forEach(orderProduct -> {
            if (isEqualsDeliverType(constructionStatusCode, orderProduct)
                && isEqualDeliveredEmployee(employee, orderProduct)) {
                orderProduct.updateOrderProductStatus(deliveryCompleteStatus);
            }
        });
    }

    /**
     * 배송타입이 시공타입인지 비교연산하는 메서드입니다.
     *
     * @param constructionStatusCode 배송타입 상태코드입니다.
     * @param orderProduct           배송타입인지 확인할 주문상세품입니다.
     * @return 시공타입인지에 대한 결과를 반환합니다.
     */
    private boolean isEqualsDeliverType(
        StatusCode constructionStatusCode, OrderProduct orderProduct) {
        return Objects.equals(orderProduct.getProduct().getDeliveryType(), constructionStatusCode);
    }

    /**
     * 배송완료할 물품에 대해 배송했던 직원이 같은 직원인지 비교하는 메서드입니다.
     *
     * @param employee     요청한 직원입니다.
     * @param orderProduct 배송 수락했던 직원입니다.
     * @return 같은 직원이면 true, 다르면 false를 반환합니다.
     */
    private boolean isEqualDeliveredEmployee(Employee employee, OrderProduct orderProduct) {
        return Objects.equals(employee, orderProduct.getEmployee());
    }
}
