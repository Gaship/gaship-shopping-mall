package shop.gaship.gashipshoppingmall.delivery.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipshoppingmall.delivery.dto.response.TrackingNoResponseDto;
import shop.gaship.gashipshoppingmall.delivery.service.impl.DeliveryServiceImpl;

/**
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
                   post("/eggplant/tracking-no")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(content))
               .andExpect(status().isOk());

        verify(deliveryService).addTrackingNo(any());
    }
}