package shop.gaship.gashipshoppingmall.product.repository.impl;

import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.gaship.gashipshoppingmall.product.dto.response.ProductResponseDto;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.product.entity.QProduct;
import shop.gaship.gashipshoppingmall.product.repository.custom.ProductRepositoryCustom;

import java.util.List;

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
                        product.productCode))
                .fetch();
    }
}
