package shop.gaship.gashipshoppingmall.membergrade.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipshoppingmall.membergrade.dto.MemberGradeDto;
import shop.gaship.gashipshoppingmall.membergrade.dto.MemberGradeRequestDto;
import shop.gaship.gashipshoppingmall.membergrade.dummy.MemberGradeDtoDummy;
import shop.gaship.gashipshoppingmall.membergrade.service.MemberGradeService;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * packageName    : shop.gaship.gashipshoppingmall.membergrade.controller
 * fileName       : MemberGradeRestControllerTest
 * author         : Semi Kim
 * date           : 2022/07/09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/09        Semi Kim       최초 생성
 */
@WebMvcTest(MemberGradeRestController.class)
class MemberGradeRestControllerTest {
    @MockBean
    private MemberGradeService memberGradeService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;
    private MemberGradeRequestDto testMemberGradeRequestDto;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        testMemberGradeRequestDto = MemberGradeDtoDummy.requestDummy("일반", 0L);
    }

    @Test
    void memberGradeAdd() throws Exception {
        mockMvc.perform(post("/grades")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testMemberGradeRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(memberGradeService).addMemberGrade(any());
    }

    @Test
    void memberGradeModify() throws Exception {
        int testMemberGradeNo = 1;

        mockMvc.perform(put("/grades/" + testMemberGradeNo)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testMemberGradeRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(memberGradeService).modifyMemberGrade(any(), any());
    }

    @Test
    void memberGradeRemove() throws Exception {
        int testMemberGradeNo = 1;

        mockMvc.perform(delete("/grades/" + testMemberGradeNo)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(memberGradeService).removeMemberGrade(any());
    }

    @Test
    void memberGradeDetails() throws Exception {
        // given
        int testMemberGradeNo = 1;
        MemberGradeDto testMemberGradeDto = MemberGradeDtoDummy.responseDummy("일반", 0L, "1개월");

        // mocking
        when(memberGradeService.findMemberGrade(any()))
                .thenReturn(testMemberGradeDto);

        // when&then
        mockMvc.perform(get("/grades/" + testMemberGradeNo)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", equalTo(testMemberGradeDto.getName())))
                .andExpect(jsonPath("$.accumulateAmount", equalTo(Integer.parseInt(testMemberGradeDto.getAccumulateAmount().toString()))))
                .andExpect(jsonPath("$.renewalPeriodStatusCode", equalTo(testMemberGradeDto.getRenewalPeriodStatusCode())));
    }

    @Test
    void memberGradeList() throws Exception {
        // given
        Pageable pageable = PageRequest.of(0, 10);

        // mocking
        when(memberGradeService.findMemberGrades(pageable))
                .thenReturn(List.of(MemberGradeDtoDummy.responseDummy("일반", 0L, "12개월")));

        // when&then
        mockMvc.perform(get("/grades")
                        .queryParam("size", "10")
                        .queryParam("page", "0")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}