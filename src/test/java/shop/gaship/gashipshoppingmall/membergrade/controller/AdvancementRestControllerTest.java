package shop.gaship.gashipshoppingmall.membergrade.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipshoppingmall.error.ExceptionAdviceController;
import shop.gaship.gashipshoppingmall.error.adapter.LogAndCrashAdapter;
import shop.gaship.gashipshoppingmall.membergrade.dto.request.RenewalMemberGradeRequestDto;
import shop.gaship.gashipshoppingmall.membergrade.dto.response.AdvancementTargetResponseDto;
import shop.gaship.gashipshoppingmall.membergrade.dummy.AdvancementTargetDummy;
import shop.gaship.gashipshoppingmall.membergrade.dummy.RenewalMemberGradeRequestDummy;
import shop.gaship.gashipshoppingmall.membergrade.service.AdvancementService;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 회원승급 관련 rest controller 테스트.
 *
 * @author : 김세미
 * @since 1.0
 */
@WebMvcTest(AdvancementRestController.class)
@Import({ExceptionAdviceController.class})
class AdvancementRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    LogAndCrashAdapter logAndCrashAdapter;

    @MockBean
    private AdvancementService advancementService;

    @DisplayName("등급갱신 대상회원 다건 조회")
    @Test
    void advancementTargetList() throws Exception {
        AdvancementTargetResponseDto dummyResponseDto =
                AdvancementTargetDummy.dummy();
        String testRenewalGradeDate = "2022-07-27";

        when(advancementService.findTargetsByRenewalGradeDate(any()))
                .thenReturn(List.of(dummyResponseDto));

        mockMvc.perform(get("/api/member-grades/advancement/target")
                        .queryParam("renewalGradeDate", testRenewalGradeDate    )
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", equalTo(1)))
                .andExpect(jsonPath("$[0].memberNo", equalTo(1)));
    }

    @DisplayName("회원의 등급 수정 및 등급이력 등록")
    @Test
    void advancementMemberAdd() throws Exception{
        RenewalMemberGradeRequestDto dummyRequestDto =
                RenewalMemberGradeRequestDummy.dummy();

        doNothing().when(advancementService).renewalMemberGrade(any());

        mockMvc.perform(post("/api/member-grades/advancement")
                .content(objectMapper.writeValueAsString(dummyRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
