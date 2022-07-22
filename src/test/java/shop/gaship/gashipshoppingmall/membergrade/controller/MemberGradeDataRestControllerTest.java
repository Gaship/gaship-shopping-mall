package shop.gaship.gashipshoppingmall.membergrade.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipshoppingmall.membergrade.dto.response.MemberGradeResponseDto;
import shop.gaship.gashipshoppingmall.membergrade.dummy.MemberGradeDtoDummy;
import shop.gaship.gashipshoppingmall.membergrade.service.MemberGradeService;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * 회원등급 rest controller 테스트.
 *
 * @author : 김세미
 * @since 1.0
 */
@WebMvcTest(MemberGradeDataRestController.class)
class MemberGradeDataRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberGradeService memberGradeService;

    @DisplayName("전체 회원등급 다건 조회")
    @Test
    void memberGradeDataList() throws Exception{
        MemberGradeResponseDto dummyMemberGradeResponseDto =
                MemberGradeDtoDummy.responseDummy("일반",
                        0L,
                        "12개월");

        when(memberGradeService.findMemberGrades())
                .thenReturn(List.of(dummyMemberGradeResponseDto));

        mockMvc.perform(get("/grade-data"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", equalTo(1)))
                .andExpect(jsonPath("$[0].name", equalTo("일반")));
    }

}