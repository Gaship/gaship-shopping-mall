package shop.gaship.gashipshoppingmall.orderproduct.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.gaship.gashipshoppingmall.order.entity.Order;
import shop.gaship.gashipshoppingmall.orderproduct.dto.OrderProductSpecificDto;
import shop.gaship.gashipshoppingmall.orderproduct.entity.OrderProduct;
import shop.gaship.gashipshoppingmall.orderproduct.event.CouponUseEvent;
import shop.gaship.gashipshoppingmall.orderproduct.event.OrderProductCancelEvent;
import shop.gaship.gashipshoppingmall.orderproduct.exception.OrderProductNotFoundException;
import shop.gaship.gashipshoppingmall.orderproduct.repository.OrderProductRepository;
import shop.gaship.gashipshoppingmall.orderproduct.service.OrderProductService;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.product.exception.NoMoreProductException;
import shop.gaship.gashipshoppingmall.product.exception.ProductNotFoundException;
import shop.gaship.gashipshoppingmall.product.repository.ProductRepository;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
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

        // 주문 상세 저장 실패
        applicationEventPublisher.publishEvent(new OrderProductCancelEvent(savedOrderProducts));
    }

    private OrderProduct makeOrderProduct(Order order, StatusCode statusCode,
                                          OrderProductSpecificDto orderProductSpecific) {
        Integer additionalWarrantyPeriod = orderProductSpecific.getAdditionalWarrantyPeriod();
        Long amount = orderProductSpecific.getAmount();
        LocalDate hopeDate = orderProductSpecific.getHopeDate();
        Integer couponNo = orderProductSpecific.getCouponNo();

        Product product = productRepository.findById(orderProductSpecific.getProductNo())
            .orElseThrow(ProductNotFoundException::new);

        return OrderProduct.builder()
            .order(order)
            .product(product)
            .amount(amount)
            .warrantyExpirationDate(calculateWarrantyDate(additionalWarrantyPeriod))
            .hopeDate(hopeDate)
            .memberCouponNo(couponNo)
            .orderStatusCode(statusCode)
            .build();
    }

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

    private LocalDate calculateWarrantyDate(Integer additionalWarrantyPeriod) {
        return LocalDate.now().plusYears(1L)
            .plusMonths(additionalWarrantyPeriod);
    }

    @Transactional
    @Override
    public void updateOrderStatus(OrderProduct orderProduct, StatusCode statusCode) {
        orderProduct.updateOrderProductStatus(statusCode);
    }

    @Transactional
    @Override
    public void updateOrderStatusByOrderProductNo(Integer orderProductNo, OrderStatus statusCode,
                                                  Long cancellationAmount) {
        OrderProduct orderProduct = orderProductRepository.findById(orderProductNo)
                .orElseThrow(OrderProductNotFoundException::new);
        StatusCode orderStatusCode =
            statusCodeRepository.findByStatusCodeName(statusCode.getValue())
                .orElseThrow(StatusCodeNotFoundException::new);

        orderProduct.updateCancellation(orderStatusCode, cancellationAmount);
    }
}
