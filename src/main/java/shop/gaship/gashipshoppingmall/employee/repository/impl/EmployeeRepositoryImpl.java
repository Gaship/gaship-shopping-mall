package shop.gaship.gashipshoppingmall.employee.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import shop.gaship.gashipshoppingmall.addresslist.entity.QAddressList;
import shop.gaship.gashipshoppingmall.addresslocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.addresslocal.entity.QAddressLocal;
import shop.gaship.gashipshoppingmall.employee.dto.response.EmployeeInfoResponseDto;
import shop.gaship.gashipshoppingmall.employee.entity.Employee;
import shop.gaship.gashipshoppingmall.employee.entity.QEmployee;
import shop.gaship.gashipshoppingmall.employee.repository.EmployeeRepositoryCustom;
import shop.gaship.gashipshoppingmall.member.dto.response.SignInUserDetailsDto;
import shop.gaship.gashipshoppingmall.order.entity.Order;
import shop.gaship.gashipshoppingmall.order.entity.QOrder;
import shop.gaship.gashipshoppingmall.orderproduct.entity.OrderProduct;
import shop.gaship.gashipshoppingmall.orderproduct.entity.QOrderProduct;
import shop.gaship.gashipshoppingmall.product.entity.QProduct;
import shop.gaship.gashipshoppingmall.statuscode.entity.QStatusCode;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.status.DeliveryType;
import shop.gaship.gashipshoppingmall.statuscode.status.OrderStatus;


/**
 * 쿼리 DSL 을 통해 직원에 관한 커스텀 쿼리를 구현시 사용하는 클래스입니다.
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
                .where(employee.encodedEmailForSearch.eq(email))
                .select(
                    Projections.constructor(
                        SignInUserDetailsDto.class,
                        employee.employeeNo,
                        employee.email,
                        employee.password.as("hashedPassword"),
                        Expressions.FALSE.as("isSocial"),
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
    public Page<EmployeeInfoResponseDto> findAllEmployees(Pageable pageable) {
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
                    employee.addressLocal.addressName,
                    employee.employeeNo)
            );

        List<EmployeeInfoResponseDto> content =
            query.offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        return (PageableExecutionUtils.getPage(content, pageable,
            query::fetchCount));
    }

    /**
     * {@inheritDoc}
     *
     * @param pageable   페이징 객체입니다.
     * @param employeeNo 직원의 고유번호입니다.
     * @return {@inheritDoc}
     */
    @Override
    public Page<Order> findOrderBasedOnEmployeeLocation(Pageable pageable,
                                                        Integer employeeNo) {
        QEmployee employee = QEmployee.employee;
        QOrder order = QOrder.order;
        QStatusCode statusCode = QStatusCode.statusCode;
        QAddressLocal addressLocal = QAddressLocal.addressLocal;
        QAddressList addressList = QAddressList.addressList;
        QOrderProduct orderProduct = QOrderProduct.orderProduct;
        QProduct product = QProduct.product;

        JPQLQuery<AddressLocal> retrieveDeliverLocalPoint = from(employee)
            .innerJoin(employee.addressLocal, addressLocal)
            .where(employee.employeeNo.eq(employeeNo))
            .select(addressLocal.upperLocal);

        JPQLQuery<StatusCode> installStatusCode = from(statusCode)
            .where(statusCode.statusCodeName.eq(DeliveryType.CONSTRUCTION.getValue()))
            .select(statusCode);

        JPQLQuery<OrderProduct> installWorkResult = from(orderProduct)
            .innerJoin(orderProduct.order, order)
            .innerJoin(orderProduct.product, product)
            .innerJoin(order.addressList, addressList)
            .innerJoin(addressList.addressLocal, addressLocal)
            .innerJoin(orderProduct.orderStatusCode, statusCode)
            .where(addressLocal.upperLocal.eq(retrieveDeliverLocalPoint),
                product.deliveryType.eq(installStatusCode),
                statusCode.statusCodeName
                    .eq(OrderStatus.DELIVERY_PREPARING.getValue()));

        JPQLQuery<Order> employeeInstallWorkResult =
            installWorkResult.select(order)
                .orderBy(order.orderDatetime.asc())
                .distinct()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        return PageableExecutionUtils.getPage(
            employeeInstallWorkResult.fetch(), pageable, installWorkResult::fetchCount);
    }
}
