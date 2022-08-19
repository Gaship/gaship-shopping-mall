package shop.gaship.gashipshoppingmall.addresslocal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipshoppingmall.addresslocal.dto.request.ModifyAddressRequestDto;
import shop.gaship.gashipshoppingmall.addresslocal.dto.response.GetAddressLocalResponseDto;
import shop.gaship.gashipshoppingmall.addresslocal.dummy.GetAddressLocalResponseDtoDummy;
import shop.gaship.gashipshoppingmall.addresslocal.dummy.ModifyAddressRequestDtoDummy;
import shop.gaship.gashipshoppingmall.addresslocal.service.AddressLocalService;
import shop.gaship.gashipshoppingmall.util.PageResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * packageName    : shop.gaship.gashipshoppingmall.addressLocal.controller
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

        mvc.perform(put("/api/addressLocals")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(objectMapper.writeValueAsString(dto))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());

        //then
        verify(service, times(1)).modifyLocalDelivery(any());
    }

    @DisplayName("지역 배달가능 여부 수정 실패 테스트")
    @Test
    void modifyAddressLocalFailTest() throws Exception {
        //given
        ModifyAddressRequestDto dto = new ModifyAddressRequestDto(null, true);

        //when & then
        mvc.perform(put("/api/addressLocals")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(objectMapper.writeValueAsString(dto))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError())
            .andExpect(jsonPath("$.message").value("지역을 입력하세요"))
            .andDo(print());
    }

    @DisplayName("주소지 검색 테스트")
    @Test
    void findAddressLocalTest() throws Exception {
        //given
        GetAddressLocalResponseDto dto = GetAddressLocalResponseDtoDummy.dummy();
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<GetAddressLocalResponseDto> list = new ArrayList<>();
        list.add(dto);

        PageImpl<GetAddressLocalResponseDto> page = new PageImpl<>(list, pageRequest, pageRequest.getPageSize());
        PageResponse<GetAddressLocalResponseDto> pages = new PageResponse<>(page);
        //when
        when(service.findAddressLocals(dto.getAddressName(), pageRequest))
            .thenReturn(pages);

        mvc.perform(get("/api/addressLocals")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .queryParam("address", dto.getAddressName())
                .queryParam("page", objectMapper.writeValueAsString(pageRequest.getPageNumber()))
                .queryParam("size", objectMapper.writeValueAsString(pageRequest.getPageSize()))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content.[0].upperAddressName").value(dto.getUpperAddressName()))
            .andExpect(jsonPath("$.content.[0].addressName").value(dto.getAddressName()))
            .andDo(print());

        //then
        verify(service, times(1)).findAddressLocals(dto.getAddressName(), pageRequest);

    }
}
