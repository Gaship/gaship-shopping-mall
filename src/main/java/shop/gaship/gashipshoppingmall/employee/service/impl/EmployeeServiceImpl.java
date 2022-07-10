package shop.gaship.gashipshoppingmall.employee.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import shop.gaship.gashipshoppingmall.employee.dto.CreateEmployeeDto;
import shop.gaship.gashipshoppingmall.employee.dto.GetEmployee;
import shop.gaship.gashipshoppingmall.employee.dto.ModifyEmployeeDto;
import shop.gaship.gashipshoppingmall.employee.entity.Employee;
import shop.gaship.gashipshoppingmall.employee.exception.EmployeeNotFoundException;
import shop.gaship.gashipshoppingmall.employee.repository.EmployeeRepository;
import shop.gaship.gashipshoppingmall.employee.service.EmployeeService;

/**
 *packageName    : shop.gaship.gashipshoppingmall.employee.service.impl
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
    @Override
    @Transactional
    public void createEmployee(CreateEmployeeDto dto) {
        if(repository.findByEmail(dto.getEmail()).isEmpty()){
            repository.save(new Employee(dto));
        }
    }

    @Override
    @Transactional
    @Modifying
    public void modifyEmployee(Integer employeeNo, ModifyEmployeeDto dto) {
        Employee employee = repository.findById(employeeNo)
            .orElseThrow(EmployeeNotFoundException::new);
        employee.modifyEmployee(dto);

        repository.save(employee);
    }

    @Override
    public GetEmployee getEmployee(Integer employeeNo) {
        Employee employee = repository.findById(employeeNo)
            .orElseThrow(EmployeeNotFoundException::new);

        return new GetEmployee(employee);
    }

    @Override
    public List<GetEmployee> getAllEmployees() {
        return repository.findAll().stream().map(GetEmployee::new).collect(Collectors.toList());
    }

}
