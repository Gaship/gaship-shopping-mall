package shop.gaship.gashipshoppingmall.productreview.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import java.util.Objects;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import shop.gaship.gashipshoppingmall.member.entity.QMember;
import shop.gaship.gashipshoppingmall.order.entity.QOrder;
import shop.gaship.gashipshoppingmall.orderproduct.entity.QOrderProduct;
import shop.gaship.gashipshoppingmall.product.entity.QProduct;
import shop.gaship.gashipshoppingmall.productreview.dto.request.ProductReviewViewRequestDto;
import shop.gaship.gashipshoppingmall.productreview.dto.response.ProductReviewResponseDto;
import shop.gaship.gashipshoppingmall.productreview.entity.ProductReview;
import shop.gaship.gashipshoppingmall.productreview.entity.QProductReview;
import shop.gaship.gashipshoppingmall.productreview.repository.custom.ProductReviewRepositoryCustom;

/**
 * 상품평 레퍼지토리 구현체입니다.
 *
 * @author : 김보민
 * @see org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
 * @see shop.gaship.gashipshoppingmall.productreview.repository.custom.ProductReviewRepositoryCustom
 * @since 1.0
 */
public class ProductReviewRepositoryImpl extends QuerydslRepositorySupport
        implements ProductReviewRepositoryCustom {
    QProductReview review = QProductReview.productReview;
    QOrderProduct orderProduct = QOrderProduct.orderProduct;
    QOrder order = QOrder.order;
    QMember member = QMember.member;
    QProduct product = QProduct.product;

    public ProductReviewRepositoryImpl() {
        super(ProductReview.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductReviewResponseDto> findProductReviews(
            ProductReviewViewRequestDto viewRequest) {
        List<ProductReviewResponseDto> content = productReviewQuery(viewRequest)
                .select(Projections.bean(ProductReviewResponseDto.class,
                        orderProduct.no.as("orderProductNo"),
                        order.member.memberNo.as("writerNo"),
                        order.member.nickname.as("writerNickname"),
                        orderProduct.product.name.as("productName"),
                        review.title,
                        review.content,
                        review.starScore,
                        review.registerDatetime.as("registerDateTime"),
                        review.modifyDatetime.as("modifyDateTime")
                ))
                .limit(viewRequest.getPageable().getPageSize())
                .offset(viewRequest.getPageable().getOffset())
                .fetch();
        return PageableExecutionUtils.getPage(content, viewRequest.getPageable(),
                () -> productReviewQuery(viewRequest).fetch().size());
    }

    private JPQLQuery<ProductReview> productReviewQuery(ProductReviewViewRequestDto viewRequest) {
        return from(review)
                .innerJoin(review.orderProduct, orderProduct)
                .innerJoin(orderProduct.order, order)
                .innerJoin(order.member, member)
                .innerJoin(orderProduct.product, product)
                .where(eqOrderProductNo(viewRequest.getOrderProductNo()),
                        eqProductNo(viewRequest.getProductNo()),
                        eqMemberNo(viewRequest.getMemberNo()));
    }

    private BooleanExpression eqOrderProductNo(Integer orderProductNo) {
        return (Objects.nonNull(orderProductNo)) ? review.orderProduct.no.eq(orderProductNo) : null;
    }

    private BooleanExpression eqProductNo(Integer productNo) {
        return (Objects.nonNull(productNo)) ? product.no.eq(productNo) : null;
    }

    private BooleanExpression eqMemberNo(Integer memberNo) {
        return (Objects.nonNull(memberNo)) ? member.memberNo.eq(memberNo) : null;
    }
}
