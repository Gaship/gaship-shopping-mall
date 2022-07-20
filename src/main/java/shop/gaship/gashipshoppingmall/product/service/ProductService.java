package shop.gaship.gashipshoppingmall.product.service;

import org.springframework.web.multipart.MultipartFile;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductCreateRequestDto;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductModifyRequestDto;
import shop.gaship.gashipshoppingmall.product.dto.request.SalesStatusModifyRequestDto;

import java.io.IOException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import shop.gaship.gashipshoppingmall.product.dto.response.ProductAllInfoResponseDto;
import shop.gaship.gashipshoppingmall.product.dto.response.ProductResponseDto;
import shop.gaship.gashipshoppingmall.product.exception.ProductNotFoundException;
import shop.gaship.gashipshoppingmall.response.PageResponse;

import java.util.List;

/**
 * 상품 서비스 인터페이스 입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
public interface ProductService {
    /**
     * 상품 등록 메서드입니다.
     *
     * @param files 리스트 형태의 다중 이미지 파일
     * @param createRequest 상품 생성 요청 dto
     * @throws IOException 파일 업로드 시 IOException 이 발생할 수 있습니다.
     * @author 김보민
     */
    void addProduct(List<MultipartFile> files, ProductCreateRequestDto createRequest) throws IOException;

    /**
     * 상품 수정 메서드입니다.
     *
     * @param files 리스트 형태의 다중 이미지 파일
     * @param modifyRequest 상품 수정 요청 dto
     * @throws IOException 파일 업로드 시 IOException 이 발생할 수 있습니다.
     * @author 김보민
     */
    void modifyProduct(List<MultipartFile> files, ProductModifyRequestDto modifyRequest) throws IOException;

    /**
     * 상품 판매상태를 수정하는 메서드입니다.
     *
     * @param salesStatusModifyRequest 상품 판매상태 수정 요청 dto
     * @author 김보민
     */
    void modifyProductSalesStatus(SalesStatusModifyRequestDto salesStatusModifyRequest);

    /**
     * 제품의 정보를 코드값으로 조회하기위한 메서드입니다.
     *
     * @param productCode 조회할 코드정보입니다.
     * @return list 제품의 정보들이 list 형태로 반환됩니다.
     * @author 유호철
     */
    PageResponse<ProductAllInfoResponseDto> findProductByCode(String productCode, PageRequest pageRequest);


    /**
     * 제품하나를 조회하기위한 메서드입니다.
     *
     * @param no 조회할 제품번호입니다.
     * @return product response dto 제품의정보가담긴 객체로 반환됩니다.
     * @throws ProductNotFoundException 제품이존재하지않을경우 예외가 발생합니다.
     * @author 유호철
     */
    ProductAllInfoResponseDto findProduct(Integer no);

    /**
     * 최대, 최소 금액을 통해 알맞는 상품을 조회하는 메서드입니다.
     *
     * @param min 최소금액입니다.
     * @param max 최대금액입니다.
     * @return list
     * @author 유호철
     */
    PageResponse<ProductAllInfoResponseDto> findProductByPrice(Long min, Long max, Integer page, Integer size);

    /**
     * 카테고리를 통해 알맞은 상품을 조회하는 메서드입니다.
     *
     * @param no 카테고리 no 가 입력이된다.
     * @return list 같은 카테고리의 no 를 가진 제품들이 반환된다.
     * @author 유호철
     */
    PageResponse<ProductAllInfoResponseDto> findProductByCategory(Integer no, Integer page, Integer size);

    /**
     * 이름을 통해 알맞은 상품을 조호하는 메서드입니다.
     *
     * @param name 조회할 상품의 이름.
     * @author 유호철
     */
    PageResponse<ProductAllInfoResponseDto> findProductByName(String name, Integer page, Integer size);

    /**
     * 알맞은 상품들의 정보들을 반환합니다.
     *
     * @return list 조회된 상품들의 정보들을 반환합니다.
     * @author 유호철
     */
    PageResponse<ProductAllInfoResponseDto> findProductsInfo(Integer page, Integer size);
}
