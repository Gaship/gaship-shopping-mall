package shop.gaship.gashipshoppingmall.employee.service;

import java.util.List;
import shop.gaship.gashipshoppingmall.employee.dto.request.CreateEmployeeRequestDto;
import shop.gaship.gashipshoppingmall.employee.dto.response.EmployeeInfoResponseDto;
import shop.gaship.gashipshoppingmall.employee.dto.request.ModifyEmployeeRequestDto;

/**
 *packageName     : shop.gaship.gashipshoppingmall.employee.service
 * fileName       : EmployeeService
 * author         : 유호철
 * date           : 2022/07/10
 * description    : Employee Service 를 위한 class
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/10        유호철       최초 생성
 */
public interface EmployeeService {

    void createEmployee(CreateEmployeeRequestDto dto);

    void modifyEmployee(ModifyEmployeeRequestDto dto);

    EmployeeInfoResponseDto getEmployee(Integer employeeNo);

    List<EmployeeInfoResponseDto> getAllEmployees();
}
