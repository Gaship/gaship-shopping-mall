package shop.gaship.gashipshoppingmall.order.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.gaship.gashipshoppingmall.addresslist.entity.AddressList;
import shop.gaship.gashipshoppingmall.addresslist.exception.NotFoundAddressListException;
import shop.gaship.gashipshoppingmall.addresslist.repository.AddressListRepository;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.order.dto.request.OrderRegisterRequestDto;
import shop.gaship.gashipshoppingmall.order.entity.Order;
import shop.gaship.gashipshoppingmall.order.repository.OrderRepository;
import shop.gaship.gashipshoppingmall.order.service.OrderService;
import shop.gaship.gashipshoppingmall.orderproduct.service.OrderProductService;

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
    private final AddressListRepository addressListRepository;
    private final OrderProductService orderProductService;

    @Transactional
    @Override
    public void insertOrder(OrderRegisterRequestDto orderRequest) {
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
            .deliveryRequest(orderRequest.getDeliveryRequest()).build();

        // 주문 등록
        Order savedOrder = orderRepository.save(order);
        orderProductService.registerOrderProduct(savedOrder,
            orderRequest.getOrderProductSpecific());
    }
}
