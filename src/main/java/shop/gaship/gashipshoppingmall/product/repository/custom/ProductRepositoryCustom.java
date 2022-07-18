package shop.gaship.gashipshoppingmall.product.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.gaship.gashipshoppingmall.product.dto.response.ProductResponseDto;

import java.util.List;

/**
 * QueryDsl 을 하기위한 인터페이스 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
public interface ProductRepositoryCustom {
    /**
     * 제품코드로 제품을 조회하기위한 메서드입니다.
     *
     * @param productCode 조회할 제품코드가 들어옵니다.
     * @return productResponseDto 조회된 제품의 정보가 들어가서 반환됩니다.
     * @author 유호철
     */
    List<ProductResponseDto> findByCode(String productCode);

    /**
     * 페이징된 제품들을 조회하기위해 만든 메서드입니다.
     *
     * pageable 페이징 하기위한 객체
     * @return page 페이징된 제품들이 반환됩니다.
     * @author 유호철
     */
    Page<ProductResponseDto> findAllPage(Pageable pageable);
}
