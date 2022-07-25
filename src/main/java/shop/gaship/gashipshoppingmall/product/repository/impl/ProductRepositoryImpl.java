package shop.gaship.gashipshoppingmall.product.repository.impl;


import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.util.StringUtils;
import com.querydsl.jpa.JPAExpressions;
import java.util.List;

import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import shop.gaship.gashipshoppingmall.category.entity.QCategory;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductRequestDto;
import shop.gaship.gashipshoppingmall.product.dto.response.ProductAllInfoResponseDto;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.product.entity.QProduct;
import shop.gaship.gashipshoppingmall.product.repository.custom.ProductRepositoryCustom;
import shop.gaship.gashipshoppingmall.productTag.entity.QProductTag;
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

    @Override
    public Page<ProductAllInfoResponseDto> findProduct(ProductRequestDto requestDto) {

        QCategory upper = new QCategory("upper");
        QCategory top = new QCategory("top");


        List<ProductAllInfoResponseDto> content = productQuery(requestDto)
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
                        product.imageLink1.as("img1"),
                        product.imageLink2.as("img2"),
                        product.imageLink3.as("img3"),
                        product.imageLink4.as("img4"),
                        product.imageLink5.as("img5"),
                        product.explanation,
                        category.level,
                        JPAExpressions.select(upper.name.concat("-").concat(
                                        JPAExpressions.select(top.name)
                                                .where(top.no.eq(upper.upperCategory.no))
                                                .from(top)
                                ).as("upperName"))
                                .where(upper.no.eq(category.upperCategory.no))
                                .from(upper)))
                .distinct()
                .limit(requestDto.getPageable().getPageSize())
                .offset(requestDto.getPageable().getOffset())
                .fetch();

        return PageableExecutionUtils.getPage(content,  requestDto.getPageable(),
                () -> productQuery(requestDto)
                        .fetch()
                        .size());
    }

    private JPQLQuery<Product> productQuery(ProductRequestDto requestDto) {
        return from(product)
                .innerJoin(category)
                .on(product.category.no.eq(category.no))
                .innerJoin(productTag)
                .on(product.productTags.contains(productTag))
                .innerJoin(productTag.tag, tag)
                .where(eqProductName(requestDto.getProductName()),
                        eqProductCode(requestDto.getCode()),
                        eqCategory(requestDto.getCategoryNo()),
                        eqPrice(requestDto.getMinAmount(), requestDto.getMaxAmount()),
                        eqTagNo(requestDto.getTagNo()),
                        eqProductNo(requestDto.getProductNo()),
                        eqStatus(requestDto.getStatusName()));
    }

    private BooleanExpression eqProductNo(Integer productNo) {
        if (productNo <= 0) {
            return null;
        }
        return product.no.eq(productNo);
    }

    private BooleanExpression eqProductName(String name) {
        if (StringUtils.isNullOrEmpty(name)) {
            return null;
        }
        return product.name.contains(name);
    }


    private BooleanExpression eqProductCode(String code) {
        if (StringUtils.isNullOrEmpty(code)) {
            return null;
        }
        return product.code.contains(code);
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
        return tag.tagNo.eq(tagNo);
    }

    private BooleanExpression eqStatus(String statusName) {
        if (StringUtils.isNullOrEmpty(statusName)) {
            return null;
        }
        return product.salesStatus.statusCodeName.eq(statusName);
    }
}
