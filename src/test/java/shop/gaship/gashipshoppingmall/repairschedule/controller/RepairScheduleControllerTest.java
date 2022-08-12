package shop.gaship.gashipshoppingmall.repairschedule.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipshoppingmall.repairschedule.dto.request.CreateScheduleRequestDto;
import shop.gaship.gashipshoppingmall.repairschedule.dto.request.ModifyScheduleRequestDto;
import shop.gaship.gashipshoppingmall.repairschedule.dto.response.GetRepairScheduleResponseDto;
import shop.gaship.gashipshoppingmall.repairschedule.dummy.CreateScheduleRequestDtoDummy;
import shop.gaship.gashipshoppingmall.repairschedule.dummy.GetRepairScheduleResponseDtoDummy;
import shop.gaship.gashipshoppingmall.repairschedule.service.RepairScheduleService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * packageName    : shop.gaship.gashipshoppingmall.repairSchedule.controller
 * fileName       : RepairScheduleControllerTest
 * author         : 유호철
 * date           : 2022/07/14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/14       유호철       최초 생성
 */
@WebMvcTest(RepairScheduleController.class)
class RepairScheduleControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    RepairScheduleService service;

    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("스케줄 생성 테스트")
    @Test
    void addRepairSchedule() throws Exception {
        //given
        CreateScheduleRequestDto dto = CreateScheduleRequestDtoDummy.dummy();

        //when & then
        doNothing().when(service).addRepairSchedule(dto);

        mvc.perform(post("/repair-schedule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(dto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());

        verify(service, times(1)).addRepairSchedule(any(CreateScheduleRequestDto.class));
    }

    @DisplayName("스케줄 생성실패 테스트")
    @Test
    void addRepairScheduleFail() throws Exception {
        //given
        CreateScheduleRequestDto dto = new CreateScheduleRequestDto(null,1,10);

        //when & then
        doNothing().when(service).addRepairSchedule(dto);

        mvc.perform(post("/repair-schedule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(dto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("일자를 기입해주세요"))
                .andDo(print());
    }

    @DisplayName("스케줄 건수 수정")
    @Test
    void fixRepairSchedule() throws Exception {
        //given
        ModifyScheduleRequestDto dto = new ModifyScheduleRequestDto();
        ReflectionTestUtils.setField(dto, "labor", 10);
        ReflectionTestUtils.setField(dto, "date", LocalDate.now());
        ReflectionTestUtils.setField(dto, "localNo", 1);

        //when & then
        doNothing().when(service).modifyRepairSchedule(dto);

        mvc.perform(put("/repair-schedule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(dto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        //then
        verify(service, times(1)).modifyRepairSchedule(any());
    }

    @DisplayName("스케줄 건수 수정실패 ")
    @Test
    void fixRepairScheduleFail() throws Exception {
        //given
        ModifyScheduleRequestDto dto = new ModifyScheduleRequestDto();
        ReflectionTestUtils.setField(dto, "labor", 10);
        ReflectionTestUtils.setField(dto, "date", null);
        ReflectionTestUtils.setField(dto, "localNo", 1);
        //when & then
        doNothing().when(service).modifyRepairSchedule(dto);

        mvc.perform(put("/repair-schedule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(dto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("일자를 기입해주세요"))
                .andDo(print());

    }

    @DisplayName("모든 스케줄 조회 테스트")
    @Test
    void getAllSchedule() throws Exception {
        //given
        PageRequest pageRequest = PageRequest.of(1, 10);
        List<GetRepairScheduleResponseDto> list = new ArrayList<>();
        GetRepairScheduleResponseDto responseDto1 = GetRepairScheduleResponseDtoDummy.dummy1();
        GetRepairScheduleResponseDto responseDto2 = GetRepairScheduleResponseDtoDummy.dummy2();
        list.add(responseDto1);
        list.add(responseDto2);
        Page<GetRepairScheduleResponseDto> pages = new PageImpl<>(list, pageRequest,
                list.size());
        //when
        when(service.findRepairSchedules(pageRequest.getPageNumber(),pageRequest.getPageSize()))
                .thenReturn(pages);

        mvc.perform(get("/repair-schedule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .queryParam("page", objectMapper.writeValueAsString(pageRequest.getPageSize()))
                        .queryParam("size", objectMapper.writeValueAsString(pageRequest.getPageNumber()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        verify(service, times(1)).findRepairSchedules(any(Integer.class), any(Integer.class));
    }

    @Test
    void getScheduleByDate() throws Exception {
        //given
        List<GetRepairScheduleResponseDto> list = new ArrayList<>();
        GetRepairScheduleResponseDto responseDto1 = GetRepairScheduleResponseDtoDummy.dummy1();
        GetRepairScheduleResponseDto responseDto2 = GetRepairScheduleResponseDtoDummy.dummy2();
        LocalDate now = LocalDate.now();
        list.add(responseDto1);
        list.add(responseDto2);
        //when & then
        when(service.findSchedulesByDate(now))
                .thenReturn(list);

        mvc.perform(get("/repair-schedule/date")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .queryParam("date",now.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        //then
        verify(service, times(1)).findSchedulesByDate(any());
    }
}
