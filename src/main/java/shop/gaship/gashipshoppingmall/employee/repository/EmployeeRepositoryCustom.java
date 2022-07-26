package shop.gaship.gashipshoppingmall.employee.repository;

import java.util.Optional;
import org.springframework.data.repository.NoRepositoryBean;

import shop.gaship.gashipshoppingmall.member.dto.SignInUserDetailsDto;

@NoRepositoryBean
public interface EmployeeRepositoryCustom {
    /**
     * 직원의 계정 정보를 이메일을 통해서 찾을 때 사용됩니다.
     *
     * @param email 직원의 이메일입니다.
     * @return Optional에 감싸진 직원의 계정정보입니다. 없다면 Null이 감싸져있습니다.
     */
    Optional<SignInUserDetailsDto> findSignInEmployeeUserDetail(String email);
}
