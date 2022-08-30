package shop.gaship.gashipshoppingmall.orderproduct.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPQLQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import shop.gaship.gashipshoppingmall.delivery.dto.DeliveryDto;
import shop.gaship.gashipshoppingmall.member.entity.QMember;
import shop.gaship.gashipshoppingmall.order.entity.QOrder;
import shop.gaship.gashipshoppingmall.orderproduct.dto.response.OrderProductDetailResponseDto;
import shop.gaship.gashipshoppingmall.orderproduct.dto.response.OrderProductResponseDto;
import shop.gaship.gashipshoppingmall.orderproduct.entity.OrderProduct;
import shop.gaship.gashipshoppingmall.orderproduct.entity.QOrderProduct;
import shop.gaship.gashipshoppingmall.orderproduct.repository.OrderProductRepositoryCustom;
import shop.gaship.gashipshoppingmall.product.entity.QProduct;
import shop.gaship.gashipshoppingmall.statuscode.entity.QStatusCode;
import shop.gaship.gashipshoppingmall.totalsale.dto.request.TotalSaleRequestDto;
import shop.gaship.gashipshoppingmall.totalsale.dto.response.TotalSaleResponseDto;

import static shop.gaship.gashipshoppingmall.statuscode.status.OrderStatus.CANCELLATION;
import static shop.gaship.gashipshoppingmall.statuscode.status.OrderStatus.CANCEL_COMPLETE;
import static shop.gaship.gashipshoppingmall.statuscode.status.OrderStatus.DELIVERY_COMPLETE;
import static shop.gaship.gashipshoppingmall.statuscode.status.OrderStatus.DELIVERY_PREPARING;
import static shop.gaship.gashipshoppingmall.statuscode.status.OrderStatus.EXCHANGE_COMPLETE;
import static shop.gaship.gashipshoppingmall.statuscode.status.OrderStatus.EXCHANGE_RECEPTION;
import static shop.gaship.gashipshoppingmall.statuscode.status.OrderStatus.PURCHASE_CONFIRMATION;
import static shop.gaship.gashipshoppingmall.statuscode.status.OrderStatus.RETURN_COMPLETE;
import static shop.gaship.gashipshoppingmall.statuscode.status.OrderStatus.RETURN_RECEPTION;
import static shop.gaship.gashipshoppingmall.statuscode.status.OrderStatus.SHIPPING;


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
        QStatusCode statusCode = QStatusCode.statusCode;

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
            .innerJoin(orderProduct.orderStatusCode, statusCode)
            .where(order.no.in(orderList))
            .select(Projections.constructor(TotalSaleResponseDto.class,
                order.orderDatetime.as("totalSaleDate"),
                orderProduct.product.no.count().as("orderCnt"),
                queryCaseInteger(orderProduct.orderStatusCode.statusCodeName
                        .eq(EXCHANGE_RECEPTION.getValue())
                        .or(orderProduct.orderStatusCode.statusCodeName
                            .eq(CANCELLATION.getValue()))
                        .or(orderProduct.orderStatusCode.statusCodeName
                            .eq(RETURN_RECEPTION.getValue()))
                        .or(orderProduct.orderStatusCode.statusCodeName
                            .eq(EXCHANGE_COMPLETE.getValue()))
                        .or(orderProduct.orderStatusCode.statusCodeName
                            .eq(CANCEL_COMPLETE.getValue()))
                        .or(orderProduct.orderStatusCode.statusCodeName
                            .eq(RETURN_COMPLETE.getValue())),
                    orderProduct.no)
                    .otherwise((Integer) null)
                    .count().as("orderCancelCnt"),
                queryCaseInteger(orderProduct.orderStatusCode.statusCodeName
                        .eq(PURCHASE_CONFIRMATION.getValue())
                        .or(orderProduct.orderStatusCode.statusCodeName
                            .eq(SHIPPING.getValue()))
                        .or(orderProduct.orderStatusCode.statusCodeName
                            .eq(DELIVERY_COMPLETE.getValue()))
                        .or(orderProduct.orderStatusCode.statusCodeName
                            .eq(DELIVERY_PREPARING.getValue())),
                    orderProduct.no)
                    .otherwise((Integer) null)
                    .count().as("orderSaleCnt"),
                orderProduct.amount.sum().as("totalAmount"),
                queryCaseLong(orderProduct.orderStatusCode.statusCodeName
                        .ne(PURCHASE_CONFIRMATION.getValue())
                        .or(orderProduct.orderStatusCode.statusCodeName
                            .ne(DELIVERY_PREPARING.getValue()))
                        .or(orderProduct.orderStatusCode.statusCodeName
                            .ne(SHIPPING.getValue()))
                        .or(orderProduct.orderStatusCode.statusCodeName
                            .ne(DELIVERY_COMPLETE.getValue())),
                    orderProduct.cancellationAmount)
                    .otherwise(0L)
                    .sum().as("cancelAmount"),
                (new CaseBuilder()
                    .when(orderProduct.orderStatusCode.statusCodeName
                        .eq(DELIVERY_PREPARING.getValue())
                        .or(orderProduct.orderStatusCode.statusCodeName.eq(PURCHASE_CONFIRMATION.getValue()))
                        .or(orderProduct.orderStatusCode.statusCodeName.eq(DELIVERY_COMPLETE.getValue()))
                        .or(orderProduct.orderStatusCode.statusCodeName.eq(SHIPPING.getValue())))
                    .then(orderProduct.amount)
                    .otherwise(0L))
                    .sum().as("orderSaleAmount")))
            .groupBy(order.orderDatetime)
            .fetch();
    }

    private CaseBuilder.Cases<Integer, NumberExpression<Integer>> queryCaseInteger(
        BooleanExpression statusCodeNo,
        NumberPath<Integer> orderProduct) {
        return new CaseBuilder()
            .when(statusCodeNo)
            .then(orderProduct);
    }

    private CaseBuilder.Cases<Long, NumberExpression<Long>> queryCaseLong(
        BooleanExpression statusCodeNo,
        NumberPath<Long> orderProduct) {
        return new CaseBuilder()
            .when(statusCodeNo)
            .then(orderProduct);
    }

    @Override
    public Optional<OrderProductDetailResponseDto> findOrderProductDetail(Integer orderProductNo,
                                                                          Integer memberNo) {
        QOrder order = QOrder.order;
        QProduct product = QProduct.product;
        QMember member = QMember.member;
        QStatusCode statusCode = QStatusCode.statusCode;
        QOrderProduct orderProduct = QOrderProduct.orderProduct;


        return Optional.ofNullable(from(orderProduct)
            .innerJoin(orderProduct.order, order)
            .innerJoin(order.member, member)
            .innerJoin(orderProduct.product, product)
            .innerJoin(orderProduct.orderStatusCode, statusCode)
            .select(Projections.constructor(OrderProductDetailResponseDto.class,
                product.no.as("productNo"),
                order.no.as("orderNo"),
                product.name.as("productName"),
                order.totalOrderAmount,
                orderProduct.orderStatusCode.statusCodeName.as("orderProductStatus"),
                orderProduct.trackingNo,
                product.color,
                product.manufacturer,
                product.manufacturerCountry,
                product.seller,
                product.importer,
                product.qualityAssuranceStandard,
                product.explanation,
                member.memberNo))
            .where(orderProduct.no.eq(orderProductNo).and(member.memberNo.eq(memberNo)))
            .fetchOne());

    }

    @Override
    public Page<OrderProductResponseDto> findAllOrdersByMemberNo(Integer memberNo, Pageable pageable) {
        QOrderProduct orderProduct = QOrderProduct.orderProduct;
        QProduct product = QProduct.product;
        QStatusCode statusCode = QStatusCode.statusCode;
        QOrder order = QOrder.order;

        JPQLQuery<OrderProductResponseDto> query = from(orderProduct)
            .innerJoin(orderProduct.order, order)
            .innerJoin(orderProduct.product, product)
            .innerJoin(orderProduct.orderStatusCode, statusCode)
            .select(Projections.constructor(OrderProductResponseDto.class,
                orderProduct.no.as("orderProductNo"),
                order.no.as("orderNo"),
                product.name.as("productName"),
                order.totalOrderAmount,
                order.orderDatetime,
                order.receiptName,
                order.receiptPhoneNumber,
                orderProduct.orderStatusCode.statusCodeName.as("orderStatus"),
                orderProduct.trackingNo));

        List<OrderProductResponseDto> content = query
            .where(order.member.memberNo.eq(memberNo))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();


        return PageableExecutionUtils.getPage(content, pageable,
            query::fetchCount);
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
                order.addressList.addressDetail,
                order.receiptPhoneNumber,
                orderProduct.no))
            .fetchOne();

        return Optional.ofNullable(deliveryDto);
    }
}
