package shop.gaship.gashipshoppingmall.member.controller;

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
import shop.gaship.gashipshoppingmall.member.dto.MemberResponseDto;
import shop.gaship.gashipshoppingmall.member.memberTestUtils.MemberTestUtils;
import shop.gaship.gashipshoppingmall.member.service.MemberService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * packageName    : shop.gaship.gashipshoppingmall.member.controller
 * fileName       : MemberControllerTest
 * author         : choijungwoo
 * date           : 2022/07/11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/11        choijungwoo       최초 생성
 */
@WebMvcTest(MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    MemberService memberService;

    @DisplayName("회원 등록 테스트")
    @Test
    void registerMemberTest() throws Exception {
        String body = objectMapper.writeValueAsString(MemberTestUtils.memberRegisterRequestDto());
        doNothing().when(memberService).register(MemberTestUtils.memberRegisterRequestDto());

        mockMvc.perform(post("/signUp")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(memberService).register(any());
    }

    @DisplayName("회원 정보 수정 테스트")
    @Test
    void modifyMemberTest() throws Exception {
        String body = objectMapper.writeValueAsString(MemberTestUtils.memberModifyRequestDto());
        doNothing().when(memberService).modify(MemberTestUtils.memberModifyRequestDto());

        mockMvc.perform(put("/members/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(memberService).modify(any());
    }

    @DisplayName("회원 삭제 테스트(실삭제(db 에서 삭제))")
    @Test
    void deleteMemberTest() throws Exception {
        doNothing().when(memberService).delete(any(Integer.class));
        mockMvc.perform(delete("/members/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(memberService).delete(any());
    }

    @DisplayName("회원 단건 조회 테스트")
    @Test
    void getMemberTest() throws Exception {
        when(memberService.get(any(Integer.class))).thenReturn(MemberTestUtils.memberResponseDto());

        mockMvc.perform(MockMvcRequestBuilders.get("/members/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(memberService).get(any());
    }

    @DisplayName("회원 다건 조회 테스트")
    @Test
    void getMemberListTest() throws Exception {
        List<MemberResponseDto> memberResponseDtoList = List.of(MemberTestUtils.memberResponseDto(), MemberTestUtils.memberResponseDto());
        when(memberService.getList(any(Pageable.class))).thenReturn(memberResponseDtoList);

        mockMvc.perform(get("/members")
                        .queryParam("page", "0")
                        .queryParam("size", "10")
                        .queryParam("sort", "title")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(memberService).getList(PageRequest.of(0, 10, Sort.by("title")));
    }
}