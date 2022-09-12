package shop.gaship.gashipshoppingmall.orderproduct.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipshoppingmall.error.ExceptionAdviceController;
import shop.gaship.gashipshoppingmall.error.adapter.LogAndCrashAdapter;
import shop.gaship.gashipshoppingmall.orderproduct.dto.response.OrderProductDetailResponseDto;
import shop.gaship.gashipshoppingmall.orderproduct.dto.response.OrderProductResponseDto;
import shop.gaship.gashipshoppingmall.orderproduct.service.OrderProductService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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

@WebMvcTest(OrderProductController.class)
@Import({ExceptionAdviceController.class})
class OrderProductControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    LogAndCrashAdapter logAndCrashAdapter;

    @MockBean
    OrderProductService orderProductService;

    @DisplayName("멤버 번호를 통해 상품을 조회")
    @Test
    void orderproductFindByMember() throws Exception {
        List<OrderProductResponseDto> list = new ArrayList<>();
        OrderProductResponseDto dto = new OrderProductResponseDto(1, 100L, LocalDateTime.now(), "유유",
            "receipt");
        PageRequest pageRequest = PageRequest.of(0, 10);
        list.add(dto);
        PageImpl<OrderProductResponseDto> page = new PageImpl<>(list, pageRequest, 1);

        when(orderProductService.findMemberOrders(anyInt(), any()))
            .thenReturn(page);

        mvc.perform(get("/api/order-products/member/" + 1)
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("page", objectMapper.writeValueAsString(pageRequest.getPageNumber()))
                .queryParam("size", objectMapper.writeValueAsString(pageRequest.getPageSize())))
            .andExpect(jsonPath("$.content.[0].orderNo").value(dto.getOrderNo()))
            .andExpect(jsonPath("$.content.[0].totalOrderAmount").value(dto.getTotalOrderAmount()))
            .andExpect(jsonPath("$.content.[0].receiptName").value(dto.getReceiptName()))
            .andExpect(jsonPath("$.content.[0].receiptPhoneNumber").value(dto.getReceiptPhoneNumber()))

            .andExpect(status().isOk())
            .andDo(print());
    }

    @DisplayName("상품번호에 해당하는 상품 상세표시")
    @Test
    void orderProductDetail() throws Exception {
        PageRequest pageRequest = PageRequest.of(0, 10);
        OrderProductDetailResponseDto dto =
            new OrderProductDetailResponseDto(1, 1, 1, "product", 1L, "status", "uuid", "color", "manufacturer"
                , "korea", "seller", "importer", "qq", "explain", 1, "address", "zipCode", "name", "010", "0101", LocalDateTime.now(), 10L, "");
        dto.setFilePath("file");
        List<OrderProductDetailResponseDto> list = List.of(dto);
        PageImpl<OrderProductDetailResponseDto> page = new PageImpl<>(list);
        when(orderProductService.findMemberOrderProductDetail(anyInt(), anyInt(), any(Pageable.class)))
            .thenReturn(page);

        mvc.perform(get("/api/order-products/{orderProductNo}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("memberNo", objectMapper.writeValueAsString(1))
                .queryParam("page", objectMapper.writeValueAsString(pageRequest.getPageNumber()))
                .queryParam("size", objectMapper.writeValueAsString(pageRequest.getPageSize())))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].productNo").value(objectMapper.writeValueAsString(dto.getProductNo())))
            .andExpect(jsonPath("$.content[0].orderNo").value(objectMapper.writeValueAsString(dto.getOrderNo())))
            .andExpect(jsonPath("$.content[0].productName").value((dto.getProductName())))
            .andExpect(jsonPath("$.content[0].totalOrderAmount").value((dto.getTotalOrderAmount())))
            .andExpect(jsonPath("$.content[0].orderProductStatus").value((dto.getOrderProductStatus())))
            .andExpect(jsonPath("$.content[0].trackingNo").value((dto.getTrackingNo())))
            .andExpect(jsonPath("$.content[0].color").value((dto.getColor())))
            .andExpect(jsonPath("$.content[0].manufacturer").value((dto.getManufacturer())))
            .andExpect(jsonPath("$.content[0].manufacturerCountry").value((dto.getManufacturerCountry())))
            .andExpect(jsonPath("$.content[0].seller").value((dto.getSeller())))
            .andExpect(jsonPath("$.content[0].importer").value((dto.getImporter())))
            .andExpect(jsonPath("$.content[0].qualityAssuranceStandard").value((dto.getQualityAssuranceStandard())))
            .andExpect(jsonPath("$.content[0].explanation").value((dto.getExplanation())))
            .andExpect(jsonPath("$.content[0].filePath").value(("file")))
            .andExpect(jsonPath("$.content[0].memberNo").value(dto.getMemberNo()))
            .andDo(print());
    }

}
