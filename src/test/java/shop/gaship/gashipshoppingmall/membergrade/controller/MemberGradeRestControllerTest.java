package shop.gaship.gashipshoppingmall.membergrade.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipshoppingmall.membergrade.dto.request.MemberGradeModifyRequestDto;
import shop.gaship.gashipshoppingmall.membergrade.dto.response.MemberGradeResponseDto;
import shop.gaship.gashipshoppingmall.membergrade.dto.request.MemberGradeAddRequestDto;
import shop.gaship.gashipshoppingmall.membergrade.dummy.MemberGradeDtoDummy;
import shop.gaship.gashipshoppingmall.membergrade.exception.MemberGradeNotFoundException;
import shop.gaship.gashipshoppingmall.membergrade.service.MemberGradeService;
import shop.gaship.gashipshoppingmall.response.PageResponse;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 회원등급 RestController 테스트.
 *
 * @author : 김세미
 * @since 1.0
 */
@WebMvcTest(MemberGradeRestController.class)
class MemberGradeRestControllerTest {
    @MockBean
    private MemberGradeService memberGradeService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private MemberGradeAddRequestDto addRequestDummy;

    @BeforeEach
    void setUp() {
        addRequestDummy = MemberGradeDtoDummy.requestDummy("일반", 0L);
    }

    @DisplayName("회원등급을 등록하는 경우")
    @Test
    void memberGradeAdd() throws Exception {
        mockMvc.perform(post("/api/member-grades")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addRequestDummy))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(memberGradeService).addMemberGrade(any());
    }

    @DisplayName("회원등급 등록시 " +
            "name 이 null 인 경우")
    @Test
    void memberGradeAdd_whenNameIsNull() throws Exception{
        addRequestDummy.setName(null);

        mockMvc.perform(post("/api/member-grades")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addRequestDummy))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

        verify(memberGradeService, never()).addMemberGrade(any());
    }

    @DisplayName("회원등급을 수정하는 경우")
    @Test
    void memberGradeModify() throws Exception {
        MemberGradeModifyRequestDto requestDummy = MemberGradeDtoDummy.modifyRequestDummy(1, "새싹", 0L);

        mockMvc.perform(put("/api/member-grades")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDummy))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(memberGradeService).modifyMemberGrade(any());
    }

    @DisplayName("회원등급 수정시 " +
            "request 의 name 이 null 인 경우")
    @Test
    void memberGradeModify_whenNameIsNull() throws Exception {
        MemberGradeModifyRequestDto requestDummy = MemberGradeDtoDummy.modifyRequestDummy(1, null, 0L);

        mockMvc.perform(put("/api/member-grades")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDummy))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

        verify(memberGradeService, never()).modifyMemberGrade(any());
    }

    @DisplayName("회원등급 삭제하는 경우")
    @Test
    void memberGradeRemove() throws Exception {
        int testMemberGradeNo = 1;

        mockMvc.perform(delete("/api/member-grades/" + testMemberGradeNo)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(memberGradeService).removeMemberGrade(any());
    }

    @DisplayName("회원등급 단건 조회하는 경우")
    @Test
    void memberGradeDetails() throws Exception {
        int testMemberGradeNo = 1;
        MemberGradeResponseDto testMemberGradeResponseDto = MemberGradeDtoDummy.responseDummy("일반", 0L, "1개월");

        when(memberGradeService.findMemberGrade(any()))
                .thenReturn(testMemberGradeResponseDto);

        mockMvc.perform(get("/api/member-grades/" + testMemberGradeNo)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", equalTo(testMemberGradeResponseDto.getName())))
                .andExpect(jsonPath("$.accumulateAmount", equalTo(Integer.parseInt(testMemberGradeResponseDto.getAccumulateAmount().toString()))))
                .andExpect(jsonPath("$.renewalPeriodStatusCode", equalTo(testMemberGradeResponseDto.getRenewalPeriodStatusCode())));
    }

    @DisplayName("회원등급 다건 조회하는 경우")
    @Test
    void memberGradeList() throws Exception {
        int page = 1;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        MemberGradeResponseDto dummyMemberGradeResponseDto =
                MemberGradeDtoDummy.responseDummy("일반",
                        0L,
                        "12개월");

        when(memberGradeService.findMemberGrades(pageable))
                .thenReturn(new PageResponse<>(
                        new PageImpl<>(List.of(dummyMemberGradeResponseDto), pageable, 1)));

        mockMvc.perform(get("/api/member-grades")
                        .queryParam("size", "10")
                        .queryParam("page", "1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.page", equalTo(page)))
                .andExpect(jsonPath("$.size", equalTo(size)));
    }

    @DisplayName("전체 회원등급 다건 조회")
    @Test
    void memberGradeDataList() throws Exception{
        MemberGradeResponseDto dummyMemberGradeResponseDto =
                MemberGradeDtoDummy.responseDummy("일반",
                        0L,
                        "12개월");

        when(memberGradeService.findMemberGrades())
                .thenReturn(List.of(dummyMemberGradeResponseDto));

        mockMvc.perform(get("/api/member-grades"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", equalTo(1)))
                .andExpect(jsonPath("$[0].name", equalTo("일반")));
    }

    @DisplayName("Exception Handler 테스트")
    @Test
    void exceptionHandler_whenThrowMemberGradeNotFoundException() throws Exception {
        int testMemberGradeNo = 100;
        MemberGradeNotFoundException memberGradeNotFoundException = new MemberGradeNotFoundException();
        String errorMessage = memberGradeNotFoundException.getMessage();

        when(memberGradeService.findMemberGrade(any()))
                .thenThrow(memberGradeNotFoundException);

        mockMvc.perform(get("/api/member-grades/" + testMemberGradeNo)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", equalTo(errorMessage)));

        verify(memberGradeService).findMemberGrade(any());
    }
}