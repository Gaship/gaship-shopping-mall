package shop.gaship.gashipshoppingmall.tag.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipshoppingmall.tag.dto.response.PageResponseDto;
import shop.gaship.gashipshoppingmall.tag.dto.request.TagAddRequestDto;
import shop.gaship.gashipshoppingmall.tag.dto.request.TagModifyRequestDto;
import shop.gaship.gashipshoppingmall.tag.dto.response.TagResponseDto;
import shop.gaship.gashipshoppingmall.tag.dummy.TagDummy;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;
import shop.gaship.gashipshoppingmall.tag.service.TagService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        String body = objectMapper.writeValueAsString(TagDummy.TagAddRequestDtoDummy());

        mockMvc.perform(post("/api/tags")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(tagService).addTag(any(TagAddRequestDto.class));
    }

    @DisplayName("태그 수정 테스트")
    @Test
    void modifyTagTest() throws Exception {
        String body = objectMapper.writeValueAsString(TagDummy.TagModifyRequestDtoDummy());

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
        TagResponseDto tagResponseDto = TagDummy.TagResponseDtoDummy();

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
        String page = "1";
        String size = "10";
        String sort = "title";
        PageResponseDto<TagResponseDto, Tag> tagPageResponseDto = TagDummy.TagPageResponseDtoDummy();
        when(tagService.findTags(any())).thenReturn(tagPageResponseDto);

        mockMvc.perform(get("/api/tags")
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .queryParam("sort", sort)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(tagService).findTags(any());
    }
}