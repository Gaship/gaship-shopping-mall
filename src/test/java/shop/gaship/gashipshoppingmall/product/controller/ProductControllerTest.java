package shop.gaship.gashipshoppingmall.product.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import shop.gaship.gashipshoppingmall.product.dto.request.ProductCreateRequestDto;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductModifyRequestDto;
import shop.gaship.gashipshoppingmall.product.dummy.ProductDummy;
import shop.gaship.gashipshoppingmall.product.service.ProductService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 상품 컨트롤러 테스트 입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@WebMvcTest({ ProductController.class })
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMultipartFile multipartFile;

    @BeforeEach
    void setUp() throws IOException {
        File file = new File("src/test/resources/sample.jpg");
        multipartFile = new MockMultipartFile(
                "image", "sample.jpg", "multipart/mixed", new FileInputStream(file));
    }

    @DisplayName("상품 생성 post 요청")
    @Test
    void productAdd() throws Exception {
        ProductCreateRequestDto createRequest = ProductDummy.createRequestDummy();
        MockMultipartFile multipartCreateRequest = new MockMultipartFile("createRequest", "createRequest",
                "application/json", objectMapper.writeValueAsString(createRequest).getBytes(StandardCharsets.UTF_8));

        mockMvc.perform(multipart("/products")
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

        mockMvc.perform(multipartPutBuilder("/products")
                    .file(multipartFile)
                    .file(multipartModifyRequest)
                    .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertThat(result.getResponse().getContentAsString()).isEmpty())
                .andDo(print());
    }

    private MockMultipartHttpServletRequestBuilder multipartPutBuilder(String url) {
        final MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart(url);
        builder.with(request1 -> {
            request1.setMethod(HttpMethod.PUT.name());
            return request1;
        });
        return builder;
    }
}