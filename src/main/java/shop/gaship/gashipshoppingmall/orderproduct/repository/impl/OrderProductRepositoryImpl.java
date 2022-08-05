package shop.gaship.gashipshoppingmall.orderproduct.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.gaship.gashipshoppingmall.order.entity.QOrder;
import shop.gaship.gashipshoppingmall.orderproduct.entity.OrderProduct;
import shop.gaship.gashipshoppingmall.orderproduct.entity.QOrderProduct;
import shop.gaship.gashipshoppingmall.orderproduct.repository.OrderProductRepositoryCustom;
import shop.gaship.gashipshoppingmall.totalsale.dto.request.TotalSaleRequestDto;
import shop.gaship.gashipshoppingmall.totalsale.dto.response.TotalSaleDto;

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
    public List<TotalSaleDto> findTotalSale(TotalSaleRequestDto dto) {
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
            .select(Projections.constructor(TotalSaleDto.class,
                order.orderDatetime.as("totalSaleDate"),
                orderProduct.product.no.count().as("orderCnt"),
                (new CaseBuilder()
                    .when(orderProduct.orderStatusCode.statusCodeNo.between(1, 12))
                    .then(orderProduct.no)
                    .otherwise(0)).count().as("orderCancelCnt"),
                (new CaseBuilder()
                    .when(orderProduct.orderStatusCode.statusCodeNo.eq(13))
                    .then(orderProduct.no)
                    .otherwise(0)).count().as("orderSaleCnt"),
                orderProduct.amount.sum().as("totalAmount"),
                (new CaseBuilder()
                    .when(orderProduct.orderStatusCode.statusCodeNo.between(7, 12))
                    .then(orderProduct.cancellationAmount)
                    .otherwise(0)).sum().as("cancelAmount"),
                (new CaseBuilder()
                    .when(orderProduct.orderStatusCode.statusCodeNo.eq(13)
                        .or(orderProduct.orderStatusCode.statusCodeNo.eq(4)))
                    .then(orderProduct.amount)
                    .otherwise(0L)).sum().as("orderSaleAmount")))
            .groupBy(order.orderDatetime)
            .fetch();
    }
}
