package shop.gaship.gashipshoppingmall.producttag.repository.impl;

import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.gaship.gashipshoppingmall.producttag.entity.ProductTag;
import shop.gaship.gashipshoppingmall.producttag.entity.QProductTag;
import shop.gaship.gashipshoppingmall.producttag.repository.custom.ProductTagRepositoryCustom;

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
}
