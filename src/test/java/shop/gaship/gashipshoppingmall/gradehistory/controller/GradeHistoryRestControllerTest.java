package shop.gaship.gashipshoppingmall.gradehistory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipshoppingmall.gradehistory.dto.request.GradeHistoryAddRequestDto;
import shop.gaship.gashipshoppingmall.gradehistory.dummy.GradeHistoryDtoDummy;
import shop.gaship.gashipshoppingmall.gradehistory.service.GradeHistoryService;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        GradeHistoryAddRequestDto requestDummy = GradeHistoryDtoDummy.requestDto();

        mockMvc.perform(post("/gradehistory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDummy)))
                .andExpect(status().isCreated());

        verify(gradeHistoryService).addGradeHistory(any());
    }

    @DisplayName("등급이력 등록시" +
            "요청 data 의 gradeName 이 NULL 인 경우")
    @Test
    void gradeHistoryAdd_whenGradeNameIsNull_throwExp() throws Exception {
        GradeHistoryAddRequestDto requestDummy = GradeHistoryDtoDummy.requestDto();
        ReflectionTestUtils.setField(requestDummy, "gradeName", null);

        mockMvc.perform(post("/gradehistory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDummy)))
                .andExpect(status().is4xxClientError());

        verify(gradeHistoryService, never()).addGradeHistory(any());
    }

    @DisplayName("등급이력 등록시" +
            "요청 data 의 member 를 찾을 수 없는 경우")
    @Test
    void exceptionHandler() throws Exception{
        GradeHistoryAddRequestDto requestDummy = GradeHistoryDtoDummy.requestDto();
        MemberNotFoundException exception = new MemberNotFoundException();
        String errorMessage = exception.getMessage();

        doThrow(exception)
                .when(gradeHistoryService).addGradeHistory(any());

        mockMvc.perform(post("/gradehistory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDummy)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", equalTo(errorMessage)));

        verify(gradeHistoryService).addGradeHistory(any());
    }

}