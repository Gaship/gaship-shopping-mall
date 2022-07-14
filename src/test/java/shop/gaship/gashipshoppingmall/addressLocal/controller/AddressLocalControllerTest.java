package shop.gaship.gashipshoppingmall.addressLocal.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import shop.gaship.gashipshoppingmall.addressLocal.dto.request.AddressSearchRequestDto;
import shop.gaship.gashipshoppingmall.addressLocal.dto.request.ModifyAddressRequestDto;
import shop.gaship.gashipshoppingmall.addressLocal.dto.response.GetAddressLocalResponseDto;
import shop.gaship.gashipshoppingmall.addressLocal.dummy.GetAddressLocalResponseDtoDummy;
import shop.gaship.gashipshoppingmall.addressLocal.dummy.ModifyAddressRequestDtoDummy;
import shop.gaship.gashipshoppingmall.addressLocal.service.AddressLocalService;

/**
 *packageName    : shop.gaship.gashipshoppingmall.addressLocal.controller
 * fileName       : AddressLocalControllerTest
 * author         : 유호철
 * date           : 2022/07/14
 * description    : 주소지 컨트롤러 테스트 클래스
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/14       유호철       최초 생성
 */

@WebMvcTest(AddressLocalController.class)
class AddressLocalControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    AddressLocalService service;

    @Autowired
    ObjectMapper objectMapper;


    @DisplayName("지역 배달가능 불가능 여부 수정 테스트")
    @Test
    void modifyAddressLocalIsDelivery() throws Exception {
        //given
        ModifyAddressRequestDto dto = ModifyAddressRequestDtoDummy.dummy();

        //when & then
        doNothing().when(service).modifyLocalDelivery(dto);

        mvc.perform(put("/addressLocals")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(objectMapper.writeValueAsString(dto))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());

        //then
        verify(service,times(1)).modifyLocalDelivery(any());
    }

    @DisplayName("주소지 검색 테스트")
    @Test
    void findAddressLocalTest() throws Exception {
        //given
        GetAddressLocalResponseDto dto = GetAddressLocalResponseDtoDummy.dummy();
        AddressSearchRequestDto requestDto = new AddressSearchRequestDto(dto.getUpperAddressName());

        List<GetAddressLocalResponseDto> list = new ArrayList<>();
        list.add(dto);
        //when
        when(service.searchAddress(requestDto))
            .thenReturn(list);

        MvcResult mvcResult = mvc.perform(get("/addressLocals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto))
                .characterEncoding(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();

        //then
        verify(service, times(1)).searchAddress(any());
    }
}