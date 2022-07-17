package shop.gaship.gashipshoppingmall.dayLabor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipshoppingmall.dayLabor.dto.request.CreateDayLaborRequestDto;
import shop.gaship.gashipshoppingmall.dayLabor.dto.request.FixDayLaborRequestDto;
import shop.gaship.gashipshoppingmall.dayLabor.dto.response.GetDayLaborResponseDto;
import shop.gaship.gashipshoppingmall.dayLabor.dummy.CreateDayLaborRequestDtoDummy;
import shop.gaship.gashipshoppingmall.dayLabor.dummy.FixDayLaborRequestDtoDummy;
import shop.gaship.gashipshoppingmall.dayLabor.dummy.GetDayLaborResponseDtoDummy;
import shop.gaship.gashipshoppingmall.dayLabor.service.DayLaborService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * packageName    : shop.gaship.gashipshoppingmall.dayLabor.controller fileName       :
 * DayLaborControllerTest author         : 유호철 date           : 2022/07/13 description    : 직역별물량
 * 컨트롤러 테스트 =========================================================== DATE              AUTHOR
 * NOTE ----------------------------------------------------------- 2022/07/13       유호철
 * 최초 생성
 */
@WebMvcTest(DayLaborController.class)
class DayLaborControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    DayLaborService service;

    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("지역별 물량 등록 테스트")
    @Test
    void postDayLabor() throws Exception {
        //given
        CreateDayLaborRequestDto dto = CreateDayLaborRequestDtoDummy.dummy();
        //when
        doNothing().when(service).addDayLabor(dto);

        mvc.perform(post("/dayLabors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(dto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());

        //then
        verify(service, times(1)).addDayLabor(any());
    }

    @DisplayName("지역별 물량등록 유효성 검사 실패 테스트")
    @Test
    void postDayLaborFail() throws Exception {
        //given
        CreateDayLaborRequestDto dto =
                new CreateDayLaborRequestDto(null,10);
        //when & then
        doNothing().when(service).addDayLabor(dto);

        mvc.perform(post("/dayLabors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(dto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("유효성 검사를 실패했습니다 : 지역번호를 입력하세요"))
                .andDo(print());

    }


    @DisplayName("지역별 물량 (물량값 수정)")
    @Test
    void modifyDayLabor() throws Exception {
        //given
        FixDayLaborRequestDto dto = FixDayLaborRequestDtoDummy.dummy();

        //when
        doNothing().when(service).modifyDayLabor(dto);

        mvc.perform(put("/dayLabors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andDo(print());

        //then
        verify(service, times(1)).modifyDayLabor(any());
    }

    @DisplayName("지역별 물량 (물량값 수정) 실패테스트")
    @Test
    void modifyDayLaborFail() throws Exception {
        //given
        FixDayLaborRequestDto dto =
               new FixDayLaborRequestDto(1,null);

        //when & then
        doNothing().when(service).modifyDayLabor(dto);

        mvc.perform(put("/dayLabors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("유효성 검사를 실패했습니다 : 최대물량을 입력하세요"))
                .andDo(print());

    }

    @DisplayName("지역별 일량 전체 조회")
    @Test
    void getAllLabor() throws Exception {
        //given
        GetDayLaborResponseDto d1 = GetDayLaborResponseDtoDummy.dummy1();
        GetDayLaborResponseDto d2 = GetDayLaborResponseDtoDummy.dummy2();
        List<GetDayLaborResponseDto> list = new ArrayList<>();
        list.add(d1);
        list.add(d2);

        //when
        when(service.findDayLabors()).thenReturn(list);

        mvc.perform(get("/dayLabors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].local").value(d1.getLocal()))
                .andExpect(jsonPath("$[0].maxLabor").value(d1.getMaxLabor()))
                .andExpect(jsonPath("$[1].local").value(d2.getLocal()))
                .andExpect(jsonPath("$[1].maxLabor").value(d2.getMaxLabor()));

        verify(service, times(1)).findDayLabors();
    }
}