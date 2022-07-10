package shop.gaship.gashipshoppingmall.employee.service;

import java.util.List;
import shop.gaship.gashipshoppingmall.employee.dto.CreateEmployeeDto;
import shop.gaship.gashipshoppingmall.employee.dto.GetEmployee;
import shop.gaship.gashipshoppingmall.employee.dto.ModifyEmployeeDto;

/**
 *packageName    : shop.gaship.gashipshoppingmall.employee.service
 * fileName       : EmployeeService
 * author         : 유호철
 * date           : 2022/07/10
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/10        유호철       최초 생성
 */
public interface EmployeeService {

    void createEmployee(CreateEmployeeDto dto);

    void modifyEmployee(Integer empolyeeNo, ModifyEmployeeDto dto);

    GetEmployee getEmployee(Integer employeeNo);

    List<GetEmployee> getAllEmployees();
}
