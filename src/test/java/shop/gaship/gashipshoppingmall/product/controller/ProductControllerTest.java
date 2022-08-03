package shop.gaship.gashipshoppingmall.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductCreateRequestDto;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductModifyRequestDto;
import shop.gaship.gashipshoppingmall.product.dto.request.SalesStatusModifyRequestDto;
import shop.gaship.gashipshoppingmall.product.dto.response.ProductAllInfoResponseDto;
import shop.gaship.gashipshoppingmall.product.dummy.ProductDummy;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.product.service.ProductService;
import shop.gaship.gashipshoppingmall.product.service.impl.ProductServiceImpl;
import shop.gaship.gashipshoppingmall.response.PageResponse;
import shop.gaship.gashipshoppingmall.statuscode.status.SalesStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
    private MockMvc mvc;

    @MockBean
    private ProductService service;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMultipartFile multipartFile;

    Product product;

    ProductAllInfoResponseDto response;

    PageRequest pageRequest;

    @BeforeEach
    void setUp() throws IOException {
        File file = new File("src/test/resources/sample.jpg");
        multipartFile = new MockMultipartFile(
            "image", "sample.jpg", "multipart/mixed", new FileInputStream(file));

        pageRequest = PageRequest.of(0, 10);
        product = ProductDummy.dummy();
        response = new ProductAllInfoResponseDto(1, "a", "d", "카테", 100L, LocalDateTime.now(),
            "아", "한국", "판매원", "가나다라", 100L, "w", "#RRRR",
            1, "img", null, null, null, null, "설명", 3, "카테");
    }

    @DisplayName("상품 생성 post 요청")
    @Test
    void productAdd() throws Exception {
        ProductCreateRequestDto createRequest = ProductDummy.createRequestDummy();
        MockMultipartFile multipartCreateRequest = new MockMultipartFile("createRequest", "createRequest",
            "application/json", objectMapper.writeValueAsString(createRequest).getBytes(StandardCharsets.UTF_8));

        mvc.perform(multipart("/api/products")
                .file(multipartFile)
                .file(multipartCreateRequest)
                .contentType(MediaType.MULTIPART_FORM_DATA))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(result -> assertThat(result.getResponse().getContentAsString()).isEmpty())
            .andDo(print());
    }

    @DisplayName("상품 수정 put 요청")
    @Test
    void productModify() throws Exception {
        ProductModifyRequestDto modifyRequest = ProductDummy.modifyRequestDummy();
        MockMultipartFile multipartModifyRequest = new MockMultipartFile("modifyRequest", "modifyRequest",
            "application/json", objectMapper.writeValueAsString(modifyRequest).getBytes(StandardCharsets.UTF_8));

        mvc.perform(multipartPutBuilder("/api/products")
                .file(multipartFile)
                .file(multipartModifyRequest)
                .contentType(MediaType.MULTIPART_FORM_DATA))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(result -> assertThat(result.getResponse().getContentAsString()).isEmpty())
            .andDo(print());
    }

    @DisplayName("상품 판매상태 수정 put 요청")
    @Test
    void salesStatusModify() throws Exception {
        SalesStatusModifyRequestDto salesStatusModifyRequest = new SalesStatusModifyRequestDto(
            1, SalesStatus.SOLD_OUT.getValue());

        doNothing().when(service).modifyProductSalesStatus(salesStatusModifyRequest);

        mvc.perform(put("/api/products/salesStatus")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(salesStatusModifyRequest)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andDo(print());

        verify(service).modifyProductSalesStatus(salesStatusModifyRequest);
    }

    private MockMultipartHttpServletRequestBuilder multipartPutBuilder(String url) {
        final MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart(url);
        builder.with(request1 -> {
            request1.setMethod(HttpMethod.PUT.name());
            return request1;
        });
        return builder;
    }


    @DisplayName("코드로 상품들을 조회하는 테스트")
    @Test
    void getProductByCode() throws Exception {
        //given & when
        List<ProductAllInfoResponseDto> list = List.of(response);
        PageRequest req = PageRequest.of(1, 10);
        Page<ProductAllInfoResponseDto> pages = new PageImpl<>(list, req, list.size());
        when(service.findProductByCode(response.getProductCode(), pageRequest))
            .thenReturn(new PageResponse<>(pages));

        mvc.perform(get("/api/products/code")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON)
                .queryParam("code", response.getProductCode())
                .queryParam("page", objectMapper.writeValueAsString(pageRequest.getPageNumber()))
                .queryParam("size", objectMapper.writeValueAsString(pageRequest.getPageSize())))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].productName").value(response.getProductName()))
            .andExpect(jsonPath("$.content[0].productNo").value(response.getProductNo()))
            .andExpect(jsonPath("$.content[0].categoryName").value(response.getCategoryName()))
            .andExpect(jsonPath("$.content[0].amount").value(response.getAmount()))
            .andExpect(jsonPath("$.content[0].manufacturer").value(response.getManufacturer()))
            .andExpect(jsonPath("$.content[0].country").value(response.getCountry()))
            .andExpect(jsonPath("$.content[0].seller").value(response.getSeller()))
            .andExpect(jsonPath("$.content[0].importer").value(response.getImporter()))
            .andExpect(jsonPath("$.content[0].quality").value(response.getQuality()))
            .andExpect(jsonPath("$.content[0].installationCost").value(response.getInstallationCost()))
            .andExpect(jsonPath("$.content[0].color").value(response.getColor()))
            .andExpect(jsonPath("$.content[0].quantity").value(response.getQuantity()))
            .andExpect(jsonPath("$.content[0].img1").value(response.getImg1()))
            .andExpect(jsonPath("$.content[0].img5").value(response.getImg5()))
            .andExpect(jsonPath("$.content[0].img4").value(response.getImg4()))
            .andExpect(jsonPath("$.content[0].img3").value(response.getImg3()))
            .andExpect(jsonPath("$.content[0].img2").value(response.getImg2()))
            .andExpect(jsonPath("$.content[0].explanation").value(response.getExplanation()))
            .andExpect(jsonPath("$.content[0].level").value(response.getLevel()))
            .andExpect(jsonPath("$.content[0].upperName").value(response.getUpperName()))
            .andDo(print());

        //then
        verify(service, times(1)).findProductByCode(any(), any());
    }

    @DisplayName("상품 단건조회 테스트")
    @Test
    void getProductTest() throws Exception {
        //given & when
        when(service.findProduct(any()))
            .thenReturn(response);

        mvc.perform(get("/api/products/{productNo}", response.getProductNo())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.productName").value(response.getProductName()))
            .andExpect(jsonPath("$.productNo").value(response.getProductNo()))
            .andExpect(jsonPath("$.categoryName").value(response.getCategoryName()))
            .andExpect(jsonPath("$.amount").value(response.getAmount()))
            .andExpect(jsonPath("$.manufacturer").value(response.getManufacturer()))
            .andExpect(jsonPath("$.country").value(response.getCountry()))
            .andExpect(jsonPath("$.seller").value(response.getSeller()))
            .andExpect(jsonPath("$.importer").value(response.getImporter()))
            .andExpect(jsonPath("$.quality").value(response.getQuality()))
            .andExpect(jsonPath("$.installationCost").value(response.getInstallationCost()))
            .andExpect(jsonPath("$.color").value(response.getColor()))
            .andExpect(jsonPath("$.quantity").value(response.getQuantity()))
            .andExpect(jsonPath("$.img1").value(response.getImg1()))
            .andExpect(jsonPath("$.img5").value(response.getImg5()))
            .andExpect(jsonPath("$.img4").value(response.getImg4()))
            .andExpect(jsonPath("$.img3").value(response.getImg3()))
            .andExpect(jsonPath("$.img2").value(response.getImg2()))
            .andExpect(jsonPath("$.explanation").value(response.getExplanation()))
            .andExpect(jsonPath("$.level").value(response.getLevel()))
            .andExpect(jsonPath("$.upperName").value(response.getUpperName()))
            .andDo(print());

        //then
        verify(service, times(1)).findProduct(response.getProductNo());
    }

    @DisplayName("상품 다건조회 - 상품금액으로 조회 테스트")
    @Test
    void getProductsByPrice() throws Exception {
        //given & when
        Pageable pageRequest = PageRequest.of(0, 10);
        Page<ProductAllInfoResponseDto> list = new PageImpl<>(List.of(response), pageRequest, pageRequest.getPageSize());
        when(service.findProductByPrice(0L, 10000000L, pageRequest))
            .thenReturn(new PageResponse<>(list));

        MvcResult mvcResult = mvc.perform(get("/api/products/price")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON)
                .queryParam("min", objectMapper.writeValueAsString(0L))
                .queryParam("max", objectMapper.writeValueAsString(10000000L))
                .queryParam("page", objectMapper.writeValueAsString(pageRequest.getPageNumber()))
                .queryParam("size", objectMapper.writeValueAsString(pageRequest.getPageSize())))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].productName").value(response.getProductName()))
            .andExpect(jsonPath("$.content[0].productNo").value(response.getProductNo()))
            .andExpect(jsonPath("$.content[0].categoryName").value(response.getCategoryName()))
            .andExpect(jsonPath("$.content[0].amount").value(response.getAmount()))
            .andExpect(jsonPath("$.content[0].manufacturer").value(response.getManufacturer()))
            .andExpect(jsonPath("$.content[0].country").value(response.getCountry()))
            .andExpect(jsonPath("$.content[0].seller").value(response.getSeller()))
            .andExpect(jsonPath("$.content[0].importer").value(response.getImporter()))
            .andExpect(jsonPath("$.content[0].quality").value(response.getQuality()))
            .andExpect(jsonPath("$.content[0].installationCost").value(response.getInstallationCost()))
            .andExpect(jsonPath("$.content[0].color").value(response.getColor()))
            .andExpect(jsonPath("$.content[0].quantity").value(response.getQuantity()))
            .andExpect(jsonPath("$.content[0].img1").value(response.getImg1()))
            .andExpect(jsonPath("$.content[0].img5").value(response.getImg5()))
            .andExpect(jsonPath("$.content[0].img4").value(response.getImg4()))
            .andExpect(jsonPath("$.content[0].img3").value(response.getImg3()))
            .andExpect(jsonPath("$.content[0].img2").value(response.getImg2()))
            .andExpect(jsonPath("$.content[0].explanation").value(response.getExplanation()))
            .andExpect(jsonPath("$.content[0].level").value(response.getLevel()))
            .andExpect(jsonPath("$.content[0].upperName").value(response.getUpperName()))
            .andDo(print())
            .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());

    }

    @DisplayName("상품 다건조회 - 카테고리번호로 조회 테스트")
    @Test
    void getProductsByCategoryNo() throws Exception {
        //given & when
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<ProductAllInfoResponseDto> list = new PageImpl<>(List.of(response), pageRequest, pageRequest.getPageSize());
        when(service.findProductByCategory(1, pageRequest))
            .thenReturn(new PageResponse<>(list));

        //then
        mvc.perform(get("/api/products/category/{categoryNo}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON)
                .queryParam("page", objectMapper.writeValueAsString(0))
                .queryParam("size", objectMapper.writeValueAsString(10)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].productName").value(response.getProductName()))
            .andExpect(jsonPath("$.content[0].productNo").value(response.getProductNo()))
            .andExpect(jsonPath("$.content[0].categoryName").value(response.getCategoryName()))
            .andExpect(jsonPath("$.content[0].amount").value(response.getAmount()))
            .andExpect(jsonPath("$.content[0].manufacturer").value(response.getManufacturer()))
            .andExpect(jsonPath("$.content[0].country").value(response.getCountry()))
            .andExpect(jsonPath("$.content[0].seller").value(response.getSeller()))
            .andExpect(jsonPath("$.content[0].importer").value(response.getImporter()))
            .andExpect(jsonPath("$.content[0].quality").value(response.getQuality()))
            .andExpect(jsonPath("$.content[0].installationCost").value(response.getInstallationCost()))
            .andExpect(jsonPath("$.content[0].color").value(response.getColor()))
            .andExpect(jsonPath("$.content[0].quantity").value(response.getQuantity()))
            .andExpect(jsonPath("$.content[0].img1").value(response.getImg1()))
            .andExpect(jsonPath("$.content[0].img5").value(response.getImg5()))
            .andExpect(jsonPath("$.content[0].img4").value(response.getImg4()))
            .andExpect(jsonPath("$.content[0].img3").value(response.getImg3()))
            .andExpect(jsonPath("$.content[0].img2").value(response.getImg2()))
            .andExpect(jsonPath("$.content[0].explanation").value(response.getExplanation()))
            .andExpect(jsonPath("$.content[0].level").value(response.getLevel()))
            .andExpect(jsonPath("$.content[0].upperName").value(response.getUpperName()))
            .andDo(print());

        verify(service, times(1)).findProductByCategory(response.getProductNo(), pageRequest);
    }

    @DisplayName("제품다건조회 - 이름으로 조회")
    @Test
    void getProductByName() throws Exception {
        //given & when
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<ProductAllInfoResponseDto> list = new PageImpl<>(List.of(response), pageRequest, pageRequest.getPageSize());
        when(service.findProductByName(response.getProductName(), pageRequest))
            .thenReturn(new PageResponse<>(list));


        //then
        mvc.perform(get("/api/products/name")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON)
                .queryParam("name", response.getProductName())
                .queryParam("page", objectMapper.writeValueAsString(pageRequest.getPageNumber()))
                .queryParam("size", objectMapper.writeValueAsString(pageRequest.getPageSize())))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].productName").value(response.getProductName()))
            .andExpect(jsonPath("$.content[0].productNo").value(response.getProductNo()))
            .andExpect(jsonPath("$.content[0].categoryName").value(response.getCategoryName()))
            .andExpect(jsonPath("$.content[0].amount").value(response.getAmount()))
            .andExpect(jsonPath("$.content[0].manufacturer").value(response.getManufacturer()))
            .andExpect(jsonPath("$.content[0].country").value(response.getCountry()))
            .andExpect(jsonPath("$.content[0].seller").value(response.getSeller()))
            .andExpect(jsonPath("$.content[0].importer").value(response.getImporter()))
            .andExpect(jsonPath("$.content[0].quality").value(response.getQuality()))
            .andExpect(jsonPath("$.content[0].installationCost").value(response.getInstallationCost()))
            .andExpect(jsonPath("$.content[0].color").value(response.getColor()))
            .andExpect(jsonPath("$.content[0].quantity").value(response.getQuantity()))
            .andExpect(jsonPath("$.content[0].img1").value(response.getImg1()))
            .andExpect(jsonPath("$.content[0].img5").value(response.getImg5()))
            .andExpect(jsonPath("$.content[0].img4").value(response.getImg4()))
            .andExpect(jsonPath("$.content[0].img3").value(response.getImg3()))
            .andExpect(jsonPath("$.content[0].img2").value(response.getImg2()))
            .andExpect(jsonPath("$.content[0].explanation").value(response.getExplanation()))
            .andExpect(jsonPath("$.content[0].level").value(response.getLevel()))
            .andExpect(jsonPath("$.content[0].upperName").value(response.getUpperName()))
            .andDo(print());
    }

    @DisplayName("제품다건조회 - statusCode")
    @Test
    void getProductsByStatusCodeName() throws Exception {
        //given & when
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<ProductAllInfoResponseDto> list = new PageImpl<>(List.of(response), pageRequest, pageRequest.getPageSize());
        when(service.findProductStatusCode("aa", pageRequest))
            .thenReturn(new PageResponse<>(list));

        //then
        mvc.perform(get("/api/products/statusCode")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON)
                .queryParam("statusName", "aa")
                .queryParam("page", objectMapper.writeValueAsString(pageRequest.getPageNumber()))
                .queryParam("size", objectMapper.writeValueAsString(pageRequest.getPageSize())))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].productName").value(response.getProductName()))
            .andExpect(jsonPath("$.content[0].productNo").value(response.getProductNo()))
            .andExpect(jsonPath("$.content[0].categoryName").value(response.getCategoryName()))
            .andExpect(jsonPath("$.content[0].amount").value(response.getAmount()))
            .andExpect(jsonPath("$.content[0].manufacturer").value(response.getManufacturer()))
            .andExpect(jsonPath("$.content[0].country").value(response.getCountry()))
            .andExpect(jsonPath("$.content[0].seller").value(response.getSeller()))
            .andExpect(jsonPath("$.content[0].importer").value(response.getImporter()))
            .andExpect(jsonPath("$.content[0].quality").value(response.getQuality()))
            .andExpect(jsonPath("$.content[0].installationCost").value(response.getInstallationCost()))
            .andExpect(jsonPath("$.content[0].color").value(response.getColor()))
            .andExpect(jsonPath("$.content[0].quantity").value(response.getQuantity()))
            .andExpect(jsonPath("$.content[0].img1").value(response.getImg1()))
            .andExpect(jsonPath("$.content[0].img5").value(response.getImg5()))
            .andExpect(jsonPath("$.content[0].img4").value(response.getImg4()))
            .andExpect(jsonPath("$.content[0].img3").value(response.getImg3()))
            .andExpect(jsonPath("$.content[0].img2").value(response.getImg2()))
            .andExpect(jsonPath("$.content[0].explanation").value(response.getExplanation()))
            .andExpect(jsonPath("$.content[0].level").value(response.getLevel()))
            .andExpect(jsonPath("$.content[0].upperName").value(response.getUpperName()))
            .andDo(print());

        verify(service, times(1))
            .findProductStatusCode("aa", pageRequest);
    }

    @DisplayName("제품다건조회 - 정보들 다있는거")
    @Test
    void getProductsInfoAll() throws Exception {
        //given & when
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<ProductAllInfoResponseDto> list = new PageImpl<>(List.of(response), pageRequest, pageRequest.getPageSize());
        when(service.findProductsInfo(pageRequest))
            .thenReturn(new PageResponse<>(list));

        //then
        mvc.perform(get("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON)
                .queryParam("page", objectMapper.writeValueAsString(pageRequest.getPageNumber()))
                .queryParam("size", objectMapper.writeValueAsString(pageRequest.getPageSize())))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].productName").value(response.getProductName()))
            .andExpect(jsonPath("$.content[0].productNo").value(response.getProductNo()))
            .andExpect(jsonPath("$.content[0].categoryName").value(response.getCategoryName()))
            .andExpect(jsonPath("$.content[0].amount").value(response.getAmount()))
            .andExpect(jsonPath("$.content[0].manufacturer").value(response.getManufacturer()))
            .andExpect(jsonPath("$.content[0].country").value(response.getCountry()))
            .andExpect(jsonPath("$.content[0].seller").value(response.getSeller()))
            .andExpect(jsonPath("$.content[0].importer").value(response.getImporter()))
            .andExpect(jsonPath("$.content[0].quality").value(response.getQuality()))
            .andExpect(jsonPath("$.content[0].installationCost").value(response.getInstallationCost()))
            .andExpect(jsonPath("$.content[0].color").value(response.getColor()))
            .andExpect(jsonPath("$.content[0].quantity").value(response.getQuantity()))
            .andExpect(jsonPath("$.content[0].img1").value(response.getImg1()))
            .andExpect(jsonPath("$.content[0].img5").value(response.getImg5()))
            .andExpect(jsonPath("$.content[0].img4").value(response.getImg4()))
            .andExpect(jsonPath("$.content[0].img3").value(response.getImg3()))
            .andExpect(jsonPath("$.content[0].img2").value(response.getImg2()))
            .andExpect(jsonPath("$.content[0].explanation").value(response.getExplanation()))
            .andExpect(jsonPath("$.content[0].level").value(response.getLevel()))
            .andExpect(jsonPath("$.content[0].upperName").value(response.getUpperName()))
            .andDo(print());

        verify(service, times(1))
            .findProductsInfo(pageRequest);
    }

    @DisplayName("제품다건조회 - 상품번호들로 조회")
    @Test
    void getProductListByProductNos() throws Exception {
        ProductAllInfoResponseDto dto = new ProductAllInfoResponseDto(2, "a", "d", "카테", 100L, LocalDateTime.now(),
            "아", "한국", "판매원", "가나다라", 100L, "w", "#RRRR",
            1, "img", null, null, null, null, "설명", 3, "카테");
        List<ProductAllInfoResponseDto> responseDtoList = new ArrayList<>();
        responseDtoList.add(response);
        responseDtoList.add(dto);

        Page<ProductAllInfoResponseDto> list = new PageImpl<>(responseDtoList, pageRequest, pageRequest.getPageSize());
        when(service.findProductByProductNos(List.of(response.getProductNo(), dto.getProductNo()), pageRequest))
            .thenReturn(new PageResponse<>(list));

        mvc.perform(get("/api/products/many-product/{productNos}", "1,2")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON)
//                .queryParam("productNos", objectMapper.writeValueAsString(response.getProductNo()))
//                .queryParam("productNos", objectMapper.writeValueAsString(dto.getProductNo()))
                .queryParam("page", objectMapper.writeValueAsString(pageRequest.getPageNumber()))
                .queryParam("size", objectMapper.writeValueAsString(pageRequest.getPageSize())))
            .andExpect(jsonPath("$.content[0].productName").value(response.getProductName()))
            .andExpect(jsonPath("$.content[0].productNo").value(response.getProductNo()))
            .andExpect(jsonPath("$.content[0].categoryName").value(response.getCategoryName()))
            .andExpect(jsonPath("$.content[0].amount").value(response.getAmount()))
            .andExpect(jsonPath("$.content[0].manufacturer").value(response.getManufacturer()))
            .andExpect(jsonPath("$.content[0].country").value(response.getCountry()))
            .andExpect(jsonPath("$.content[0].seller").value(response.getSeller()))
            .andExpect(jsonPath("$.content[0].importer").value(response.getImporter()))
            .andExpect(jsonPath("$.content[0].quality").value(response.getQuality()))
            .andExpect(jsonPath("$.content[0].installationCost").value(response.getInstallationCost()))
            .andExpect(jsonPath("$.content[0].color").value(response.getColor()))
            .andExpect(jsonPath("$.content[0].quantity").value(response.getQuantity()))
            .andExpect(jsonPath("$.content[0].img1").value(response.getImg1()))
            .andExpect(jsonPath("$.content[0].img5").value(response.getImg5()))
            .andExpect(jsonPath("$.content[0].img4").value(response.getImg4()))
            .andExpect(jsonPath("$.content[0].img3").value(response.getImg3()))
            .andExpect(jsonPath("$.content[0].img2").value(response.getImg2()))
            .andExpect(jsonPath("$.content[0].explanation").value(response.getExplanation()))
            .andExpect(jsonPath("$.content[0].level").value(response.getLevel()))
            .andExpect(jsonPath("$.content[0].upperName").value(response.getUpperName()))
            .andExpect(jsonPath("$.content[1].productName").value(dto.getProductName()))
            .andExpect(jsonPath("$.content[1].productNo").value(dto.getProductNo()))
            .andExpect(jsonPath("$.content[1].categoryName").value(dto.getCategoryName()))
            .andExpect(jsonPath("$.content[1].amount").value(dto.getAmount()))
            .andExpect(jsonPath("$.content[1].manufacturer").value(dto.getManufacturer()))
            .andExpect(jsonPath("$.content[1].country").value(dto.getCountry()))
            .andExpect(jsonPath("$.content[1].seller").value(dto.getSeller()))
            .andExpect(jsonPath("$.content[1].importer").value(dto.getImporter()))
            .andExpect(jsonPath("$.content[1].quality").value(dto.getQuality()))
            .andExpect(jsonPath("$.content[1].installationCost").value(dto.getInstallationCost()))
            .andExpect(jsonPath("$.content[1].color").value(dto.getColor()))
            .andExpect(jsonPath("$.content[1].quantity").value(dto.getQuantity()))
            .andExpect(jsonPath("$.content[1].img1").value(dto.getImg1()))
            .andExpect(jsonPath("$.content[1].img5").value(dto.getImg5()))
            .andExpect(jsonPath("$.content[1].img4").value(dto.getImg4()))
            .andExpect(jsonPath("$.content[1].img3").value(dto.getImg3()))
            .andExpect(jsonPath("$.content[1].img2").value(dto.getImg2()))
            .andExpect(jsonPath("$.content[1].explanation").value(dto.getExplanation()))
            .andExpect(jsonPath("$.content[1].level").value(dto.getLevel()))
            .andExpect(jsonPath("$.content[1].upperName").value(dto.getUpperName()))
            .andExpect(status().isOk())

            .andDo(print());

        verify(service, times(1))
            .findProductByProductNos(List.of(response.getProductNo(), dto.getProductNo()), pageRequest);

    }
}