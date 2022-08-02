package shop.gaship.gashipshoppingmall.productreview.repository.impl;

import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import shop.gaship.gashipshoppingmall.member.entity.QMember;
import shop.gaship.gashipshoppingmall.order.entity.QOrder;
import shop.gaship.gashipshoppingmall.orderproduct.entity.QOrderProduct;
import shop.gaship.gashipshoppingmall.product.entity.QProduct;
import shop.gaship.gashipshoppingmall.productreview.dto.response.ProductReviewResponseDto;
import shop.gaship.gashipshoppingmall.productreview.entity.ProductReview;
import shop.gaship.gashipshoppingmall.productreview.entity.QProductReview;
import shop.gaship.gashipshoppingmall.productreview.repository.custom.ProductReviewRepositoryCustom;

/**
 * 설명작성란
 *
 * @author : 김보민
 * @see org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
 * @see shop.gaship.gashipshoppingmall.productreview.repository.custom.ProductReviewRepositoryCustom
 * @since 1.0
 */
public class ProductReviewRepositoryImpl extends QuerydslRepositorySupport
        implements ProductReviewRepositoryCustom {

    public ProductReviewRepositoryImpl() {
        super(ProductReview.class);
    }

    @Override
    public Page<ProductReviewResponseDto> findAllByProductNo(Integer productNo, Pageable pageable) {
        QProductReview review = QProductReview.productReview;
        QOrderProduct orderProduct = QOrderProduct.orderProduct;
        QOrder order = QOrder.order;
        QMember member = QMember.member;
        QProduct product = QProduct.product;

        List<ProductReviewResponseDto> content = from(review)
                .innerJoin(orderProduct).on(review.orderProduct.no.eq(orderProduct.no))
                .innerJoin(order).on(orderProduct.order.no.eq(order.no))
                .innerJoin(order.member, member)
                .innerJoin(orderProduct.product, product)
                .select(Projections.bean(ProductReviewResponseDto.class,
                        orderProduct.no,
                        order.member.nickname,
                        orderProduct.product.name,
                        review.title,
                        review.content,
                        review.imagePath,
                        review.starScore,
                        review.registerDatetime,
                        review.modifyDatetime
                ))
                .where(review.orderProduct.product.no.eq(productNo))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
        return PageableExecutionUtils.getPage(content, pageable, () -> from(review).fetch().size());
    }
}
