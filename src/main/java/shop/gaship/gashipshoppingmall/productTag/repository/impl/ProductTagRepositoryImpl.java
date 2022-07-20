package shop.gaship.gashipshoppingmall.productTag.repository.impl;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.gaship.gashipshoppingmall.productTag.entity.ProductTag;
import shop.gaship.gashipshoppingmall.productTag.entity.QProductTag;
import shop.gaship.gashipshoppingmall.productTag.repository.custom.ProductTagRepositoryCustom;
import shop.gaship.gashipshoppingmall.tag.entity.QTag;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;

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
    public List<Tag> findTagByProductNo(Integer productNo) {
        QProductTag productTag = QProductTag.productTag;
        QTag tag = QTag.tag;

        return from(productTag)
                .innerJoin(productTag.tag, tag)
                .where(productTag.product.no.eq(productNo))
                .select(tag)
                .fetch();
    }
}