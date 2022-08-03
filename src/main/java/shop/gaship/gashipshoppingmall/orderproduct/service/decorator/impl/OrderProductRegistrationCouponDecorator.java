package shop.gaship.gashipshoppingmall.orderproduct.service.decorator.impl;

import org.springframework.stereotype.Component;
import shop.gaship.gashipshoppingmall.membercoupon.entity.MemberCoupon;
import shop.gaship.gashipshoppingmall.membercoupon.excpetion.MemberCouponNotFoundException;
import shop.gaship.gashipshoppingmall.membercoupon.repository.MemberCouponRepository;
import shop.gaship.gashipshoppingmall.orderproduct.entity.OrderProduct;
import shop.gaship.gashipshoppingmall.orderproduct.repository.OrderProductRepository;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.exception.StatusCodeNotFoundException;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;
import shop.gaship.gashipshoppingmall.statuscode.status.CouponStatus;

/**
 * 주문 상세품목을 쿠폰을 적용하여 저장하며, 저장 후에 재고를 삭감할 기능,
 * 멤버의 쿠폰의 상태를 변경 할 기능을 가진 데코레이터 클래스입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Component
public class OrderProductRegistrationCouponDecorator
    extends OrderProductRegistrationBaseDecorator {
    private static final ThreadLocal<MemberCoupon> MEMBER_COUPON_THREAD_LOCAL;
    private final MemberCouponRepository memberCouponRepository;
    private final StatusCodeRepository statusCodeRepository;

    static {
        MEMBER_COUPON_THREAD_LOCAL = new ThreadLocal<>();
    }

    /**
     * 주문 상세품목 쿠폰 적용 저장 데코레이터의 생성자입니다.
     *
     * @param orderProductRepository 주문상세품목의 저장을 위한 Repository입니다.
     * @param memberCouponRepository 멤버가 적용 할 쿠폰을 조회하기 위한 Repository입니다.
     * @param statusCodeRepository   주문 상세품목이 가질 기본 상태를 조회하기위한 StatusCode Repository입니다.
     */
    public OrderProductRegistrationCouponDecorator(OrderProductRepository orderProductRepository,
                                                   MemberCouponRepository memberCouponRepository,
                                                   StatusCodeRepository statusCodeRepository) {
        super(orderProductRepository);
        this.memberCouponRepository = memberCouponRepository;
        this.statusCodeRepository = statusCodeRepository;
    }

    /**
     * 멤버의 쿠폰을 적용하기 위해 스레드 로컬에 저장합니다.
     *
     * @param couponNo 적용할 쿠폰의 고유번호입니다.
     */
    public void applyMemberCoupon(Integer couponNo) {
        MemberCoupon memberCoupon = memberCouponRepository.findById(couponNo)
            .orElseThrow(MemberCouponNotFoundException::new);
        MEMBER_COUPON_THREAD_LOCAL.set(memberCoupon);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * 스레드 로컬에 적용할 쿠폰을 꺼내어 사용합니다.
     *
     * @param orderProduct 저장할 주문 상세품목입니다.
     * @return {@inheritDoc}
     */
    @Override
    public OrderProduct save(OrderProduct orderProduct) {
        MemberCoupon memberCoupon = MEMBER_COUPON_THREAD_LOCAL.get();
        orderProduct.applyMemberCoupon(memberCoupon);

        return super.save(orderProduct);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * 이 후, 쿠폰을 사용 상태로 만듭니다.
     *
     * @param product 삭감 할 품목 객체입니다.
     */
    @Override
    public void cleanUpStock(Product product) {
        StatusCode couponUsedStatus =
            statusCodeRepository.findByStatusCodeName(CouponStatus.USED.getValue())
            .orElseThrow(StatusCodeNotFoundException::new);

        MemberCoupon memberCoupon = MEMBER_COUPON_THREAD_LOCAL.get();
        memberCoupon.updateMemberCouponStatus(couponUsedStatus);
        MEMBER_COUPON_THREAD_LOCAL.remove();

        super.cleanUpStock(product);
    }
}
