package shop.gaship.gashipshoppingmall.gradehistory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipshoppingmall.gradehistory.dto.request.GradeHistoryAddRequestDto;
import shop.gaship.gashipshoppingmall.gradehistory.dto.request.GradeHistoryFindRequestDto;
import shop.gaship.gashipshoppingmall.gradehistory.dto.response.GradeHistoryResponseDto;
import shop.gaship.gashipshoppingmall.gradehistory.dummy.GradeHistoryDtoDummy;
import shop.gaship.gashipshoppingmall.gradehistory.service.GradeHistoryService;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;
import shop.gaship.gashipshoppingmall.response.PageResponse;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 등급이력 Rest Controller 테스트.
 *
 * @author : 김세미
 * @since 1.0
 */
@WebMvcTest(GradeHistoryRestController.class)
class GradeHistoryRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GradeHistoryService gradeHistoryService;

    @DisplayName("등급이력 등록")
    @Test
    void gradeHistoryAdd() throws Exception {
        GradeHistoryAddRequestDto requestDummy = GradeHistoryDtoDummy.addRequestDummy();

        mockMvc.perform(post("/api/grade-histories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDummy)))
                .andExpect(status().isCreated());

        verify(gradeHistoryService).addGradeHistory(any());
    }

    @DisplayName("등급이력 등록시" +
            "요청 data 의 gradeName 이 NULL 인 경우")
    @Test
    void gradeHistoryAdd_whenGradeNameIsNull_throwExp() throws Exception {
        GradeHistoryAddRequestDto requestDummy = GradeHistoryDtoDummy.addRequestDummy();
        ReflectionTestUtils.setField(requestDummy, "gradeName", null);

        mockMvc.perform(post("/api/grade-histories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDummy)))
                .andExpect(status().is4xxClientError());

        verify(gradeHistoryService, never()).addGradeHistory(any());
    }

    @DisplayName("등급이력 등록시" +
            "요청 data 의 member 를 찾을 수 없는 경우")
    @Test
    void exceptionHandler() throws Exception{
        GradeHistoryAddRequestDto requestDummy = GradeHistoryDtoDummy.addRequestDummy();
        MemberNotFoundException exception = new MemberNotFoundException();
        String errorMessage = exception.getMessage();

        doThrow(exception)
                .when(gradeHistoryService).addGradeHistory(any());

        mockMvc.perform(post("/api/grade-histories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDummy)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message", equalTo(errorMessage)));

        verify(gradeHistoryService).addGradeHistory(any());
    }

    @DisplayName("멤버를 통한 등급이력 다건 조회 테스트")
    @Test
    void gradeHistoryList() throws Exception{
        GradeHistoryResponseDto responseDtoDummy = GradeHistoryDtoDummy.responseDummy();
        GradeHistoryFindRequestDto requestDtoDummy = GradeHistoryDtoDummy.findRequestDummy();

        when(gradeHistoryService.findGradeHistories(any()))
                .thenReturn(new PageResponse<>(
                        new PageImpl<>(List.of(responseDtoDummy),
                                PageRequest.of(requestDtoDummy.getPage(),
                                        requestDtoDummy.getSize()), 2)));

        mockMvc.perform(get("/api/grade-histories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDtoDummy)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.page",equalTo(requestDtoDummy.getPage())))
                .andExpect(jsonPath("$.size", equalTo(requestDtoDummy.getSize())));
    }
}