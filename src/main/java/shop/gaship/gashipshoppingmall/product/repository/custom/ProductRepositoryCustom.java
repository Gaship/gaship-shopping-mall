package shop.gaship.gashipshoppingmall.product.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.gaship.gashipshoppingmall.product.dto.response.ProductAllInfoResponseDto;
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

    /**
     * 카테고리 번호를 기준으로 조회하는 메서드입니다.
     *
     * @param categoryNo 조회하기위한 카테고리 번호
     * @return list 조회된 상품들의 정보가 반환됩니다.
     * @author 유호철
     */
    List<ProductResponseDto> findProductByCategory(Integer categoryNo);

    /**
     * 상품이름을 기준으로 조회하는 메서드입니다.
     *
     * @param name 조회할 상품의 이름이 들어갑니다.
     * @return list 조회된 상품들의 정보가 반환됩니다.
     * @author 유호철
     */
    List<ProductResponseDto> findByProductName(String name);

    /**
     * 태그번호를 통해 상품들을 조회하는 메서드입니다.
     *
     * @param tagNo 태그번호가 들어갑니다.
     * @return list 조회된 상품들의 정보가 반환됩니다.
     * @author 유호철
     */
    List<ProductResponseDto> findByTag(Integer tagNo);

    /**
     * @param productName 제품의 이름으로 조회할때 사용.
     * @param productCode 제품의 코드로 조회할때 사용.
     * @param categoryNo 카테고리로 조회할때 사용.
     * @param minAmount 금액으로 조회할때 사용.
     * @param maxAmount 금액으로 조회할때 사용.
     * @param tagNo 태그로 조회할때 사용.
     * @return page 페이징이된 제품의 정보가 들어 있습니다.
     * @author 유호철
     */
    List<ProductAllInfoResponseDto> findProduct(String productName, String productCode,
                                                Integer categoryNo, Long minAmount, Long maxAmount,
                                                Integer tagNo, Pageable pageable);
}
