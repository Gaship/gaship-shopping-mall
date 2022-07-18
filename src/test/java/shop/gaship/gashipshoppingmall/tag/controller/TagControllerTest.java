package shop.gaship.gashipshoppingmall.tag.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import shop.gaship.gashipshoppingmall.tag.dto.TagRequestDto;
import shop.gaship.gashipshoppingmall.tag.dto.TagResponseDto;
import shop.gaship.gashipshoppingmall.tag.service.TagService;
import shop.gaship.gashipshoppingmall.tag.utils.TestDummy;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * packageName    : shop.gaship.gashipshoppingmall.tag.controller
 * fileName       : TagControllerTest
 * author         : choijungwoo
 * date           : 2022/07/12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/12        choijungwoo       최초 생성
 */
@WebMvcTest(TagController.class)
class TagControllerTest {
    private final String title = "테스트 타이틀";
    private final int tagNo = 1;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    TagService tagService;

    @DisplayName("태그 등록 테스트")
    @Test
    void addTagTest() throws Exception {
        String body = objectMapper.writeValueAsString(TestDummy.CreateTestTagRequestDto());

        mockMvc.perform(post("/admin/1/tags")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(tagService).addTag(any(TagRequestDto.class));
    }

    @DisplayName("태그 수정 테스트")
    @Test
    void modifyTagTest() throws Exception {
        String body = objectMapper.writeValueAsString(TestDummy.CreateTestTagRequestDto());

        mockMvc.perform(put("/admin/1/tags/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(tagService).modifyTag(any(TagRequestDto.class));
    }

    @DisplayName("태그 삭제 테스트")
    @Test
    void removeTagTest() throws Exception {
        mockMvc.perform(delete("/admin/1/tags/1"))
                .andExpect(status().isOk());
    }

    @DisplayName("태그 단건 조회 테스트")
    @Test
    void findTagTest() throws Exception {
        TagResponseDto tagResponseDto = TestDummy.CreateTestTagResponseDto(title);

        when(tagService.findTag(any()))
                .thenReturn(tagResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/1/tags/" + tagNo)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(tagService).findTag(tagNo);
    }

    @DisplayName("태그 다건 조회 테스트")
    @Test
    void findTagsTest() throws Exception {
        List<TagResponseDto> tagResponseDtoList = List.of(TestDummy.CreateTestTagResponseDto(title));
        when(tagService.findTags(any())).thenReturn(tagResponseDtoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/1/tags")
                        .queryParam("page", "0")
                        .queryParam("size", "10")
                        .queryParam("sort", "title")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(tagService).findTags(PageRequest.of(0, 10, Sort.by("title")));
    }
}