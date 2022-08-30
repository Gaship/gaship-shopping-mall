package shop.gaship.gashipshoppingmall.product.repository.impl;


import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.util.StringUtils;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.gaship.gashipshoppingmall.category.entity.QCategory;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductRequestViewDto;
import shop.gaship.gashipshoppingmall.product.dto.response.ProductAllInfoResponseDto;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.product.entity.QProduct;
import shop.gaship.gashipshoppingmall.product.repository.custom.ProductRepositoryCustom;
import shop.gaship.gashipshoppingmall.producttag.entity.QProductTag;
import shop.gaship.gashipshoppingmall.tag.entity.QTag;


/**
 * QueryDsl 을 쓰기위한 Repo 구현체입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
public class ProductRepositoryImpl extends QuerydslRepositorySupport
    implements ProductRepositoryCustom {
    QProduct product = QProduct.product;
    QCategory category = QCategory.category;
    QTag tag = QTag.tag;
    QProductTag productTag = QProductTag.productTag;

    public ProductRepositoryImpl() {
        super(Product.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductAllInfoResponseDto> findProduct(ProductRequestViewDto requestDto) {
        QCategory upper = new QCategory("upper");
        QCategory top = new QCategory("top");


        JPQLQuery<ProductAllInfoResponseDto> productAllQuery = productQuery(requestDto)
            .select(Projections.constructor(ProductAllInfoResponseDto.class,
                product.no.as("productNo"),
                product.name.as("productName"),
                product.code.as("productCode"),
                category.name.as("categoryName"),
                product.amount,
                product.registerDatetime.as("dateTime"),
                product.manufacturer,
                product.manufacturerCountry.as("country"),
                product.seller,
                product.importer,
                product.shippingInstallationCost.as("installationCost"),
                product.qualityAssuranceStandard.as("quality"),
                product.color,
                product.stockQuantity.as("quantity"),
                product.explanation,
                category.level,
                product.deliveryType.statusCodeName.as("deliveryType"),
                JPAExpressions.select(upper.name.concat("-").concat(
                        JPAExpressions.select(top.name)
                            .where(top.no.eq(upper.upperCategory.no))
                            .from(top)
                    ).as("upperName")
               )
                    .where(upper.no.eq(category.upperCategory.no))
                    .from(upper)))
                .orderBy(product.registerDatetime.desc())
            .distinct();

        List<ProductAllInfoResponseDto> content = productAllQuery
                .offset(requestDto.getPageable().getOffset())
                .limit(requestDto.getPageable().getPageSize())
                .fetch();

        return new PageImpl<>(content, requestDto.getPageable(), productAllQuery.fetchCount());
    }

    private JPQLQuery<Product> productQuery(ProductRequestViewDto requestDto) {
        return from(product)
            .innerJoin(category)
            .on(product.category.no.eq(category.no))
            .innerJoin(productTag)
            .on(product.productTags.contains(productTag))
            .innerJoin(productTag.tag, tag)
            .where(eqCategory(requestDto.getCategoryNo()),
                eqPrice(requestDto.getMinAmount(), requestDto.getMaxAmount()),
                eqTagNo(requestDto.getTagNo()),
                eqProductNo(requestDto.getProductNo()),
                eqStatus(requestDto.getStatusName()),
                eqProductNos(requestDto.getProductNoList()));
    }

    private BooleanExpression eqProductNos(List<Integer> productNo) {
        if (productNo.isEmpty()) {
            return null;
        }
        return product.no.in(productNo);
    }

    private BooleanExpression eqProductNo(Integer productNo) {
        if (productNo <= 0) {
            return null;
        }
        return product.no.eq(productNo);
    }

    private BooleanExpression eqCategory(Integer categoryNo) {
        if (categoryNo <= 0) {
            return null;
        }
        return category.no.eq(categoryNo);
    }

    private BooleanExpression eqPrice(Long minAmount, Long maxAmount) {
        if (minAmount < 0 || maxAmount <= 0) {
            return null;
        }
        return product.amount.between(minAmount, maxAmount);
    }

    private BooleanExpression eqTagNo(Integer tagNo) {
        if (tagNo <= 0) {
            return null;
        }
        return product.productTags.any().tag.tagNo.eq(tagNo);
    }

    private BooleanExpression eqStatus(String statusName) {
        if (StringUtils.isNullOrEmpty(statusName)) {
            return null;
        }
        return product.salesStatus.statusCodeName.eq(statusName);
    }
}
