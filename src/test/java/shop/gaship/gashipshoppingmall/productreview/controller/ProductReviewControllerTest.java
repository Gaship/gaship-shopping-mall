package shop.gaship.gashipshoppingmall.productreview.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import shop.gaship.gashipshoppingmall.productreview.dto.request.ProductReviewRequestDto;
import shop.gaship.gashipshoppingmall.productreview.dummy.ProductReviewDummy;
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

    private MockMultipartHttpServletRequestBuilder multipartPutBuilder(String url) {
        final MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart(url);
        builder.with(request -> {
            request.setMethod(HttpMethod.PUT.name());
            return request;
        });
        return builder;
    }
}