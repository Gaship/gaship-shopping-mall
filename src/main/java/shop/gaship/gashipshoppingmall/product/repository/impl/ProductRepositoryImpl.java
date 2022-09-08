package shop.gaship.gashipshoppingmall.product.repository.impl;


import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.util.StringUtils;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import shop.gaship.gashipshoppingmall.category.entity.Category;
import shop.gaship.gashipshoppingmall.category.entity.QCategory;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductRequestViewDto;
import shop.gaship.gashipshoppingmall.product.dto.response.ProductAllInfoResponseDto;
import shop.gaship.gashipshoppingmall.product.dto.response.ProductByCategoryResponseDto;
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

    QStatusCode statusCode = QStatusCode.statusCode;

    public ProductRepositoryImpl() {
        super(Product.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductByCategoryResponseDto> findProductByCategory(Integer categoryNo, Long min, Long max, Pageable pageable) {
        QCategory lower = new QCategory("lower");
        QStatusCode statusCode = QStatusCode.statusCode;
        // 필요정보 상품이름, 상품번호, 상품가격
        List<Category> lowerList = from(category)
            .innerJoin(category.lowerCategories, lower)
            .where(lower.upperCategory.no.eq(categoryNo))
            .select(lower)
            .fetch();

        if (lowerList.isEmpty()) {
            return Page.empty();
        }

        if (lowerList.get(0).getLevel() == 3) {
            JPQLQuery<ProductByCategoryResponseDto> query = from(product)
                .innerJoin(product.category, category)
                .innerJoin(product.salesStatus, statusCode)
                .select(Projections.constructor(ProductByCategoryResponseDto.class,
                    product.no.as("productNo"),
                    product.name.as("productName"),
                    product.amount
                ))
                .where(product.category.no.in(lowerList
                            .stream()
                            .map(Category::getNo)
                            .collect(Collectors.toUnmodifiableList()))
                        .and(statusCode.statusCodeName.ne(SalesStatus.HIDING.getValue())),
                    eqPrice(min, max))
                .distinct();

            List<ProductByCategoryResponseDto> content = query
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
            return PageableExecutionUtils.getPage(content, pageable, query::fetchCount);
        }

        List<Integer> lastCategory = from(category)
            .select(category.no)
            .where(category.upperCategory.no.in(lowerList.stream()
                    .map(Category::getNo)
                    .collect(Collectors.toUnmodifiableList()))
                .and(category.level.eq(3)))
            .fetch();

        if (lastCategory.isEmpty()) {
            return Page.empty();
        }

        JPQLQuery<ProductByCategoryResponseDto> query = from(product)
            .innerJoin(product.category, category)
            .innerJoin(product.salesStatus, statusCode)
            .select(Projections.constructor(ProductByCategoryResponseDto.class,
                product.no.as("productNo"),
                product.name.as("productName"),
                product.amount
            ))
            .where(product.category.no.in(lastCategory)
                    .and(statusCode.statusCodeName.ne(SalesStatus.HIDING.getValue())),
                eqPrice(min, max))
            .distinct();

        List<ProductByCategoryResponseDto> content = query
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .fetch();

        return PageableExecutionUtils.getPage(content, pageable, query::fetchCount);
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
                product.salesStatus.statusCodeName.as("salesStatus"),
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
            .innerJoin(product.salesStatus, statusCode)
            .innerJoin(product.deliveryType, statusCode)
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
