package shop.gaship.gashipshoppingmall.order.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.gaship.gashipshoppingmall.addresslist.entity.AddressList;
import shop.gaship.gashipshoppingmall.addresslist.exception.NotFoundAddressListException;
import shop.gaship.gashipshoppingmall.addresslist.repository.AddressListRepository;
import shop.gaship.gashipshoppingmall.delivery.event.DeliveryEvent;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.order.dto.request.OrderRegisterRequestDto;
import shop.gaship.gashipshoppingmall.order.dto.response.CancelOrderResponseDto;
import shop.gaship.gashipshoppingmall.order.dto.response.OrderCancelResponseDto;
import shop.gaship.gashipshoppingmall.order.dto.response.OrderPaymentResponseDto;
import shop.gaship.gashipshoppingmall.order.dto.response.OrderResponseDto;
import shop.gaship.gashipshoppingmall.order.entity.Order;
import shop.gaship.gashipshoppingmall.order.exception.OrderNotFoundException;
import shop.gaship.gashipshoppingmall.order.repository.OrderRepository;
import shop.gaship.gashipshoppingmall.order.service.OrderService;
import shop.gaship.gashipshoppingmall.orderproduct.entity.OrderProduct;
import shop.gaship.gashipshoppingmall.orderproduct.event.OrderProductRegisteredEvent;
import shop.gaship.gashipshoppingmall.orderproduct.exception.OrderProductEmptyException;
import shop.gaship.gashipshoppingmall.orderproduct.exception.OrderProductNotFoundException;
import shop.gaship.gashipshoppingmall.orderproduct.repository.OrderProductRepository;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.exception.StatusCodeNotFoundException;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;
import shop.gaship.gashipshoppingmall.statuscode.status.DeliveryType;
import shop.gaship.gashipshoppingmall.statuscode.status.OrderStatus;

/**
 * 주문에 관한 요구사항 정의를 구현하는 클래스입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final OrderProductRepository orderProductRepository;
    private final AddressListRepository addressListRepository;
    private final StatusCodeRepository statusCodeRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * {@inheritDoc}
     *
     * @param orderRequest 주문을 요청하기 위한 데이터가 담긴 객체입니다.
     * @return 저장된 주문 고유번호입니다.
     */
    @Transactional
    @Override
    public Integer insertOrder(OrderRegisterRequestDto orderRequest) {
        AddressList addressList = addressListRepository.findById(orderRequest.getAddressListNo())
            .orElseThrow(NotFoundAddressListException::new);
        Member member = memberRepository.findById(orderRequest.getMemberNo())
            .orElseThrow(MemberNotFoundException::new);

        Order order = Order.builder()
            .addressList(addressList)
            .member(member)
            .receiptName(orderRequest.getReceiverName())
            .receiptPhoneNumber(orderRequest.getReceiverPhoneNo())
            .receiptSubPhoneNumber(orderRequest.getReceiverSubPhoneNo())
            .deliveryRequest(orderRequest.getDeliveryRequest())
            .totalOrderAmount(orderRequest.getTotalAmount())
            .build();

        // 주문 등록
        Order savedOrder = orderRepository.save(order);
        applicationEventPublisher.publishEvent(
            new OrderProductRegisteredEvent(savedOrder, orderRequest.getOrderProducts()));

        return savedOrder.getNo();
    }


    /**
     * {@inheritDoc}
     *
     * @param orderNo 주문 번호입니다.
     * @return 주문 등록이 완료되고 결제를 위한 정보가 담긴 객체입니다.
     */
    @Override
    public OrderResponseDto findOrderForPayments(Integer orderNo) {
        Order order = orderRepository.findById(orderNo)
            .orElseThrow(OrderProductNotFoundException::new);

        String productTitleName = orderProductRepository.findOrderProductsByOrder(order).stream()
            .findFirst()
            .orElseThrow(OrderProductEmptyException::new)
            .getProduct()
            .getName();
        StringBuilder orderNameBuilder = new StringBuilder(productTitleName);
        int orderProductCount = order.getOrderProducts().size() - 1; // 한건은 위에 존재

        if (orderProductCount > 0) {
            orderNameBuilder.append("외 ").append(orderProductCount).append("건");
        }


        return new OrderResponseDto(
            order.getTotalOrderAmount(),
            order.getNo(),
            orderNameBuilder.toString(),
            order.getReceiptName()
        );
    }


    /**
     * {@inheritDoc}
     *
     * @param orderNo    주문 번호입니다.
     * @param paymentKey 결제 번호입니다.
     */
    @Transactional
    @Override
    public void orderPaymentsSuccess(Integer orderNo, String paymentKey) {
        Order order = orderRepository.findById(orderNo)
            .orElseThrow(OrderProductNotFoundException::new);
        StatusCode parcelDeliveryType =
            statusCodeRepository.findByStatusCodeName(DeliveryType.PARCEL.getValue())
                .orElseThrow(StatusCodeNotFoundException::new);
        StatusCode deliveryPreparingType =
                statusCodeRepository.findByStatusCodeName(OrderStatus.DELIVERY_PREPARING.getValue())
                        .orElseThrow(StatusCodeNotFoundException::new);

        order.updateOrderPaymentKey(paymentKey);

        order.getOrderProducts().forEach(orderProduct ->
                    orderProduct.changeOrderStatusCode(deliveryPreparingType));

        boolean hasParcelDeliveryProduct = order.getOrderProducts().stream()
            .anyMatch(orderProduct ->
                Objects.equals(orderProduct.getProduct().getDeliveryType(), parcelDeliveryType));

        if (hasParcelDeliveryProduct) {
            List<Integer> orderProductNos = order.getOrderProducts().stream()
                .filter(orderProduct ->
                    Objects.equals(orderProduct.getProduct().getDeliveryType(), parcelDeliveryType))
                .map(OrderProduct::getNo)
                .collect(Collectors.toUnmodifiableList());
            applicationEventPublisher.publishEvent(new DeliveryEvent(orderProductNos));
        }
    }


    /**
     * {@inheritDoc}
     *
     * @throws MemberNotFoundException 회원이 존재하지않을경우 발생합니다.
     */
    @Override
    public Page<OrderCancelResponseDto> findMemberCancelOrders(Integer memberNo,
                                                               String statusName,
                                                               Pageable pageable) {
        if (memberRepository.findById(memberNo).isEmpty()) {
            throw new MemberNotFoundException();
        }

        return orderRepository.findCancelOrders(memberNo, statusName, pageable);
    }

    @Override
    public CancelOrderResponseDto findOrderForCancelPayment(Integer orderNo) {
        return orderRepository.findOrderForCancel(orderNo)
                .orElseThrow(OrderNotFoundException::new);
    }

    @Override
    public OrderPaymentResponseDto findOrderDetailsForPayment(Integer orderNo) {
        return orderRepository.findOrderForPayment(orderNo)
                .orElseThrow(OrderNotFoundException::new);
    }
}
