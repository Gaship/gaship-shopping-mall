package shop.gaship.gashipshoppingmall.employee.service;

import java.util.List;
import shop.gaship.gashipshoppingmall.employee.dto.request.CreateEmployeeRequestDto;
import shop.gaship.gashipshoppingmall.employee.dto.request.ModifyEmployeeRequestDto;
import shop.gaship.gashipshoppingmall.employee.dto.response.EmployeeInfoResponseDto;
import shop.gaship.gashipshoppingmall.member.dto.SignInUserDetailsDto;

/**
 * 직원을 서비스레이어에서 사용하기위한 인터페이스 클래스입니다.
 *
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

    /**
     * 직원이 로그인을 시도했을 시 해당 계정에 맞는 직원 상세정보를 전달합니다.
     *
     * @return 로그인 할 직원의 계정 상세정보를 반환합니다.
     */
    SignInUserDetailsDto findSignInEmployeeFromEmail(String email);
}
