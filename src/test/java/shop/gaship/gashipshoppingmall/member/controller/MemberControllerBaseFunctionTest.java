package shop.gaship.gashipshoppingmall.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import shop.gaship.gashipshoppingmall.member.dto.request.MemberModifyByAdminDto;
import shop.gaship.gashipshoppingmall.member.dto.response.MemberResponseDto;
import shop.gaship.gashipshoppingmall.member.dummy.MemberModifyByAdminDtoDummy;
import shop.gaship.gashipshoppingmall.member.memberTestDummy.MemberDummy;
import shop.gaship.gashipshoppingmall.member.service.MemberService;
import shop.gaship.gashipshoppingmall.response.PageResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 회원 기본 crud 레스트 컨트롤러 테스트입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@WebMvcTest(MemberController.class)
class MemberControllerBaseFunctionTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    MemberService memberService;

    @DisplayName("회원 정보 수정 테스트")
    @Test
    void modifyMemberTest() throws Exception {
        String body = objectMapper.writeValueAsString(MemberDummy.memberModifyRequestDtoDummy());
        doNothing().when(memberService).modifyMember(MemberDummy.memberModifyRequestDtoDummy());

        mockMvc.perform(put("/api/members/{memberNo}", 1)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(memberService, times(1)).modifyMember(any());

    }

    @DisplayName("관리자의 회원 정보 수정 테스트")
    @Test
    void modifyMemberByAdminTest() throws Exception {
        doNothing().when(memberService).modifyMemberByAdmin(any(MemberModifyByAdminDto.class));

        String body = objectMapper.writeValueAsString(MemberModifyByAdminDtoDummy.dummy());

        mockMvc.perform(
                        put("/api/members/{memberNo}/role", 1)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        then(memberService).should().modifyMemberByAdmin(any(MemberModifyByAdminDto.class));
    }

    @DisplayName("회원 삭제 테스트(실삭제(db 에서 삭제))")
    @Test
    void deleteMemberTest() throws Exception {
        doNothing().when(memberService).removeMember(any(Integer.class));
        mockMvc.perform(delete("/api/members/1").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        verify(memberService).removeMember(any());
    }

    @DisplayName("회원 단건 조회 테스트")
    @Test
    void getMemberTest() throws Exception {
        when(memberService.findMember(anyInt())).thenReturn(MemberDummy.memberResponseDto());

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/members/1")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(memberService).findMember(any());
    }

    @DisplayName("회원 다건 조회 테스트")
    @Test
    void getMemberListTest() throws Exception {
        PageResponse<MemberResponseDto> memberResponseDto =
                MemberDummy.createTestMemberPageResponseDto();
        when(memberService.findMembers(any(Pageable.class))).thenReturn(memberResponseDto);
        mockMvc.perform(get("/api/members")
                        .queryParam("page", "0")
                        .queryParam("size", "10")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(memberService).findMembers(PageRequest.of(0, 10));
    }

}
