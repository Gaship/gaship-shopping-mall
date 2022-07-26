package shop.gaship.gashipshoppingmall.productTag.repository.impl;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.gaship.gashipshoppingmall.productTag.entity.ProductTag;
import shop.gaship.gashipshoppingmall.productTag.entity.QProductTag;
import shop.gaship.gashipshoppingmall.productTag.repository.custom.ProductTagRepositoryCustom;

import java.util.List;

/**
 * QueryDsl 을 작성하기위한 구현 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
public class ProductTagRepositoryImpl extends QuerydslRepositorySupport
        implements ProductTagRepositoryCustom {

    public ProductTagRepositoryImpl() {
        super(ProductTag.class);
    }

    @Override
    public List<String> findTagsByProductNo(Integer productNo) {
        QProductTag productTag = QProductTag.productTag;

        return from(productTag)
                .innerJoin(productTag.tag)
                .where(productTag.product.no.in(productNo))
                .select(productTag.tag.title)
                .fetch();
    }

//    @Override
//    public List<String> findTagProductNo(List<Integer> productNo, Tag tag) {
//        QProductTag productTag = QProductTag.productTag;
//        QTag tagQ = QTag.tag;
//
//        return from(productTag)
//                .where(productTag.product.no.eq(productNo)
//                        .and(productTag.tag.tagNo.eq(tag.getTagNo())))
//                .select(tagQ.title)
//                .fetch();
//    }
}