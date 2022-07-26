package shop.gaship.gashipshoppingmall.employee.repository.impl;

import com.querydsl.core.types.Projections;
import java.util.Optional;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.gaship.gashipshoppingmall.employee.entity.Employee;
import shop.gaship.gashipshoppingmall.employee.entity.QEmployee;
import shop.gaship.gashipshoppingmall.employee.repository.EmployeeRepositoryCustom;
import shop.gaship.gashipshoppingmall.member.dto.SignInUserDetailsDto;

/**
 * 쿼리DSL을 통해 직원에 관한 커스텀 쿼리를 구현시 사용하는 클래스입니다.
 *
 * @author 김민수
 * @see QuerydslRepositorySupport
 * @see EmployeeRepositoryCustom
 * @since 1.0
 */
public class EmployeeRepositoryImpl extends QuerydslRepositorySupport implements
        EmployeeRepositoryCustom {

    public EmployeeRepositoryImpl() {
        super(Employee.class);
    }

    /**
     * {@inheritDoc}
     *
     * @param email 직원의 이메일입니다.
     * @return {@inheritDoc}
     */
    @Override
    public Optional<SignInUserDetailsDto> findSignInEmployeeUserDetail(String email) {
        QEmployee employee = QEmployee.employee;

        return Optional.ofNullable(
            from(employee)
            .where(employee.email.eq(email))
            .select(
                Projections.constructor(
                    SignInUserDetailsDto.class,
                    employee.employeeNo,
                    employee.email,
                    employee.password,
                    Projections.list(employee.statusCode.statusCodeName))
            )
            .fetchOne()
        );
    }
}
