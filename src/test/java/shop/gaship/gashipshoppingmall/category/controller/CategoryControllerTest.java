package shop.gaship.gashipshoppingmall.category.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipshoppingmall.category.dto.CategoryDto;
import shop.gaship.gashipshoppingmall.category.dummy.CategoryDummy;
import shop.gaship.gashipshoppingmall.category.dto.CategoryCreateRequestDto;
import shop.gaship.gashipshoppingmall.category.dto.CategoryModifyRequestDto;
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
    @DisplayName("카테고리 생성 post 요청")
    void createCategory() throws Exception {
        CategoryCreateRequestDto request = new CategoryCreateRequestDto("카테고리", 1, null);

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
    @DisplayName("카테고리 수정 put 요청")
    void modifyCategory() throws Exception {
        Integer categoryNo = 1;
        CategoryModifyRequestDto request = CategoryDummy.modifyRequestDto();

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
    @DisplayName("카테고리 단건 조회 get 요청")
    void getCategory() throws Exception{
        Integer categoryNo = 1;
        CategoryDto categoryDto = CategoryDummy.dtoDummy(categoryNo);

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
    @DisplayName("카테고리 전체 조회 get 요청")
    void getCategories() throws Exception {
        CategoryDto categoryDto = CategoryDummy.dtoDummy(1);

        when(categoryService.getCategories()).thenReturn(List.of(categoryDto));

        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].no").value(categoryDto.getNo()))
                .andExpect(jsonPath("$[0].name").value(categoryDto.getName()))
                .andExpect(jsonPath("$[0].level").value(categoryDto.getLevel()))
                .andExpect(jsonPath("$[0].upperCategoryNo").value(categoryDto.getUpperCategoryNo()))
                .andExpect(jsonPath("$[0].upperCategoryName").value(categoryDto.getUpperCategoryName()))
                .andDo(print());

        verify(categoryService).getCategories();
    }

    @Test
    @DisplayName("카테고리 삭제 delete 요청")
    void removeCategory() throws Exception{
        Integer categoryNo = 1;

        doNothing().when(categoryService).removeCategory(categoryNo);

        mockMvc.perform(delete("/categories/{categoryNo}", categoryNo))
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(categoryService).removeCategory(categoryNo);
    }
}