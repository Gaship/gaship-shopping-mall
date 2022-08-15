package shop.gaship.gashipshoppingmall.orderproduct.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.gaship.gashipshoppingmall.order.entity.Order;
import shop.gaship.gashipshoppingmall.orderproduct.dto.OrderProductCancellationFailDto;
import shop.gaship.gashipshoppingmall.orderproduct.dto.OrderProductSpecificDto;
import shop.gaship.gashipshoppingmall.orderproduct.dto.OrderProductStatusCancelDto;
import shop.gaship.gashipshoppingmall.orderproduct.dto.OrderProductStatusCancelDto.CancelOrderInfo;
import shop.gaship.gashipshoppingmall.orderproduct.dto.OrderProductStatusChangeDto;
import shop.gaship.gashipshoppingmall.orderproduct.entity.OrderProduct;
import shop.gaship.gashipshoppingmall.orderproduct.event.CouponUseCancelEvent;
import shop.gaship.gashipshoppingmall.orderproduct.event.CouponUseEvent;
import shop.gaship.gashipshoppingmall.orderproduct.exception.InvalidOrderCancellationHistoryNo;
import shop.gaship.gashipshoppingmall.orderproduct.exception.OrderProductNotFoundException;
import shop.gaship.gashipshoppingmall.orderproduct.repository.OrderProductRepository;
import shop.gaship.gashipshoppingmall.orderproduct.service.OrderProductService;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.product.exception.NoMoreProductException;
import shop.gaship.gashipshoppingmall.product.exception.ProductNotFoundException;
import shop.gaship.gashipshoppingmall.product.repository.ProductRepository;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.exception.InvalidOrderStatusException;
import shop.gaship.gashipshoppingmall.statuscode.exception.StatusCodeNotFoundException;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;
import shop.gaship.gashipshoppingmall.statuscode.status.OrderStatus;

/**
 * 주문 상품상세 요구사항 명세를 구현하는 클래스입니다.
 *
 * @author 김민수
 * @see OrderProductService
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class OrderProductServiceImpl implements OrderProductService {
    public static final Integer DEFAULT_WARRANTY = 12; // 1년 기본 보증
    private final ProductRepository productRepository;
    private final StatusCodeRepository statusCodeRepository;
    private final OrderProductRepository orderProductRepository;
    private final ApplicationEventPublisher applicationEventPublisher;


    /**
     * {@inheritDoc}
     *
     * @param order         어느 주문에 저장할 것인지에 대한 주문 엔티티 객체입니다.
     * @param orderProducts 구매자가 구매한 주문 상품의 고유번호, 쿠폰 고유번호등 상품의 기본주문 정보를 담은 객체의
     *                      리스트 객체입니다.
     */
    @Transactional
    @Override
    public void registerOrderProduct(Order order, List<OrderProductSpecificDto> orderProducts) {
        StatusCode deliveryPrepending =
            statusCodeRepository.findByStatusCodeName(OrderStatus.DELIVERY_PREPARING.getValue())
                .orElseThrow(StatusCodeNotFoundException::new);

        List<OrderProduct> orderProductsForSave = orderProducts.stream()
            .map(orderProductSpecific ->
                makeOrderProduct(order, deliveryPrepending, orderProductSpecific))
            .collect(Collectors.toUnmodifiableList());

        List<OrderProduct> savedOrderProducts =
            orderProductRepository.saveAll(orderProductsForSave);

        decreaseProductStock(savedOrderProducts);

        // 쿠폰 사용 완료 처리 요청 발신
        boolean isUsedCoupon = orderProducts.stream()
            .anyMatch(orderProductSpecificDto -> orderProductSpecificDto.getCouponAmount() > 0);

        if (isUsedCoupon) {
            List<Integer> couponNos = orderProducts.stream()
                .filter(orderProductSpecificDto -> orderProductSpecificDto.getCouponAmount() > 0)
                .map(OrderProductSpecificDto::getCouponNo)
                .collect(Collectors.toUnmodifiableList());

            applicationEventPublisher.publishEvent(new CouponUseEvent(couponNos));
        }
    }

    /**
     * 주문 상품을 생성합니다.
     *
     * @param order                 주문상품의 주문입니다.
     * @param statusCode            주문 초기 상태입니다.
     * @param orderProductSpecific  주문 상품에 등록될 정보입니다.
     * @return 새로 생성할 주문 상품입니다.
     */
    private OrderProduct makeOrderProduct(Order order, StatusCode statusCode,
                                          OrderProductSpecificDto orderProductSpecific) {
        Long amount = orderProductSpecific.getAmount();
        LocalDate hopeDate = orderProductSpecific.getHopeDate();
        Integer couponNo = orderProductSpecific.getCouponNo();

        Product product = productRepository.findById(orderProductSpecific.getProductNo())
            .orElseThrow(ProductNotFoundException::new);

        return OrderProduct.builder()
            .order(order)
            .product(product)
            .amount(amount)
            .warrantyExpirationDate(LocalDate.now().plusMonths(DEFAULT_WARRANTY))
            .hopeDate(hopeDate)
            .memberCouponNo(couponNo)
            .orderStatusCode(statusCode)
            .build();
    }

    /**
     * 주문시 주문 상품의 재고를 감소시킵니다.
     *
     * @param savedOrderProducts 생성된 주문 상품들입니다.
     */
    private void decreaseProductStock(List<OrderProduct> savedOrderProducts) {
        savedOrderProducts.forEach(orderProduct -> {
            Product product = orderProduct.getProduct();
            int afterStock = product.getStockQuantity() - 1;
            if (afterStock < 0) {
                throw new NoMoreProductException();
            }
            product.updateStockQuantity(afterStock);
        });
    }

    /**
     * {@inheritDoc}
     *
     * @param orderProductStatusChangeDto 주문 상태를 교환상태로 바꿀 주문 상품입니다.
     * @throws InvalidOrderStatusException 주문 상태가 배송중, 배송완료가 아닌 경우 예외를 던집니다.
     */
    @Transactional
    @Override
    public void updateOrderProductStatusToChange(
        OrderProductStatusChangeDto orderProductStatusChangeDto) {
        StatusCode changeStatus =
            statusCodeRepository.findByStatusCodeName(OrderStatus.EXCHANGE_RECEPTION.getValue())
                .orElseThrow(StatusCodeNotFoundException::new);

        orderProductStatusChangeDto.getOrderProductNos().forEach(orderProductNo -> {
            OrderProduct orderProduct = orderProductRepository.findById(orderProductNo)
                .orElseThrow(OrderProductNotFoundException::new);
            String statusCodeName = orderProduct.getOrderStatusCode().getStatusCodeName();
            OrderStatus.checkChangeableOrder(statusCodeName);

            orderProduct.updateOrderProductStatus(changeStatus);
        });
    }

    /**
     * {@inheritDoc}
     *
     * @param orderProductStatusCancelDto   주문 취소, 반품 상품의 정보입니다.
     * @throws InvalidOrderStatusException 주문 상태가 배송준비, 배송중, 배송완료가 아닌 경우 예외를 던집니다.
     */
    @Transactional
    @Override
    public void updateOrderProductStatusToCancel(
        OrderProductStatusCancelDto orderProductStatusCancelDto) {
        StatusCode cancellationCode = statusCodeRepository
                .findByStatusCodeName(OrderStatus.CANCEL_COMPLETE.getValue())
                .orElseThrow(StatusCodeNotFoundException::new);

        orderProductStatusCancelDto.getCancelOrderInfos()
            .forEach(cancelOrderInfo -> {
                OrderProduct orderProduct =
                    orderProductRepository.findById(cancelOrderInfo.getCancelOrderProductNo())
                        .orElseThrow(OrderProductNotFoundException::new);
                String statusCodeName = orderProduct.getOrderStatusCode().getStatusCodeName();
                OrderStatus.checkCancelableOrder(statusCodeName);

                orderProduct.updateCancellation(
                    cancellationCode,
                    cancelOrderInfo.getCancelAmount(),
                    orderProductStatusCancelDto.getCancelReason(),
                    orderProductStatusCancelDto.getPaymentCancelHistoryNo(),
                    LocalDateTime.now()
                );
            });

        List<OrderProduct> orderProducts = orderProductRepository
                .findAllById(convertCancellationProductNos(orderProductStatusCancelDto));

        boolean isUsedCoupon = orderProducts.stream()
            .anyMatch(orderProduct -> Objects.nonNull(orderProduct.getMemberCouponNo()));

        if (isUsedCoupon) {
            List<Integer> couponNos = orderProducts.stream()
                .map(OrderProduct::getMemberCouponNo)
                .filter(Objects::nonNull)
                .collect(Collectors.toUnmodifiableList());

            applicationEventPublisher.publishEvent(new CouponUseCancelEvent(couponNos));
        }
    }

    /**
     * 주문 상품 취소를 하기위한 상품의 주문 상품 고유번호들을 추출하는 메서드입니다.
     *
     * @param orderProductStatusCancelDto 주문 취소정보가 담긴 객체입니다.
     * @return 취소 주문 상품들의 고유번호가 담긴 List 객체입니다.
     */
    private List<Integer> convertCancellationProductNos(
        OrderProductStatusCancelDto orderProductStatusCancelDto) {
        return orderProductStatusCancelDto.getCancelOrderInfos().stream()
            .map(CancelOrderInfo::getCancelOrderProductNo)
            .collect(Collectors.toUnmodifiableList());
    }

    /**
     * {@inheritDoc}
     *
     * @param orderProductCancellationFailDto 주문 취소 상품들의 정보입니다.
     * @throws InvalidOrderStatusException 주문 상태가 취소가 아닌 경우 예외를 던집니다.
     */
    @Transactional
    @Override
    public void restoreOrderProduct(
        OrderProductCancellationFailDto orderProductCancellationFailDto) {
        Integer cancellationHistoryNo = orderProductCancellationFailDto.getPaymentCancelHistoryNo();
        List<Integer> orderProductNos = orderProductCancellationFailDto.getRestoreOrderProductNos();
        List<OrderProduct> orderProducts = orderProductRepository.findAllById(orderProductNos);
        StatusCode deliveryPendingStatus = statusCodeRepository.findByStatusCodeName(
            OrderStatus.DELIVERY_PREPARING.getValue())
            .orElseThrow(StatusCodeNotFoundException::new);

        orderProducts.forEach(orderProduct -> {
            String statusCodeName = orderProduct.getOrderStatusCode().getStatusCodeName();
            OrderStatus.checkRecoverableOrder(statusCodeName);

            if (!Objects.equals(orderProduct.getPaymentCancelHistoryNo(), cancellationHistoryNo)) {
                throw new InvalidOrderCancellationHistoryNo();
            }

            orderProduct.updateCancellation(
                deliveryPendingStatus,
                0L,
                null,
                null,
                null
            );
        });

        boolean isUsedCoupon = orderProducts.stream()
            .anyMatch(orderProduct -> Objects.nonNull(orderProduct.getMemberCouponNo()));

        if (isUsedCoupon) {
            List<Integer> couponNos = orderProducts.stream()
                .map(OrderProduct::getMemberCouponNo)
                .filter(Objects::nonNull)
                .collect(Collectors.toUnmodifiableList());

            applicationEventPublisher.publishEvent(new CouponUseEvent(couponNos));
        }
    }
}
