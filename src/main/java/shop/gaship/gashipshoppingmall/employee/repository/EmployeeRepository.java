package shop.gaship.gashipshoppingmall.employee.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.employee.entity.Employee;

/**
 *packageName    : shop.gaship.gashipshoppingmall.employee.repository
 * fileName       : EmplyeeRepository
 * author         : 유호철
 * date           : 2022/07/10
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/10        유호철       최초 생성
 */
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    /**
     * methodName : findByEmail
     * author : 유호철
     * description : 중복 email 확인
     *
     * @param email String
     * @return optional
     */
    Optional<Employee> findByEmail(String email);
}
