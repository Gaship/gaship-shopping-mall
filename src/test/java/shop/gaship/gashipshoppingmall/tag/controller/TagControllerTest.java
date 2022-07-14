package shop.gaship.gashipshoppingmall.tag.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import shop.gaship.gashipshoppingmall.tag.dto.TagRequestDto;
import shop.gaship.gashipshoppingmall.tag.dto.TagResponseDto;
import shop.gaship.gashipshoppingmall.tag.service.TagService;
import shop.gaship.gashipshoppingmall.tag.utils.TestUtils;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
@MockBean(JpaMetamodelMappingContext.class)
class TagControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    TagService tagService;

    private final String title = "테스트 타이틀";
    private final int tagNo = 1;

    @DisplayName("태그 등록 테스트")
    @Test
    void registerTagTest() throws Exception {
        String body = objectMapper.writeValueAsString(TestUtils.CreateTestTagRequestDto());

        mockMvc.perform(post("/admin/1/tags")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(tagService).register(any(TagRequestDto.class));
    }

    @DisplayName("이름이 동일한 태그 등록")
    @Test
    void registerSameTitleTag() throws Exception {
    }

    @DisplayName("태그 수정 테스트")
    @Test
    void modifyTagTest() throws Exception {
        String body = objectMapper.writeValueAsString(TestUtils.CreateTestTagRequestDto());

        mockMvc.perform(put("/admin/1/tags/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(tagService).modify(any(TagRequestDto.class));
    }

    @DisplayName("태그 삭제 테스트")
    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/1/tags/1"))
                .andExpect(status().isOk());
    }

    @DisplayName("태그 단건 조회 테스트")
    @Test
    void getTagTest() throws Exception{
        TagResponseDto tagResponseDto = TestUtils.CreateTestTagResponseDto(title);

        when(tagService.get(any()))
                .thenReturn(tagResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/1/tags/" + tagNo)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(tagService).get(tagNo);
    }

    @DisplayName("태그 다건 조회 테스트")
    @Test
    void getTagListTest() throws Exception{
        List<TagResponseDto> tagResponseDtoList = List.of(TestUtils.CreateTestTagResponseDto(title));
        when(tagService.getList(any())).thenReturn(tagResponseDtoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/1/tags")
                        .queryParam("page","0")
                        .queryParam("size","10")
                        .queryParam("sort","title")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(tagService).getList(PageRequest.of(0,10, Sort.by("title")));
    }
}