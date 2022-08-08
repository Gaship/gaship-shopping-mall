package shop.gaship.gashipshoppingmall.productreview.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import shop.gaship.gashipshoppingmall.productreview.dto.request.ProductReviewRequestDto;
import shop.gaship.gashipshoppingmall.productreview.dto.response.ProductReviewResponseDto;
import shop.gaship.gashipshoppingmall.productreview.dummy.ProductReviewDummy;
import shop.gaship.gashipshoppingmall.productreview.entity.ProductReview;
import shop.gaship.gashipshoppingmall.productreview.service.ProductReviewService;

/**
 * 상품평 컨트롤러 테스트
 *
 * @author : 김보민
 * @since 1.0
 */
@WebMvcTest(ProductReviewController.class)
class ProductReviewControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductReviewService productReviewService;

    @Autowired
    ObjectMapper objectMapper;

    MockMultipartFile multipartFile;
    ProductReviewResponseDto responseDto = ProductReviewDummy.responseDummy();
    Pageable pageable = PageRequest.of(0, 5);
    Page<ProductReviewResponseDto> page = new PageImpl<>(List.of(responseDto), pageable, 1);

    @BeforeEach
    void setUp() throws IOException {
        File file = new File("src/test/resources/sample.jpg");
        multipartFile = new MockMultipartFile(
                "image", "sample.jpg", "multipart/mixed", new FileInputStream(file));
    }

    @DisplayName("상품평 생성 post 요청 테스트")
    @Test
    void productReviewAdd() throws Exception {
        ProductReviewRequestDto createRequest = ProductReviewDummy.createRequestDummy();
        MockMultipartFile multipartCreateRequest = new MockMultipartFile(
                "createRequest", "createRequest", "application/json",
                objectMapper.writeValueAsString(createRequest).getBytes(StandardCharsets.UTF_8));

        mockMvc.perform(multipart("/api/reviews")
                        .file(multipartFile)
                        .file(multipartCreateRequest)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @DisplayName("상품평 수정 put 요청 테스트")
    @Test
    void productReviewModify() throws Exception {
        ProductReviewRequestDto modifyRequest = ProductReviewDummy.modifyRequestDummy();
        MockMultipartFile multipartModifyRequest = new MockMultipartFile(
                "modifyRequest", "modifyRequest", "application/json",
                objectMapper.writeValueAsString(modifyRequest).getBytes(StandardCharsets.UTF_8));

        String uri = UriComponentsBuilder.newInstance()
                .path("/api/reviews/")
                .path(String.valueOf(modifyRequest.getOrderProductNo()))
                .toUriString();

        mockMvc.perform(multipartPutBuilder(uri)
                        .file(multipartFile)
                        .file(multipartModifyRequest)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertThat(result.getResponse().getContentAsString()).isEmpty())
                .andDo(print());
    }

    @DisplayName("상품평 삭제 delete 요청 테스트")
    @Test
    void productReviewRemove() throws Exception {
        Integer orderProductNo = 1;

        doNothing().when(productReviewService).removeProductReview(orderProductNo);

        mockMvc.perform(delete("/api/reviews/{orderProductNo}", orderProductNo))
                .andExpect(status().isOk())
                .andDo(print());

        verify(productReviewService).removeProductReview(orderProductNo);
    }

    @DisplayName("상품평 단건조회 get 요청 테스트")
    @Test
    void productReviewDetails() throws Exception {
        Integer orderProductNo = 1;

        when(productReviewService.findReview(orderProductNo))
                .thenReturn(ProductReviewDummy.responseDummy());

        mockMvc.perform(get("/api/reviews/{orderProductNo}", orderProductNo))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.orderProductNo").value(responseDto.getOrderProductNo()))
                .andExpect(jsonPath("$.productName").value(responseDto.getProductName()))
                .andExpect(jsonPath("$.writerNickname").value(responseDto.getWriterNickname()))
                .andExpect(jsonPath("$.title").value(responseDto.getTitle()))
                .andExpect(jsonPath("$.content").value(responseDto.getContent()))
                .andExpect(jsonPath("$.starScore").value(responseDto.getStarScore()))
                .andExpect(jsonPath("$.imagePath").value(responseDto.getImagePath()))
                .andDo(print());

        verify(productReviewService).findReview(orderProductNo);
    }

    @DisplayName("상품평 전체조회 get 요청 테스트")
    @Test
    void productReviewList() throws Exception {
        when(productReviewService.findReviews(pageable)).thenReturn(page);

        mockMvc.perform(get("/api/reviews")
                        .queryParam("page", objectMapper.writeValueAsString(pageable.getPageNumber()))
                        .queryParam("size", objectMapper.writeValueAsString(pageable.getPageSize())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.size()").value(1))
                .andExpect(jsonPath("$.content[0].orderProductNo").value(responseDto.getOrderProductNo()))
                .andExpect(jsonPath("$.content[0].productName").value(responseDto.getProductName()))
                .andExpect(jsonPath("$.content[0].writerNickname").value(responseDto.getWriterNickname()))
                .andExpect(jsonPath("$.content[0].title").value(responseDto.getTitle()))
                .andExpect(jsonPath("$.content[0].content").value(responseDto.getContent()))
                .andExpect(jsonPath("$.content[0].starScore").value(responseDto.getStarScore()))
                .andExpect(jsonPath("$.content[0].imagePath").value(responseDto.getImagePath()))
                .andDo(print());

        verify(productReviewService).findReviews(pageable);
    }

    @DisplayName("상품번호로 상품평 다건조회 get 요청 테스트")
    @Test
    void productReviewListByProduct() throws Exception {
        Integer productNo = 1;

        when(productReviewService.findReviewsByProductNo(productNo, pageable)).thenReturn(page);

        mockMvc.perform(get("/api/reviews/product/{productNo}", productNo)
                        .queryParam("page", objectMapper.writeValueAsString(pageable.getPageNumber()))
                        .queryParam("size", objectMapper.writeValueAsString(pageable.getPageSize())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.size()").value(1))
                .andExpect(jsonPath("$.content[0].orderProductNo").value(responseDto.getOrderProductNo()))
                .andExpect(jsonPath("$.content[0].productName").value(responseDto.getProductName()))
                .andExpect(jsonPath("$.content[0].writerNickname").value(responseDto.getWriterNickname()))
                .andExpect(jsonPath("$.content[0].title").value(responseDto.getTitle()))
                .andExpect(jsonPath("$.content[0].content").value(responseDto.getContent()))
                .andExpect(jsonPath("$.content[0].starScore").value(responseDto.getStarScore()))
                .andExpect(jsonPath("$.content[0].imagePath").value(responseDto.getImagePath()))
                .andDo(print());

        verify(productReviewService).findReviewsByProductNo(productNo, pageable);
    }

    @DisplayName("회원번호로 상품평 다건조회 get 요청 테스트")
    @Test
    void productReviewListByMember() throws Exception {
        Integer memberNo = 1;

        when(productReviewService.findReviewsByMemberNo(memberNo, pageable)).thenReturn(page);

        mockMvc.perform(get("/api/reviews/member/{memberNo}", memberNo)
                        .queryParam("page", objectMapper.writeValueAsString(pageable.getPageNumber()))
                        .queryParam("size", objectMapper.writeValueAsString(pageable.getPageSize())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.size()").value(1))
                .andExpect(jsonPath("$.content[0].orderProductNo").value(responseDto.getOrderProductNo()))
                .andExpect(jsonPath("$.content[0].productName").value(responseDto.getProductName()))
                .andExpect(jsonPath("$.content[0].writerNickname").value(responseDto.getWriterNickname()))
                .andExpect(jsonPath("$.content[0].title").value(responseDto.getTitle()))
                .andExpect(jsonPath("$.content[0].content").value(responseDto.getContent()))
                .andExpect(jsonPath("$.content[0].starScore").value(responseDto.getStarScore()))
                .andExpect(jsonPath("$.content[0].imagePath").value(responseDto.getImagePath()))
                .andDo(print());

        verify(productReviewService).findReviewsByMemberNo(memberNo, pageable);
    }

    private MockMultipartHttpServletRequestBuilder multipartPutBuilder(String url) {
        final MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart(url);
        builder.with(request -> {
            request.setMethod(HttpMethod.PUT.name());
            return request;
        });
        return builder;
    }
}