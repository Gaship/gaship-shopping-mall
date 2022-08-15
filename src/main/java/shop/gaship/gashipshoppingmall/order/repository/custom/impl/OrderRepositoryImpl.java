package shop.gaship.gashipshoppingmall.order.repository.custom.impl;

import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import shop.gaship.gashipshoppingmall.addresslist.entity.QAddressList;
import shop.gaship.gashipshoppingmall.order.dto.response.OrderCancelResponseDto;
import shop.gaship.gashipshoppingmall.order.dto.response.OrderDetailResponseDto;
import shop.gaship.gashipshoppingmall.order.dto.response.OrderListResponseDto;
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


    @Override
    public Page<OrderDetailResponseDto> findOrderDetails(Integer memberNo,
                                                         Integer orderNo,
                                                         Pageable pageable) {
        //상세
        //address_list, 주문일자, 실수령자명, 실주령자 전화번호, 배송요청사항, 총 구매가격,
        //order_product_list, order_product_no, 제품명, 배송형태(status), 주문상품금액, 운송장번호
        //설치일 경우에는 날짜를 넣는데 일반배송일 경우는 넣지않는다.(null)

        QOrder order = QOrder.order;
        QAddressList addressList = QAddressList.addressList;
        QOrderProduct orderProduct = QOrderProduct.orderProduct;
        QStatusCode statusCode = QStatusCode.statusCode;

        List<OrderDetailResponseDto> content = from(order)
            .innerJoin(order.orderProducts, orderProduct)
            .innerJoin(order.addressList, addressList)
            .innerJoin(order.addressList.statusCode, statusCode)
            .where(order.member.memberNo.eq(memberNo)
                .and(order.no.eq(orderNo)))
            .select(Projections.constructor(OrderDetailResponseDto.class,
                order.addressList.statusCode.statusCodeName,
                order.orderDatetime,
                order.receiptName,
                order.receiptPhoneNumber,
                order.deliveryRequest,
                order.totalOrderAmount,
                order.addressList.address,
                order.addressList.zipCode,
                orderProduct.amount,
                orderProduct.trackingNo,
                orderProduct.hopeDate))
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .fetch();

        return PageableExecutionUtils.getPage(content, pageable, content::size);

    }


    @Override
    public Page<OrderListResponseDto> findAllOrders(Integer memberNo, Pageable pageable) {
        //내 order_no, 주소지(address_list), 주문일자, 실수령자명, 실주령자 전화번호, 배송요청사항(delivery request)
        //총 구매가격 --> 집계함수 쓰라는 건가?
        //member 를 통해서
        QOrder order = QOrder.order;
        QAddressList addressList = QAddressList.addressList;

        List<OrderListResponseDto> content = from(order)
            .innerJoin(order.addressList, addressList)
            .where(order.member.memberNo.eq(memberNo))
            .select(Projections.constructor(OrderListResponseDto.class,
                order.no.as("orderNo"),
                order.addressList.address,
                order.orderDatetime,
                order.receiptName,
                order.receiptPhoneNumber,
                order.deliveryRequest,
                order.totalOrderAmount))
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .fetch();

        return PageableExecutionUtils.getPage(content, pageable, content::size);
    }

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
