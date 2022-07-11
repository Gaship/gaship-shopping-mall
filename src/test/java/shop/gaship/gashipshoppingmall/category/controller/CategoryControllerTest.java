package shop.gaship.gashipshoppingmall.category.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipshoppingmall.category.dto.CategoryDto;
import shop.gaship.gashipshoppingmall.category.request.CategoryCreateRequest;
import shop.gaship.gashipshoppingmall.category.request.CategoryModifyRequest;
import shop.gaship.gashipshoppingmall.category.service.CategoryService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * packageName    : shop.gaship.gashipshoppingmall.category.controller
 * fileName       : CategoryControllerTest
 * author         : 김보민
 * date           : 2022-07-09
 * description    : 카테고리 컨트롤러 테스트
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-07-09        김보민       최초 생성
 */
@WebMvcTest(CategoryController.class)
class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void postCategory() throws Exception {
        CategoryCreateRequest request = new CategoryCreateRequest("카테고리", 1, null);

        doNothing().when(categoryService).createCategory(request);

        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        verify(categoryService).createCategory(request);
    }

    @Test
    void putCategory() throws Exception {
        Integer categoryNo = 1;
        CategoryModifyRequest request = new CategoryModifyRequest("수정 카테고리");

        doNothing().when(categoryService).modifyCategory(categoryNo, request);

        mockMvc.perform(put("/categories/{categoryNo}", categoryNo)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        verify(categoryService).modifyCategory(categoryNo, request);
    }

    @Test
    void getCategory() throws Exception{
        Integer categoryNo = 1;
        CategoryDto categoryDto = CategoryDto.builder()
                .no(categoryNo)
                .name("카테고리")
                .level(1)
                .build();

        when(categoryService.getCategory(categoryNo)).thenReturn(categoryDto);

        mockMvc.perform(get("/categories/{categoryNo}", categoryNo))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.no").value(categoryDto.getNo()))
                .andExpect(jsonPath("$.name").value(categoryDto.getName()))
                .andExpect(jsonPath("$.level").value(categoryDto.getLevel()))
                .andExpect(jsonPath("$.upperCategoryNo").value(categoryDto.getUpperCategoryNo()))
                .andExpect(jsonPath("$.upperCategoryName").value(categoryDto.getUpperCategoryName()))
                .andDo(print());

        verify(categoryService).getCategory(categoryNo);
    }

    @Test
    void getCategories() throws Exception {
        CategoryDto categoryDto = CategoryDto.builder()
                .no(1)
                .name("카테고리")
                .level(1)
                .build();

        when(categoryService.getCategories()).thenReturn(List.of(categoryDto));

        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andDo(print());

        verify(categoryService).getCategories();
    }
}