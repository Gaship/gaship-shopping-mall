package shop.gaship.gashipshoppingmall.tag.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipshoppingmall.response.PageResponse;
import shop.gaship.gashipshoppingmall.tag.dto.request.TagAddRequestDto;
import shop.gaship.gashipshoppingmall.tag.dto.request.TagModifyRequestDto;
import shop.gaship.gashipshoppingmall.tag.dto.response.TagResponseDto;
import shop.gaship.gashipshoppingmall.tag.dummy.TagDummy;
import shop.gaship.gashipshoppingmall.tag.service.TagService;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TagController.class)
class TagControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    TagService tagService;

    @DisplayName("태그 등록 테스트")
    @Test
    void addTagTest() throws Exception {
        doNothing().when(tagService).addTag(any());
        String body = objectMapper.writeValueAsString(TagDummy.TagAddRequestDtoDummy("낡은"));

        mockMvc.perform(post("/api/tags")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(tagService).addTag(any(TagAddRequestDto.class));
    }

    @DisplayName("태그 등록 테스트 실패(타이틀이 null(Fail))")
    @Test
    void addTagFailTestNullTitleFail() throws Exception {
        String body = objectMapper.writeValueAsString(TagDummy.TagAddRequestDtoDummy(null));

        mockMvc.perform(post("/api/tags")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("등록하려는 태그의 title 은 필수값입니다."));

        verify(tagService, never()).addTag(any(TagAddRequestDto.class));
    }

    @DisplayName("태그 등록 테스트 실패(타이틀 10자 이상(Fail))")
    @Test
    void addTagFailTestWrongTitleFail() throws Exception {
        TagAddRequestDto request = TagDummy.TagAddRequestDtoDummy("10자를 훨씬 넘는 태그 타이틀");
        String body = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/tags")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("title 의 길이는 최소 1 이상 최대 10 이하 입니다."));


        verify(tagService, never()).addTag(any(TagAddRequestDto.class));
    }


    @DisplayName("태그 수정 테스트")
    @Test
    void modifyTagTest() throws Exception {
        TagModifyRequestDto request = TagDummy.TagModifyRequestDtoDummy(1,"럭셔리");
        String body = objectMapper.writeValueAsString(request);

        mockMvc.perform(put("/api/tags/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(tagService).modifyTag(any(TagModifyRequestDto.class));
    }

    @DisplayName("태그 단건 조회 테스트")
    @Test
    void findTagTest() throws Exception {
        TagResponseDto tagResponseDto = TagDummy.TagResponseDtoDummy(1,"모던");

        when(tagService.findTag(any()))
                .thenReturn(tagResponseDto);

        mockMvc.perform(get("/api/tags/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(tagService).findTag(any());
    }

    @DisplayName("태그 다건 조회 테스트")
    @Test
    void findTagsTest() throws Exception {
        Integer page = 1;
        Integer size = 10;
        String sort = "title";
        PageResponse<TagResponseDto> tagPageResponseDto = TagDummy.TagPageResponseDummy(page, size, sort);
        when(tagService.findTags(any())).thenReturn(tagPageResponseDto);

        mockMvc.perform(get("/api/tags")
                        .queryParam("page", String.valueOf(page))
                        .queryParam("size", String.valueOf(size))
                        .queryParam("sort", sort)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalPage", equalTo(4)))
                .andExpect(jsonPath("$.content.[0].title", equalTo("title...1")))
                .andExpect(jsonPath("$.page", equalTo(page)));

        verify(tagService, times(1)).findTags(any());
    }
}