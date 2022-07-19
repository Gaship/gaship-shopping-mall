package shop.gaship.gashipshoppingmall.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductCreateRequestDto;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @DisplayName("상품 생성 post 요청")
    @Test
    void productAdd() throws Exception {
        File file = new File("src/test/resources/sample.jpg");
        MockMultipartFile multipartFile = new MockMultipartFile(
                "image", "sample.jpg", "multipart/mixed", new FileInputStream(file));
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
}