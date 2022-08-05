package shop.gaship.gashipshoppingmall.employee.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import shop.gaship.gashipshoppingmall.employee.dto.response.EmployeeInfoResponseDto;
import shop.gaship.gashipshoppingmall.member.dto.response.SignInUserDetailsDto;
import shop.gaship.gashipshoppingmall.order.entity.Order;
import shop.gaship.gashipshoppingmall.response.PageResponse;


/**
 * 직원 관련 커스텀 쿼리를 선언하는 인터페이스입니다.
 */
@NoRepositoryBean
public interface EmployeeRepositoryCustom {
    /**
     * 직원의 계정 정보를 이메일을 통해서 찾을 때 사용됩니다.
     *
     * @param email 직원의 이메일입니다.
     * @return Optional에 감싸진 직원의 계정정보입니다. 없다면 Null이 감싸져있습니다.
     */
    Optional<SignInUserDetailsDto> findSignInEmployeeUserDetail(String email);

    /**
     * 직원의 정보가담긴 리스트를 반환합니다.
     *
     * @param pageable 페이징 정보가 들어옵니다.
     * @return 직원의 정보가 담긴 리스트가 반환됩니다.
     */
    PageResponse<EmployeeInfoResponseDto> findAllEmployees(Pageable pageable);

    Page<Order> findOrderBasedOnEmployeeLocation(Pageable pageable, Integer employeeNo);
}
