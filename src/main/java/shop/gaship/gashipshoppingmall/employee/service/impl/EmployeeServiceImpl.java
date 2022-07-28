package shop.gaship.gashipshoppingmall.employee.service.impl;

import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.gaship.gashipshoppingmall.addressLocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.addressLocal.repository.AddressLocalRepository;
import shop.gaship.gashipshoppingmall.employee.dto.request.CreateEmployeeRequestDto;
import shop.gaship.gashipshoppingmall.employee.dto.request.ModifyEmployeeRequestDto;
import shop.gaship.gashipshoppingmall.employee.dto.response.EmployeeInfoResponseDto;
import shop.gaship.gashipshoppingmall.employee.entity.Employee;
import shop.gaship.gashipshoppingmall.employee.exception.EmployeeNotFoundException;
import shop.gaship.gashipshoppingmall.employee.exception.WrongAddressException;
import shop.gaship.gashipshoppingmall.employee.exception.WrongStatusCodeException;
import shop.gaship.gashipshoppingmall.employee.repository.EmployeeRepository;
import shop.gaship.gashipshoppingmall.employee.service.EmployeeService;
import shop.gaship.gashipshoppingmall.member.dto.SignInUserDetailsDto;
import shop.gaship.gashipshoppingmall.response.PageResponse;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;

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

    /**
     * 직원을 생성하기위한 메서드입니다.
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

        Employee employee = new Employee();
        employee.registerEmployee(dto);
        employee.fixLocation(addressLocal);
        employee.fixCode(statusCode);

        repository.save(employee);
    }

    /**
     * 직원에대한 정보를 수정하기위한 메서드입니다.
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
        employee.modifyEmployee(dto);
    }

    /**
     * 직원번호로 하나의 직원에대한 정보를 반환받기위한 메서드입니다.
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

        return new EmployeeInfoResponseDto(employee.getName(), employee.getEmail(),
            employee.getPhoneNo(),
            employee.getAddressLocal().getAddressName());
    }

    /**
     * 전체직원에대한 정보를 조회하기위한 메서드입니다.
     *
     * @return list 직원의 정보들이 반환됩니다.
     * @author 유호철
     */
    @Override
    public PageResponse<EmployeeInfoResponseDto> findEmployees(Pageable pageable) {
        return repository.findAllEmployees(pageable);
    }

    @Override
    public SignInUserDetailsDto findSignInEmployeeFromEmail(String email) {
        return repository.findSignInEmployeeUserDetail(email)
            .orElseThrow(EmployeeNotFoundException::new);
    }
}
