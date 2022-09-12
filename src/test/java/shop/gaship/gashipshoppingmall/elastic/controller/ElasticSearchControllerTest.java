package shop.gaship.gashipshoppingmall.elastic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipshoppingmall.elastic.documents.ElasticProduct;
import shop.gaship.gashipshoppingmall.elastic.service.ElasticService;
import shop.gaship.gashipshoppingmall.error.ExceptionAdviceController;
import shop.gaship.gashipshoppingmall.error.adapter.LogAndCrashAdapter;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 설명작성란
 *
 * @author : 유호철
 * @since 1.0
 */

@WebMvcTest(ElasticSearchController.class)
@Import({ExceptionAdviceController.class})
class ElasticSearchControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ElasticService service;

    @MockBean
    LogAndCrashAdapter logAndCrashAdapter;

    ElasticProduct product;

    @BeforeEach
    void setUp() {
        product = new ElasticProduct();
        ReflectionTestUtils.setField(product, "id", 1);
        ReflectionTestUtils.setField(product, "name", "test");
        ReflectionTestUtils.setField(product, "code", "c1");
    }

    @DisplayName("엘라스틱 서치 이름 검색 관련 테스트 ")
    @Test
    void elasticSearchTestByName() throws Exception {
        when(service.findName(anyString()))
            .thenReturn(List.of(product));

        mvc.perform(get("/api/search")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .queryParam("productName", objectMapper.writeValueAsString(product.getName())))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[0].id").value(product.getId()))
            .andExpect(jsonPath("$.[0].name").value(product.getName()))
            .andExpect(jsonPath("$.[0].code").value(product.getCode()))
            .andDo(print());

        verify(service, times(1))
            .findName(anyString());
    }

    @DisplayName("엘라스틱 서치 코드 검색 관련 테스트")
    @Test
    void elasticSearchTestByCode() throws Exception {
        when(service.findCode(anyString()))
            .thenReturn(List.of(product));

        mvc.perform(get("/api/search")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .queryParam("productCode", objectMapper.writeValueAsString(product.getCode())))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[0].id").value(product.getId()))
            .andExpect(jsonPath("$.[0].name").value(product.getName()))
            .andExpect(jsonPath("$.[0].code").value(product.getCode()))
            .andDo(print());

        verify(service, times(1))
            .findCode(anyString());
    }

}
