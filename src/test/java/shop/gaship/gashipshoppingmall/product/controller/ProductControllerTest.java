package shop.gaship.gashipshoppingmall.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipshoppingmall.product.dto.response.ProductAllInfoResponseDto;
import shop.gaship.gashipshoppingmall.product.dto.response.ProductResponseDto;
import shop.gaship.gashipshoppingmall.product.dummy.ProductDummy;
import shop.gaship.gashipshoppingmall.product.dummy.ProductResponseDtoDummy;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.product.service.ProductService;
import shop.gaship.gashipshoppingmall.product.service.impl.ProductServiceImpl;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 상품 컨트롤러 테스트 입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(ProductController.class)
@Import(ProductServiceImpl.class)
class ProductControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    ProductService service;

    @Autowired
    ObjectMapper objectMapper;

    ProductResponseDto responseDto;

    Product product;
    ProductAllInfoResponseDto response;

    @BeforeEach
    void setUp() {
        product = ProductDummy.dummy2();
        responseDto = ProductResponseDtoDummy.dummy();
        response = new ProductAllInfoResponseDto(1, "a", "d", "카테", 100L, LocalDateTime.now(),
                "아", "한국", "판매원", "가나다라", 100L, "w", "#RRRR",
                1, "img", null, null, null, null, "설명", 3, "카테");
    }

    @DisplayName("코드로 상품들을 조회하는 테스트")
    @Test
    void getProductByCode() throws Exception {
        //given & when
        when(service.findProductByCode(responseDto.getProductCode()))
                .thenReturn(List.of(responseDto));

        mvc.perform(get("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
                        .queryParam("code", responseDto.getProductCode()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].no").value(responseDto.getNo()))
                .andExpect(jsonPath("$[0].name").value(responseDto.getName()))
                .andExpect(jsonPath("$[0].amount").value(responseDto.getAmount()))
                .andExpect(jsonPath("$[0].manufacturer").value(responseDto.getManufacturer()))
                .andExpect(jsonPath("$[0].manufacturerCountry").value(responseDto.getManufacturerCountry()))
                .andExpect(jsonPath("$[0].seller").value(responseDto.getSeller()))
                .andExpect(jsonPath("$[0].importer").value(responseDto.getImporter()))
                .andExpect(jsonPath("$[0].shippingInstallationCost").value(responseDto.getShippingInstallationCost()))
                .andExpect(jsonPath("$[0].qualityAssuranceStandard").value(responseDto.getQualityAssuranceStandard()))
                .andExpect(jsonPath("$[0].color").value(responseDto.getColor()))
                .andExpect(jsonPath("$[0].stockQuantity").value(responseDto.getStockQuantity()))
                .andExpect(jsonPath("$[0].imageLink1").value(responseDto.getImageLink1()))
                .andExpect(jsonPath("$[0].imageLink2").value(responseDto.getImageLink2()))
                .andExpect(jsonPath("$[0].imageLink3").value(responseDto.getImageLink3()))
                .andExpect(jsonPath("$[0].imageLink4").value(responseDto.getImageLink4()))
                .andExpect(jsonPath("$[0].imageLink5").value(responseDto.getImageLink5()))
                .andExpect(jsonPath("$[0].explanation").value(responseDto.getExplanation()))
                .andExpect(jsonPath("$[0].productCode").value(responseDto.getProductCode()))
                .andDo(print());

        //then
        verify(service, times(1)).findProductByCode(any());
    }

    @DisplayName("상품전체 조회 페이지네이션 테스트")
    @Test
    void getProductPageTest() throws Exception {
        //given & when
        List<ProductResponseDto> list = new ArrayList<>();
        list.add(responseDto);
        PageRequest req = PageRequest.of(1, 10);
        Page<ProductResponseDto> pages = new PageImpl<>(list, req, list.size());

        when(service.findProducts(1, 10))
                .thenReturn(pages);

        mvc.perform(get("/products/page")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
                        .queryParam("page", objectMapper.writeValueAsString(req.getPageNumber()))
                        .queryParam("size", objectMapper.writeValueAsString(req.getPageSize())))
                .andExpect(status().isOk())
                .andDo(print());

        //then
        verify(service, times(1)).findProducts(1, 10);

    }

    @DisplayName("상품 단건조회 테스트")
    @Test
    void getProductTest() throws Exception {
        //given & when
        when(service.findProduct(responseDto.getNo()))
                .thenReturn(responseDto);

        mvc.perform(get("/products/{productNo}", responseDto.getNo())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.no").value(responseDto.getNo()))
                .andExpect(jsonPath("$.name").value(responseDto.getName()))
                .andExpect(jsonPath("$.amount").value(responseDto.getAmount()))
                .andExpect(jsonPath("$.manufacturer").value(responseDto.getManufacturer()))
                .andExpect(jsonPath("$.manufacturerCountry").value(responseDto.getManufacturerCountry()))
                .andExpect(jsonPath("$.seller").value(responseDto.getSeller()))
                .andExpect(jsonPath("$.importer").value(responseDto.getImporter()))
                .andExpect(jsonPath("$.shippingInstallationCost").value(responseDto.getShippingInstallationCost()))
                .andExpect(jsonPath("$.qualityAssuranceStandard").value(responseDto.getQualityAssuranceStandard()))
                .andExpect(jsonPath("$.color").value(responseDto.getColor()))
                .andExpect(jsonPath("$.stockQuantity").value(responseDto.getStockQuantity()))
                .andExpect(jsonPath("$.imageLink1").value(responseDto.getImageLink1()))
                .andExpect(jsonPath("$.imageLink2").value(responseDto.getImageLink2()))
                .andExpect(jsonPath("$.imageLink3").value(responseDto.getImageLink3()))
                .andExpect(jsonPath("$.imageLink4").value(responseDto.getImageLink4()))
                .andExpect(jsonPath("$.imageLink5").value(responseDto.getImageLink5()))
                .andExpect(jsonPath("$.explanation").value(responseDto.getExplanation()))
                .andExpect(jsonPath("$.productCode").value(responseDto.getProductCode()))
                .andDo(print());

        //then
        verify(service, times(1)).findProduct(responseDto.getNo());
    }

    @DisplayName("상품 다건조회 - 상품금액으로 조회 테스트")
    @Test
    void getProductsByPrice() throws Exception {
        //given & when
        when(service.findProductByPrice(0L, responseDto.getAmount()))
                .thenReturn(List.of(responseDto));

        mvc.perform(get("/products/price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
                        .queryParam("min", objectMapper.writeValueAsString(0L))
                        .queryParam("max", objectMapper.writeValueAsString(responseDto.getAmount())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].no").value(responseDto.getNo()))
                .andExpect(jsonPath("$[0].name").value(responseDto.getName()))
                .andExpect(jsonPath("$[0].amount").value(responseDto.getAmount()))
                .andExpect(jsonPath("$[0].manufacturer").value(responseDto.getManufacturer()))
                .andExpect(jsonPath("$[0].manufacturerCountry").value(responseDto.getManufacturerCountry()))
                .andExpect(jsonPath("$[0].seller").value(responseDto.getSeller()))
                .andExpect(jsonPath("$[0].importer").value(responseDto.getImporter()))
                .andExpect(jsonPath("$[0].shippingInstallationCost").value(responseDto.getShippingInstallationCost()))
                .andExpect(jsonPath("$[0].qualityAssuranceStandard").value(responseDto.getQualityAssuranceStandard()))
                .andExpect(jsonPath("$[0].color").value(responseDto.getColor()))
                .andExpect(jsonPath("$[0].stockQuantity").value(responseDto.getStockQuantity()))
                .andExpect(jsonPath("$[0].imageLink1").value(responseDto.getImageLink1()))
                .andExpect(jsonPath("$[0].imageLink2").value(responseDto.getImageLink2()))
                .andExpect(jsonPath("$[0].imageLink3").value(responseDto.getImageLink3()))
                .andExpect(jsonPath("$[0].imageLink4").value(responseDto.getImageLink4()))
                .andExpect(jsonPath("$[0].imageLink5").value(responseDto.getImageLink5()))
                .andExpect(jsonPath("$[0].explanation").value(responseDto.getExplanation()))
                .andExpect(jsonPath("$[0].productCode").value(responseDto.getProductCode()))
                .andDo(print());

    }

    @DisplayName("상품 다건조회 - 카테고리번호로 조회 테스트")
    @Test
    void getProductsByCategoryNo() throws Exception {
        //given & when
        when(service.findProductByCategory(any()))
                .thenReturn(List.of(responseDto));

        //then
        mvc.perform(get("/products/category/{categoryNo}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].no").value(responseDto.getNo()))
                .andExpect(jsonPath("$[0].name").value(responseDto.getName()))
                .andExpect(jsonPath("$[0].amount").value(responseDto.getAmount()))
                .andExpect(jsonPath("$[0].manufacturer").value(responseDto.getManufacturer()))
                .andExpect(jsonPath("$[0].manufacturerCountry").value(responseDto.getManufacturerCountry()))
                .andExpect(jsonPath("$[0].seller").value(responseDto.getSeller()))
                .andExpect(jsonPath("$[0].importer").value(responseDto.getImporter()))
                .andExpect(jsonPath("$[0].shippingInstallationCost").value(responseDto.getShippingInstallationCost()))
                .andExpect(jsonPath("$[0].qualityAssuranceStandard").value(responseDto.getQualityAssuranceStandard()))
                .andExpect(jsonPath("$[0].color").value(responseDto.getColor()))
                .andExpect(jsonPath("$[0].stockQuantity").value(responseDto.getStockQuantity()))
                .andExpect(jsonPath("$[0].imageLink1").value(responseDto.getImageLink1()))
                .andExpect(jsonPath("$[0].imageLink2").value(responseDto.getImageLink2()))
                .andExpect(jsonPath("$[0].imageLink3").value(responseDto.getImageLink3()))
                .andExpect(jsonPath("$[0].imageLink4").value(responseDto.getImageLink4()))
                .andExpect(jsonPath("$[0].imageLink5").value(responseDto.getImageLink5()))
                .andExpect(jsonPath("$[0].explanation").value(responseDto.getExplanation()))
                .andExpect(jsonPath("$[0].productCode").value(responseDto.getProductCode()))
                .andDo(print());

        verify(service, times(1)).findProductByCategory(any());
    }

    @DisplayName("제품다건조회 - 이름으로 조회")
    @Test
    void getProductByName() throws Exception {
        //given & when
        when(service.findProductByName(product.getName()))
                .thenReturn(List.of(responseDto));

        //then
        mvc.perform(get("/products/name")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
                        .queryParam("name", product.getName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].no").value(responseDto.getNo()))
                .andExpect(jsonPath("$[0].name").value(responseDto.getName()))
                .andExpect(jsonPath("$[0].amount").value(responseDto.getAmount()))
                .andExpect(jsonPath("$[0].manufacturer").value(responseDto.getManufacturer()))
                .andExpect(jsonPath("$[0].manufacturerCountry").value(responseDto.getManufacturerCountry()))
                .andExpect(jsonPath("$[0].seller").value(responseDto.getSeller()))
                .andExpect(jsonPath("$[0].importer").value(responseDto.getImporter()))
                .andExpect(jsonPath("$[0].shippingInstallationCost").value(responseDto.getShippingInstallationCost()))
                .andExpect(jsonPath("$[0].qualityAssuranceStandard").value(responseDto.getQualityAssuranceStandard()))
                .andExpect(jsonPath("$[0].color").value(responseDto.getColor()))
                .andExpect(jsonPath("$[0].stockQuantity").value(responseDto.getStockQuantity()))
                .andExpect(jsonPath("$[0].imageLink1").value(responseDto.getImageLink1()))
                .andExpect(jsonPath("$[0].imageLink2").value(responseDto.getImageLink2()))
                .andExpect(jsonPath("$[0].imageLink3").value(responseDto.getImageLink3()))
                .andExpect(jsonPath("$[0].imageLink4").value(responseDto.getImageLink4()))
                .andExpect(jsonPath("$[0].imageLink5").value(responseDto.getImageLink5()))
                .andExpect(jsonPath("$[0].explanation").value(responseDto.getExplanation()))
                .andExpect(jsonPath("$[0].productCode").value(responseDto.getProductCode()))

                .andDo(print());
    }

    @DisplayName("제품다건조회 - 정보들 다있는거")
    @Test
    void getProductsInfoAll() throws Exception {
        //given & when
        when(service.findProductsInfo())
                .thenReturn(List.of(response));

        //then
        mvc.perform(get("/products/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productName").value(response.getProductName()))
                .andExpect(jsonPath("$[0].productNo").value(response.getProductNo()))
                .andExpect(jsonPath("$[0].categoryName").value(response.getCategoryName()))
                .andExpect(jsonPath("$[0].amount").value(response.getAmount()))
                .andExpect(jsonPath("$[0].manufacturer").value(response.getManufacturer()))
                .andExpect(jsonPath("$[0].country").value(response.getCountry()))
                .andExpect(jsonPath("$[0].seller").value(response.getSeller()))
                .andExpect(jsonPath("$[0].importer").value(response.getImporter()))
                .andExpect(jsonPath("$[0].quality").value(response.getQuality()))
                .andExpect(jsonPath("$[0].installationCost").value(response.getInstallationCost()))
                .andExpect(jsonPath("$[0].color").value(response.getColor()))
                .andExpect(jsonPath("$[0].quantity").value(response.getQuantity()))
                .andExpect(jsonPath("$[0].img1").value(response.getImg1()))
                .andExpect(jsonPath("$[0].img5").value(response.getImg5()))
                .andExpect(jsonPath("$[0].img4").value(response.getImg4()))
                .andExpect(jsonPath("$[0].img3").value(response.getImg3()))
                .andExpect(jsonPath("$[0].img2").value(response.getImg2()))
                .andExpect(jsonPath("$[0].explanation").value(response.getExplanation()))
                .andExpect(jsonPath("$[0].level").value(response.getLevel()))
                .andExpect(jsonPath("$[0].upperName").value(response.getUpperName()))
                .andDo(print());

        verify(service,times(1))
                .findProductsInfo();
    }
}