package shop.gaship.gashipshoppingmall.employee.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.employee.entity.Employee;


/**
 * 직원을 다루기위한 레포지토리 인터페이스 입니다.
 * JPA 를 사용합니다.
 *
 * @author : 유호철
 * @see JpaRepository
 * @since 1.0
 */
public interface EmployeeRepository
    extends JpaRepository<Employee, Integer>, EmployeeRepositoryCustom {

    /**
     * 이메일을통해 직원을 조회하기위한 메서드입니다.
     *
     * @param email 조회하고싶은 email 이 들어있습니다.
     * @return optional 직원에대한 정보를 optional 로 반환합니다.
     * @author 유호철
     */
    Optional<Employee> findByEmail(String email);
}
