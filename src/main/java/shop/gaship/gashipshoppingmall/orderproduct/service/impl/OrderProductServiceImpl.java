package shop.gaship.gashipshoppingmall.orderproduct.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import shop.gaship.gashipshoppingmall.order.dto.request.OrderProductSpecificDto;
import shop.gaship.gashipshoppingmall.order.entity.Order;
import shop.gaship.gashipshoppingmall.orderproduct.dto.OrderProductBenefit;
import shop.gaship.gashipshoppingmall.orderproduct.entity.OrderProduct;
import shop.gaship.gashipshoppingmall.orderproduct.service.OrderProductService;
import shop.gaship.gashipshoppingmall.orderproduct.service.decorator.impl.OrderProductRegistrationBaseDecorator;
import shop.gaship.gashipshoppingmall.orderproduct.service.decorator.impl.OrderProductRegistrationCouponDecorator;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.product.exception.ProductNotFoundException;
import shop.gaship.gashipshoppingmall.product.repository.ProductRepository;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.exception.StatusCodeNotFoundException;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;
import shop.gaship.gashipshoppingmall.statuscode.status.OrderStatus;

/**
 * 주문 상품상세 요구사항 명세를 구현하는 클래스입니다.
 *
 * @see OrderProductService
 * @author 김민수
 * @since 1.0
 */
@Service
public class OrderProductServiceImpl implements OrderProductService {
    private final OrderProductRegistrationBaseDecorator orderProductBaseDecorator;
    private final OrderProductRegistrationCouponDecorator orderProductCouponDecorator;
    private final ProductRepository productRepository;
    private final StatusCodeRepository statusCodeRepository;

    /**
     * OrderProductServiceImpl를 생성하는 인자입니다.
     *
     * @param orderProductBaseDecorator orderProduct 저장 Decorator
     * @param orderProductCouponDecorator 쿠폰 적용 orderProduct 저장 Decorator
     * @param productRepository 주문 할 물건들의 정보를 아는 repository
     * @param statusCodeRepository 주문 준비 상태 ENnied
     */
    public OrderProductServiceImpl(
                                    @Qualifier("orderProductBaseDecorator")
                                    OrderProductRegistrationBaseDecorator orderProductBaseDecorator,
                                    @Qualifier("orderProductCouponDecorator")
                                   OrderProductRegistrationCouponDecorator orderProductCouponDecorator,
                                   ProductRepository productRepository,
                                   StatusCodeRepository statusCodeRepository) {
        this.orderProductBaseDecorator = orderProductBaseDecorator;
        this.orderProductCouponDecorator = orderProductCouponDecorator;
        this.productRepository = productRepository;
        this.statusCodeRepository = statusCodeRepository;
    }


    /**
     * {@inheritDoc}
     *
     * @param order                 어느 주문에 저장할 것인지에 대한 주문 엔티티 객체입니다.
     * @param orderProductSpecifics 상품의 고유번호, 쿠폰 고유번호, 수리설치 희망일자들을 담은 객체의 리스트 객체입니다.
     */
    @Override
    public void registerOrderProduct(Order order,
                                     List<OrderProductSpecificDto> orderProductSpecifics) {
        StatusCode deliveryPeriod =
            statusCodeRepository.findByStatusCodeName(OrderStatus.DELIVERY_PREPARING.getValue())
                .orElseThrow(StatusCodeNotFoundException::new);

        orderProductSpecifics.forEach(
            orderProductSpecificDto -> registerOrderProductAndCleanUp(deliveryPeriod, order,
                orderProductSpecificDto));
    }

    /**
     * 주문 상품을 저장한 후 재고를 주문한 수량만큼 반영하는 메서드입니다.
     *
     * @param deliverPreparingStatus  주문 상품의 기본 상태입니다.
     * @param order                   주문 엔티티 객체입니다.
     * @param orderProductSpecificDto 주문 상품을 저장에 필요한 상품, 쿠폰의 고유번호 등을 보유한 객체입니다.
     */
    private void registerOrderProductAndCleanUp(StatusCode deliverPreparingStatus, Order order,
                                      OrderProductSpecificDto orderProductSpecificDto) {
        Product product = productRepository.findById(orderProductSpecificDto.getProductNo())
            .orElseThrow(ProductNotFoundException::new);
        OrderProduct orderProduct =
            OrderProduct.builder()
                .order(order)
                .product(product)
                .amount(product.getAmount())
                .orderStatusCode(deliverPreparingStatus)
                .warrantyExpirationDate(LocalDate.now().plusYears(1L))
                .hopeDate(orderProductSpecificDto.getHopeDate()).build();

        OrderProductBenefit orderProductBenefit =
            saveOrderProductAndApplyBenefit(orderProductSpecificDto, orderProduct);
        cleanUpStock(product, orderProductBenefit.getMemberCouponNo());
    }

    /**
     * 주문 상품을 저장하고 혜택이 있다면 적용시킵니다.
     *
     * @param orderProductSpecificDto 주문 상품을 저장에 필요한 상품, 쿠폰의 고유번호 등을 보유한 객체입니다.
     * @param orderProduct 저장 할 주문 상품입니다.
     * @return 적용된 혜택들의 정보가 담긴 객체를 반환합니다.
     */
    private OrderProductBenefit saveOrderProductAndApplyBenefit(
        OrderProductSpecificDto orderProductSpecificDto, OrderProduct orderProduct) {
        OrderProduct orderedProduct = orderProductBaseDecorator.save(orderProduct);

        Integer memberCouponNo = orderProductSpecificDto.getCouponNo();

        if (Objects.nonNull(memberCouponNo)) { // 쿠폰 적용시
            orderProductCouponDecorator.applyMemberCoupon(memberCouponNo);
            orderProductCouponDecorator.save(orderedProduct);
        }
        return new OrderProductBenefit(memberCouponNo);
    }

    /**
     * 주문한 제품의 수량만큼 재고를 정리합니다.
     *
     * @param product 주문한 제품 엔티티 객체 입니다.
     * @param memberCouponNo 적용한 쿠폰 번호입니다. (null 가능)
     */
    private void cleanUpStock(Product product, @Nullable Integer memberCouponNo) {
        if (Objects.nonNull(memberCouponNo)) {
            orderProductCouponDecorator.cleanUpStock(product); // 재고 삭감 + 쿠폰 사용상태 변경
        } else {
            orderProductBaseDecorator.cleanUpStock(product); // 재고 삭감
        }
    }
}
