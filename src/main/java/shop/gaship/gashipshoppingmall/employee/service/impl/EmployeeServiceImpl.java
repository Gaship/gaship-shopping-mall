package shop.gaship.gashipshoppingmall.employee.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
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
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * packageName    : shop.gaship.gashipshoppingmall.employee.service.impl
 * fileName       : EmployeeServiceImpl
 * author         : 유호철
 * date           : 2022/07/10
 * description    : EmployeeService 구현체
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/10        유호철       최초 생성
 */

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository repository;
    private final StatusCodeRepository statusCodeRepository;
    private final AddressLocalRepository localRepository;

    @Override
    @Transactional
    public void createEmployee(CreateEmployeeRequestDto dto) {

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

    @Override
    @Transactional
    @Modifying
    public void modifyEmployee(ModifyEmployeeRequestDto dto) {
        Employee employee = repository.findById(dto.getEmployeeNo())
                .orElseThrow(EmployeeNotFoundException::new);
        employee.modifyEmployee(dto);

        repository.save(employee);
    }

    @Override
    public EmployeeInfoResponseDto getEmployee(Integer employeeNo) {
        Employee employee = repository.findById(employeeNo)
                .orElseThrow(EmployeeNotFoundException::new);

        return new EmployeeInfoResponseDto(employee);
    }

    @Override
    public List<EmployeeInfoResponseDto> getAllEmployees() {
        return repository.findAll().stream()
                .map(EmployeeInfoResponseDto::new)
                .collect(Collectors.toList());
    }
}
