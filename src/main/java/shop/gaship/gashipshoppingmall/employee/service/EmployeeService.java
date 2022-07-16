package shop.gaship.gashipshoppingmall.employee.service;

import shop.gaship.gashipshoppingmall.employee.dto.request.CreateEmployeeRequestDto;
import shop.gaship.gashipshoppingmall.employee.dto.request.ModifyEmployeeRequestDto;
import shop.gaship.gashipshoppingmall.employee.dto.response.EmployeeInfoResponseDto;

import java.util.List;

/**
 * 직원을 서비스레이어에서 사용하기위한 인터페이스 클래스입니다.
 *
 *
 * @see
 * @author 유호철
 * @since 1.0
 */
public interface EmployeeService {

    /**
     * 직원을 생성하기위한 메서드입니다.
     *
     * @param dto 직원을생성하기위한 정보가들어있습니다
     * @author 유호철
     */
    void addEmployee(CreateEmployeeRequestDto dto);

    /**
     * 직원의 정보를수정하기위한 메서드입니다.
     *
     * @param dto 수정되어야할 직원의 정보들이 담겨져있습니다.
     * @author 유호철
     */
    void modifyEmployee(ModifyEmployeeRequestDto dto);

    /**
     * 직원번호를 통해 직원에대한 정보를 얻는 메서드입니다.
     *
     * @param employeeNo 직원번호 입니다.
     * @return employeeInfoResponseDto 반환되어야할 직원정보들이 반환됩니다.
     * @author 유호철
     */
    EmployeeInfoResponseDto findEmployee(Integer employeeNo);

    /**
     * 모든직원들의 정보를 반환하기위한 메서드입니다.
     *
     * @return list 반환되어야할 직원정보들이 리스트형태로 반환됩니다.
     * @author 유호철
     */
    List<EmployeeInfoResponseDto> findEmployees();
}
