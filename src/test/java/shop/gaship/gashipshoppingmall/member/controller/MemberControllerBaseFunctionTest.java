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
import shop.gaship.gashipshoppingmall.member.dto.response.MemberResponseDtoByAdmin;
import shop.gaship.gashipshoppingmall.member.dummy.MemberModifyByAdminDtoDummy;
import shop.gaship.gashipshoppingmall.member.memberTestDummy.MemberBaseDummy;
import shop.gaship.gashipshoppingmall.member.service.MemberService;
import shop.gaship.gashipshoppingmall.util.PageResponse;

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
@WebMvcTest(MemberByAdminController.class)
class MemberControllerBaseFunctionTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    MemberService memberService;

    @DisplayName("관리자의 회원 정보 수정 테스트")
    @Test
    void modifyMemberByAdminTest() throws Exception {
        doNothing().when(memberService).modifyMemberByAdmin(any(MemberModifyByAdminDto.class));

        String body = objectMapper.writeValueAsString(MemberModifyByAdminDtoDummy.dummy());

        mockMvc.perform(
                        put("/api/admin/members/{memberNo}", 1)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        then(memberService).should().modifyMemberByAdmin(any(MemberModifyByAdminDto.class));
    }


    @DisplayName("관리자의 회원 단건 조회 테스트")
    @Test
    void getMemberByAdminTest() throws Exception {
        when(memberService.findMemberByAdmin(anyInt())).thenReturn(MemberBaseDummy.memberResponseDtoByAdminDummy());

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/admin/members/1")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(memberService).findMemberByAdmin(any());
    }

    @DisplayName("관리자의 회원 다건 조회 테스트")
    @Test
    void getMemberListTest() throws Exception {
        PageResponse<MemberResponseDtoByAdmin> page =
                MemberBaseDummy.memberResponseDtoByAdminDtoPage();
        when(memberService.findMembers(any(Pageable.class))).thenReturn(page);
        mockMvc.perform(get("/api/admin/members")
                        .queryParam("page", "0")
                        .queryParam("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(memberService).findMembers(PageRequest.of(0, 10));
    }

}
