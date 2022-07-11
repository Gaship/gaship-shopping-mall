package shop.gaship.gashipshoppingmall.membergrade.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipshoppingmall.membergrade.dto.MemberGradeDto;
import shop.gaship.gashipshoppingmall.membergrade.request.MemberGradeRequest;
import shop.gaship.gashipshoppingmall.membergrade.service.MemberGradeService;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static shop.gaship.gashipshoppingmall.membergrade.utils.CreateTestUtils.createTestMemberGradeDto;
import static shop.gaship.gashipshoppingmall.membergrade.utils.CreateTestUtils.createTestMemberGradeRequest;

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
@WebMvcTest
class MemberGradeRestControllerTest {
    @MockBean
    private MemberGradeService memberGradeService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;
    private MemberGradeRequest testMemberGradeRequest;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        testMemberGradeRequest = createTestMemberGradeRequest("일반", 0L);
    }

    @Test
    void memberGradeAdd() throws Exception {
        mockMvc.perform(post("/grades")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testMemberGradeRequest))
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
                        .content(objectMapper.writeValueAsString(testMemberGradeRequest))
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
        MemberGradeDto testMemberGradeDto = createTestMemberGradeDto("일반", 0L, "1개월");

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
}