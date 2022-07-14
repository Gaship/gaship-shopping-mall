package shop.gaship.gashipshoppingmall.repairSchedule.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import shop.gaship.gashipshoppingmall.repairSchedule.dto.request.CreateScheduleRequestDto;
import shop.gaship.gashipshoppingmall.repairSchedule.dto.request.ModifyScheduleRequestDto;
import shop.gaship.gashipshoppingmall.repairSchedule.dto.request.SchedulePageRequestDto;
import shop.gaship.gashipshoppingmall.repairSchedule.dto.response.GetRepairScheduleResponseDto;
import shop.gaship.gashipshoppingmall.repairSchedule.dummy.CreateScheduleRequestDtoDummy;
import shop.gaship.gashipshoppingmall.repairSchedule.dummy.GetRepairScheduleResponseDtoDummy;
import shop.gaship.gashipshoppingmall.repairSchedule.service.RepairScheduleService;

/**
 *packageName    : shop.gaship.gashipshoppingmall.repairSchedule.controller
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
        doNothing().when(service).registerSchedule(dto);

        mvc.perform(post("/repair-schedule")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding(StandardCharsets.UTF_8)
            .content(objectMapper.writeValueAsString(dto))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andDo(print());

        verify(service, times(1)).registerSchedule(dto);
    }

    @DisplayName("스케줄 건수 수정")
    @Test
    void fixRepairSchedule() throws Exception {
        //given
        ModifyScheduleRequestDto dto = new ModifyScheduleRequestDto(10, LocalDate.now(), 1);

        //when & then
        doNothing().when(service).modifySchedule(dto);

        mvc.perform(put("/repair-schedule")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding(StandardCharsets.UTF_8)
            .content(objectMapper.writeValueAsString(dto))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());

        //then
        verify(service,times(1)).modifySchedule(any());
    }

    @DisplayName("모든 스케줄 조회 테스트")
    @Test
    void getAllSchedule() throws Exception {
        //given
        SchedulePageRequestDto dto = new SchedulePageRequestDto(1,10);
        PageRequest pageRequest = PageRequest.of(1, 10);
        List<GetRepairScheduleResponseDto> list = new ArrayList<>();
        GetRepairScheduleResponseDto responseDto1 = GetRepairScheduleResponseDtoDummy.dummy1();
        GetRepairScheduleResponseDto responseDto2 = GetRepairScheduleResponseDtoDummy.dummy2();
        list.add(responseDto1);
        list.add(responseDto2);
        Page<GetRepairScheduleResponseDto> pages = new PageImpl<>(list, pageRequest,
            list.size());
        //when
        when(service.getAllSchedule(dto))
            .thenReturn(pages);

        mvc.perform(get("/repair-schedule")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
                .characterEncoding(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());

        verify(service, times(1)).getAllSchedule(dto);
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
        when(service.findScheduleByDate(now))
            .thenReturn(list);

       mvc.perform(get("/repair-schedule/date")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(now))
                .characterEncoding(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());

        //then
        verify(service, times(1)).findScheduleByDate(any());
    }
}