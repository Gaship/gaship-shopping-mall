package shop.gaship.gashipshoppingmall.product.repository.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.gaship.gashipshoppingmall.product.dto.response.ProductResponseDto;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.product.entity.QProduct;
import shop.gaship.gashipshoppingmall.product.repository.custom.ProductRepositoryCustom;

import java.util.List;
import java.util.Optional;

/**
 * QueryDsl 을 쓰기위한 Repo 구현체입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
public class ProductRepositoryImpl extends QuerydslRepositorySupport
        implements ProductRepositoryCustom {
    public ProductRepositoryImpl() {
        super(Product.class);
    }

    @Override
    public List<ProductResponseDto> findByCode(String productCode) {
        QProduct product = QProduct.product;

        return from(product)
                .where(product.productCode.eq(productCode))
                .select(Projections.bean(ProductResponseDto.class,
                        product.no,
                        product.name,
                        product.amount,
                        product.manufacturer,
                        product.manufacturerCountry,
                        product.seller,
                        product.importer,
                        product.shippingInstallationCost,
                        product.qualityAssuranceStandard,
                        product.color,
                        product.stockQuantity,
                        product.imageLink1,
                        product.imageLink2,
                        product.imageLink3,
                        product.imageLink4,
                        product.imageLink5,
                        product.explanation,
                        product.productCode,
                        product.registerDatetime))
                .fetch();
    }

    @Override
    public Page<ProductResponseDto> findAllPage(Pageable pageable) {
        QProduct product = QProduct.product;

        QueryResults<ProductResponseDto> result = from(product)
                .select(Projections.bean(ProductResponseDto.class,
                        product.no,
                        product.name,
                        product.amount,
                        product.manufacturer,
                        product.manufacturerCountry,
                        product.seller,
                        product.importer,
                        product.shippingInstallationCost,
                        product.qualityAssuranceStandard,
                        product.color,
                        product.stockQuantity,
                        product.imageLink1,
                        product.imageLink2,
                        product.imageLink3,
                        product.imageLink4,
                        product.imageLink5,
                        product.explanation,
                        product.productCode))
                .orderBy(product.registerDatetime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    @Override
    public Optional<ProductResponseDto> findByProductNo(Integer productNo) {
        QProduct product = QProduct.product;

        return Optional.of(from(product)
                .where(product.no.eq(productNo))
                .select(Projections.bean(ProductResponseDto.class,
                        product.no,
                        product.name,
                        product.amount,
                        product.manufacturer,
                        product.manufacturerCountry,
                        product.seller,
                        product.importer,
                        product.shippingInstallationCost,
                        product.qualityAssuranceStandard,
                        product.color,
                        product.stockQuantity,
                        product.imageLink1,
                        product.imageLink2,
                        product.imageLink3,
                        product.imageLink4,
                        product.imageLink5,
                        product.explanation,
                        product.productCode,
                        product.registerDatetime))
                .fetchOne());
    }

    @Override
    public List<ProductResponseDto> findByPrice(Long minAmount, Long maxAmount) {
        QProduct product = QProduct.product;


        return from(product)
                .where(product.amount.between(minAmount, maxAmount))
                .select(Projections.bean(ProductResponseDto.class,
                        product.no,
                        product.name,
                        product.amount,
                        product.manufacturer,
                        product.manufacturerCountry,
                        product.seller,
                        product.importer,
                        product.shippingInstallationCost,
                        product.qualityAssuranceStandard,
                        product.color,
                        product.stockQuantity,
                        product.imageLink1,
                        product.imageLink2,
                        product.imageLink3,
                        product.imageLink4,
                        product.imageLink5,
                        product.explanation,
                        product.productCode,
                        product.registerDatetime))
                .orderBy(product.amount.asc())
                .fetch();
    }
}
