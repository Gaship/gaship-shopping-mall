package shop.gaship.gashipshoppingmall.employee.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import shop.gaship.gashipshoppingmall.addressLocal.entity.QAddressLocal;
import shop.gaship.gashipshoppingmall.employee.dto.response.EmployeeInfoResponseDto;
import shop.gaship.gashipshoppingmall.employee.entity.Employee;
import shop.gaship.gashipshoppingmall.employee.entity.QEmployee;
import shop.gaship.gashipshoppingmall.employee.repository.EmployeeRepositoryCustom;
import shop.gaship.gashipshoppingmall.member.dto.response.SignInUserDetailsDto;
import shop.gaship.gashipshoppingmall.response.PageResponse;


/**
 * 쿼리DSL을 통해 직원에 관한 커스텀 쿼리를 구현시 사용하는 클래스입니다.
 *
 * @author 김민수
 * @see QuerydslRepositorySupport
 * @see EmployeeRepositoryCustom
 * @since 1.0
 */
public class EmployeeRepositoryImpl extends QuerydslRepositorySupport
    implements EmployeeRepositoryCustom {

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
                        employee.password.as("hashedPassword"),
                        Expressions.asBoolean(false),
                        Projections.list(employee.statusCode.statusCodeName))
                )
                .fetchOne()
        );
    }

    /**
     * {@inheritDoc}
     *
     * @param pageable 페이징 정보가 들어옵니다.
     * @return 직원의 정보가 담긴 리스트가 반환됩니다.
     */
    @Override
    public PageResponse<EmployeeInfoResponseDto> findAllEmployees(Pageable pageable) {
        QEmployee employee = QEmployee.employee;
        QAddressLocal addressLocal = QAddressLocal.addressLocal;
        JPQLQuery<EmployeeInfoResponseDto> query = from(employee)
            .innerJoin(employee.addressLocal, addressLocal)
            .select(
                Projections.constructor(
                    EmployeeInfoResponseDto.class,
                    employee.name,
                    employee.email,
                    employee.phoneNo,
                    employee.addressLocal.addressName)
            );

        List<EmployeeInfoResponseDto> content =
            query.offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        return new PageResponse<>(PageableExecutionUtils.getPage(content, pageable,
            () -> query.fetch()
                .size()));
    }
}
