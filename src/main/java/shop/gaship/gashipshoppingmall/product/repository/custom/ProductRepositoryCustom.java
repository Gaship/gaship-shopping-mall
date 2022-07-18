package shop.gaship.gashipshoppingmall.product.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.gaship.gashipshoppingmall.product.dto.response.ProductResponseDto;

import java.util.List;
import java.util.Optional;

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
     * @param pageable 페이징 하기위한 객체입니다.
     *
     * @return page 페이징된 제품들이 반환됩니다.
     * @author 유호철
     */
    Page<ProductResponseDto> findAllPage(Pageable pageable);

    /**
     * 제품번호를 통해 제품을 조회하기 위한 메서드입니다.
     *
     * @param productNo 조회하기위한 제품번호입니다.
     * @return optional 제품값을 optional 로 반환합니다.
     * @author 유호철
     */
    Optional<ProductResponseDto> findByProductNo(Integer productNo);

    /**
     * 금액을 기준으로 상품을 조회하는 메서드입니다.
     *
     * @param minAmount 최소금액입니다.
     * @param maxAmount 최대금액입니다.
     * @return list 조회된 상품들이 반환됩니다.
     * @author 유호철
     */
    List<ProductResponseDto> findByPrice(Long minAmount, Long maxAmount);
}
