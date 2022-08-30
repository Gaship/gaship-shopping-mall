package shop.gaship.gashipshoppingmall.order.repository.custom.impl;

import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import shop.gaship.gashipshoppingmall.addresslist.entity.QAddressList;
import shop.gaship.gashipshoppingmall.order.dto.response.OrderCancelResponseDto;
import shop.gaship.gashipshoppingmall.order.entity.Order;
import shop.gaship.gashipshoppingmall.order.entity.QOrder;
import shop.gaship.gashipshoppingmall.order.repository.OrderRepositoryCustom;
import shop.gaship.gashipshoppingmall.orderproduct.entity.QOrderProduct;
import shop.gaship.gashipshoppingmall.statuscode.entity.QStatusCode;

/**
 * 쿼리 DSL 을 통해 주문에 관한 커스텀 쿼리를 구현시 사용하는 클래스입니다.
 *
 * @author : 유호철
 * @see QuerydslRepositorySupport
 * @see OrderRepositoryCustom
 * @since 1.0
 */
public class OrderRepositoryImpl extends QuerydslRepositorySupport
    implements OrderRepositoryCustom {
    public OrderRepositoryImpl() {
        super(Order.class);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Page<OrderCancelResponseDto> findCancelOrders(Integer memberNo,
                                                         String statusName,
                                                         Pageable pageable) {
        //주문 취소/교환 조회
        //취소금액, 취소사유

        QOrder order = QOrder.order;
        QOrderProduct orderProduct = QOrderProduct.orderProduct;
        QStatusCode statusCode = QStatusCode.statusCode;
        QAddressList addressList = QAddressList.addressList;

        List<OrderCancelResponseDto> content = from(order)
            .innerJoin(order.orderProducts, orderProduct)
            .innerJoin(order.addressList, addressList)
            .innerJoin(orderProduct.orderStatusCode, statusCode)
            .where(order.member.memberNo.eq(memberNo)
                .and(statusCode.statusCodeName.eq(statusName)))
            .select(Projections.constructor(OrderCancelResponseDto.class,
                order.no.as("orderNo"),
                order.addressList.address,
                order.orderDatetime,
                order.receiptName,
                order.receiptPhoneNumber,
                order.deliveryRequest,
                order.totalOrderAmount,
                orderProduct.no.as("orderProductNo"),
                orderProduct.cancellationAmount,
                orderProduct.cancellationReason,
                orderProduct.cancellationDatetime))
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .fetch();

        return PageableExecutionUtils.getPage(content, pageable, content::size);
    }


}
