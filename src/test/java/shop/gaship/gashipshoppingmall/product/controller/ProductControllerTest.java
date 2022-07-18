package shop.gaship.gashipshoppingmall.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipshoppingmall.product.dto.response.ProductResponseDto;
import shop.gaship.gashipshoppingmall.product.dummy.ProductResponseDtoDummy;
import shop.gaship.gashipshoppingmall.product.service.ProductService;
import shop.gaship.gashipshoppingmall.product.service.impl.ProductServiceImpl;
import shop.gaship.gashipshoppingmall.repairSchedule.controller.RepairScheduleController;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;import static org.mockito.Mockito.when;

/**
 * 상품 컨트롤러 테스트 입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
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
    @BeforeEach
    void setUp() {
        responseDto = ProductResponseDtoDummy.dummy();
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
                .andDo(print());
        //then
        verify(service, times(1)).findProductByCode(any());
    }
}