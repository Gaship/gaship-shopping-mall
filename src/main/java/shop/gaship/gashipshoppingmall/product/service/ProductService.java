package shop.gaship.gashipshoppingmall.product.service;

import org.springframework.data.domain.Page;
import shop.gaship.gashipshoppingmall.product.dto.response.ProductResponseDto;
import shop.gaship.gashipshoppingmall.product.exception.ProductNotFoundException;

import java.util.List;

/**
 * 상품 서비스 인터페이스 입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
public interface ProductService {

    /**
     * 제품의 정보를 코드값으로 조회하기위한 메서드입니다.
     *
     * @param productCode 조회할 코드정보입니다.
     * @return list 제품의 정보들이 list 형태로 반환됩니다.
     * @author 유호철
     */
    List<ProductResponseDto> findProductByCode(String productCode);

    /**
     * 제품들을 페이징처리해서 조회하기위한 메서드입니다.
     *
     * @param page 페이지 정보입니다.
     * @param size 페이지 사이즈정보입니다.
     * @author 유호철
     */
    Page<ProductResponseDto> findProducts(int page, int size);

    /**
     * 제품하나를 조회하기위한 메서드입니다.
     *
     * @param no 조회할 제품번호입니다.
     * @return product response dto 제품의정보가담긴 객체로 반환됩니다.
     * @throws ProductNotFoundException 제품이존재하지않을경우 예외가 발생합니다.
     * @author 유호철
     */
    ProductResponseDto findProduct(Integer no);

    /**
     * 최대, 최소 금액을 통해 알맞는 상품을 조회하는 메서드입니다.
     *
     * @param min 최소금액입니다.
     * @param max 최대금액입니다.
     * @return list
     * @author 유호철
     */
    List<ProductResponseDto> findProductByPrice(Long min, Long max);
}
