package shop.gaship.gashipshoppingmall.orderproduct.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.gaship.gashipshoppingmall.delivery.dto.DeliveryDto;
import shop.gaship.gashipshoppingmall.order.entity.QOrder;
import shop.gaship.gashipshoppingmall.orderproduct.entity.OrderProduct;
import shop.gaship.gashipshoppingmall.orderproduct.entity.QOrderProduct;
import shop.gaship.gashipshoppingmall.orderproduct.repository.OrderProductRepositoryCustom;
import shop.gaship.gashipshoppingmall.totalsale.dto.request.TotalSaleRequestDto;
import shop.gaship.gashipshoppingmall.totalsale.dto.response.TotalSaleResponseDto;

/**
 * 주문제품을 QueryDsl 을 통해 사용하기위한 클래스 구현체입니다.
 *
 * @author : 유호철
 * @see QuerydslRepositorySupport
 * @see OrderProductRepositoryCustom
 * @since 1.0
 */
public class OrderProductRepositoryImpl extends QuerydslRepositorySupport
    implements OrderProductRepositoryCustom {

    public OrderProductRepositoryImpl() {
        super(OrderProduct.class);
    }

    @Override
    public List<TotalSaleResponseDto> findTotalSale(TotalSaleRequestDto dto) {
        QOrderProduct orderProduct = QOrderProduct.orderProduct;
        QOrder order = QOrder.order;

        //커버링 인덱스 적용
        List<Integer> orderList = from(order)
            .select(order.no)
            .where(order.orderDatetime.between(dto.getStartDate(), dto.getEndDate())).fetch();

        if (orderList.isEmpty()) {
            return new ArrayList<>();
        }

        return from(order)
            .innerJoin(orderProduct)
            .on(order.eq(orderProduct.order))
            .where(order.no.in(orderList))
            .select(Projections.constructor(TotalSaleResponseDto.class,
                order.orderDatetime.as("totalSaleDate"),
                orderProduct.product.no.count().as("orderCnt"),
                queryCase(orderProduct.orderStatusCode.statusCodeNo
                    .between(1, 12), orderProduct.no)
                    .otherwise(0)
                    .count().as("orderCancelCnt"),
                queryCase(orderProduct.orderStatusCode.statusCodeNo.eq(13),
                    orderProduct.no)
                    .otherwise(0)
                    .count().as("orderSaleCnt"),
                orderProduct.amount.sum().as("totalAmount"),
                queryCase(orderProduct.orderStatusCode.statusCodeNo.between(7, 12),
                    orderProduct.cancellationAmount)
                    .otherwise(0)
                    .sum().as("cancelAmount"),
                (new CaseBuilder()
                    .when(orderProduct.orderStatusCode.statusCodeNo.eq(13)
                                                                   .or(orderProduct.orderStatusCode.statusCodeNo.eq(4)))
                    .then(orderProduct.amount)
                    .otherwise(0L))
                    .sum().as("orderSaleAmount")))
            .groupBy(order.orderDatetime)
            .fetch();
    }

    private CaseBuilder.Cases<Integer, NumberExpression<Integer>> queryCase(
        BooleanExpression statusCodeNo,
        NumberPath<Integer> orderProduct) {
        return new CaseBuilder()
            .when(statusCodeNo)
            .then(orderProduct);
    }

    @Override
    public Optional<DeliveryDto> findOrderInfo(Integer orderProductNo) {
        QOrderProduct orderProduct = QOrderProduct.orderProduct;
        QOrder order = QOrder.order;

        DeliveryDto deliveryDto = from(orderProduct)
            .innerJoin(orderProduct.order, order)
            .where(orderProduct.no.eq(orderProductNo))
            .select(Projections.constructor(DeliveryDto.class,
                order.receiptName,
                order.addressList.address,
                order.receiptPhoneNumber,
                order.no)).fetchOne();

        return Optional.of(deliveryDto);
    }
}
