package shop.gaship.gashipshoppingmall.membertag.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipshoppingmall.membertag.dto.request.MemberTagRequestDto;
import shop.gaship.gashipshoppingmall.membertag.dummy.MemberTagDummy;
import shop.gaship.gashipshoppingmall.membertag.service.MemberTagService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author 최정우
 * @since 1.0
 */
@WebMvcTest(MemberTagController.class)
class MemberTagControllerTest {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberTagService memberTagService;

    @DisplayName("회원이 등록한 모든 태그를 삭제하고 다시 등록하기 원하는 태그들을 등록하는 메서드를 테스트합니다.")
    @Test
    void memberTagDeleteAllAndAddAll() throws Exception {
        doNothing().when(memberTagService).deleteAllAndAddAllMemberTags(any());
        String contentBody = objectMapper.writeValueAsString(MemberTagDummy.memberTagRequestDtoDummy(1, List.of(1,3,5,7,9)));

        mockMvc.perform(post("/api/members/1/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(contentBody))
                .andExpect(status().isCreated());

        verify(memberTagService,times(1)).deleteAllAndAddAllMemberTags(any(MemberTagRequestDto.class));
    }

    @DisplayName("회원이 등록한 모든 태그를 조회하는 메서드를 테스트합니다.")
    @Test
    void memberTagList() throws Exception {
        when(memberTagService.findMemberTags(any())).thenReturn(MemberTagDummy.memberTagResponseDtoListDummy());

        mockMvc.perform(get("/api/members/1/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(5));

        verify(memberTagService,times(1)).findMemberTags(any());
    }
}