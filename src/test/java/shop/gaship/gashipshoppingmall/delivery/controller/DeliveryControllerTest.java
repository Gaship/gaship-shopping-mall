package shop.gaship.gashipshoppingmall.delivery.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipshoppingmall.delivery.dto.response.DeliveryInfoStatusResponseDto;
import shop.gaship.gashipshoppingmall.delivery.dto.response.TrackingNoResponseDto;
import shop.gaship.gashipshoppingmall.delivery.service.impl.DeliveryServiceImpl;

/**
 * 배송 관련 controller 테스트 클래스 입니다.
 *
 * @author : 조재철
 * @since 1.0
 */
@WebMvcTest(DeliveryController.class)
class DeliveryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    DeliveryServiceImpl deliveryService;

    @Test
    void createTrackingNo() throws Exception {

        // given
        doNothing().when(deliveryService).createTrackingNo(1);

        // when then
        mockMvc.perform(
                   get("/api/delivery/tracking-no/" + 1))
               .andExpect(status().isOk());

        verify(deliveryService).createTrackingNo(1);
    }

    @Test
    void addTrackingNo() throws Exception {

        // given
        doNothing().when(deliveryService).addTrackingNo(any());

        TrackingNoResponseDto trackingNoResponseDto = new TrackingNoResponseDto("1", "1");

        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(trackingNoResponseDto);

        // when then
        mockMvc.perform(
                   post("/api/delivery/add-tracking-no")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(content))
               .andExpect(status().isOk());

        verify(deliveryService).addTrackingNo(any());
    }

    @Test
    void changeDeliveryStatus() throws Exception {
        // given
        doNothing().when(deliveryService).changeDeliveryStatus(any());

        DeliveryInfoStatusResponseDto deliveryInfoStatusResponseDto = new DeliveryInfoStatusResponseDto();
        ReflectionTestUtils.setField(deliveryInfoStatusResponseDto, "orderProductNo", "123");
        ReflectionTestUtils.setField(deliveryInfoStatusResponseDto, "status", "배송 중");
        ReflectionTestUtils.setField(deliveryInfoStatusResponseDto, "arrivalTime", LocalDateTime.now());

        ObjectMapper objectMapper = JsonMapper.builder()
                                              .addModule(new JavaTimeModule())
                                              .build();

        String content = objectMapper.writeValueAsString(deliveryInfoStatusResponseDto);

        // when then
        mockMvc.perform(
                   patch("/api/delivery/change-delivery-info")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(content))
               .andExpect(status().isOk());

        verify(deliveryService).changeDeliveryStatus(any());
    }
}