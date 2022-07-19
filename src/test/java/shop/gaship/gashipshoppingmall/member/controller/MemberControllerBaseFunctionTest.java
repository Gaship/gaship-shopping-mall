package shop.gaship.gashipshoppingmall.member.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import shop.gaship.gashipshoppingmall.member.dto.MemberPageResponseDto;
import shop.gaship.gashipshoppingmall.member.dto.MemberResponseDto;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.memberTestDummy.MemberTestDummy;
import shop.gaship.gashipshoppingmall.member.service.MemberService;

/**
 * 설명작성란
 *
 * @author 최정우
 * @since 1.0
 */

@WebMvcTest(MemberController.class)
public class MemberControllerBaseFunctionTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    MemberService memberService;

    @DisplayName("회원 정보 수정 테스트")
    @Test
    void modifyMemberTest() throws Exception {
        String body = objectMapper.writeValueAsString(MemberTestDummy.memberModifyRequestDto());
        doNothing().when(memberService).modifyMember(MemberTestDummy.memberModifyRequestDto());

        mockMvc.perform(put("/members/1")
                .accept(MediaType.APPLICATION_JSON)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(memberService).modifyMember(any());
    }

    @DisplayName("회원 삭제 테스트(실삭제(db 에서 삭제))")
    @Test
    void deleteMemberTest() throws Exception {
        doNothing().when(memberService).removeMember(any(Integer.class));
        mockMvc.perform(delete("/members/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(memberService).removeMember(any());
    }

    @DisplayName("회원 단건 조회 테스트")
    @Test
    void getMemberTest() throws Exception {
        when(memberService.findMember(any(Integer.class))).thenReturn(
            MemberTestDummy.memberResponseDto());

        mockMvc.perform(MockMvcRequestBuilders.get("/members/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(memberService).findMember(any());
    }

    @DisplayName("회원 다건 조회 테스트")
    @Test
    void getMemberListTest() throws Exception {
        MemberPageResponseDto<MemberResponseDto, Member> memberResponseDto =
            MemberTestDummy.CreateTestMemberPageResponseDto();
        when(memberService.findMembers(any(Pageable.class))).thenReturn(memberResponseDto);
        mockMvc.perform(get("/members")
                .queryParam("page", "0")
                .queryParam("size", "10")
                .queryParam("sort", "title")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(memberService).findMembers(PageRequest.of(0, 10, Sort.by("title")));
    }

    @DisplayName("관리자의 회원 정보 수정 테스트")
    @Test
    void modifyMemberByAdminTest() throws Exception {
        String body = objectMapper.writeValueAsString(MemberTestDummy.memberModifyRequestDto());
        doNothing().when(memberService).modifyMember(MemberTestDummy.memberModifyRequestDto());

        mockMvc.perform(put("/admins/1/members")
                .accept(MediaType.APPLICATION_JSON)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(memberService).modifyMember(any());
    }
}
