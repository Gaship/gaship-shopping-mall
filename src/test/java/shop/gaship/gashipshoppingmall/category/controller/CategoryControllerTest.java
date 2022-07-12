package shop.gaship.gashipshoppingmall.category.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipshoppingmall.category.dto.response.CategoryResponseDto;
import shop.gaship.gashipshoppingmall.category.dummy.CategoryDummy;
import shop.gaship.gashipshoppingmall.category.dto.request.CategoryCreateRequestDto;
import shop.gaship.gashipshoppingmall.category.dto.request.CategoryModifyRequestDto;
import shop.gaship.gashipshoppingmall.category.exception.CategoryNotFoundException;
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
    void categoryCreate() throws Exception {
        CategoryCreateRequestDto request = new CategoryCreateRequestDto("카테고리", 1, null);

        doNothing().when(categoryService).addCategory(request);

        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        verify(categoryService).addCategory(request);
    }

    @Test
    @DisplayName("카테고리 수정 put 요청")
    void categoryModify() throws Exception {
        Integer categoryNo = 1;
        CategoryModifyRequestDto request = CategoryDummy.modifyRequestDto();

        doNothing().when(categoryService).modifyCategory(categoryNo, request);

        mockMvc.perform(put("/categories/{categoryNo}", categoryNo)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        verify(categoryService).modifyCategory(categoryNo, request);
    }

    @Test
    @DisplayName("카테고리 단건 조회 get 요청")
    void categoryDetails() throws Exception{
        Integer categoryNo = 1;
        CategoryResponseDto categoryResponseDto = CategoryDummy.dtoDummy(categoryNo);

        when(categoryService.findCategory(categoryNo)).thenReturn(categoryResponseDto);

        mockMvc.perform(get("/categories/{categoryNo}", categoryNo))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.no").value(categoryResponseDto.getNo()))
                .andExpect(jsonPath("$.data.name").value(categoryResponseDto.getName()))
                .andExpect(jsonPath("$.data.level").value(categoryResponseDto.getLevel()))
                .andExpect(jsonPath("$.data.upperCategoryNo").value(categoryResponseDto.getUpperCategoryNo()))
                .andExpect(jsonPath("$.data.upperCategoryName").value(categoryResponseDto.getUpperCategoryName()))
                .andDo(print());

        verify(categoryService).findCategory(categoryNo);
    }

    @Test
    @DisplayName("카테고리 전체 조회 get 요청")
    void categoryList() throws Exception {
        CategoryResponseDto categoryResponseDto = CategoryDummy.dtoDummy(1);

        when(categoryService.findCategories()).thenReturn(List.of(categoryResponseDto));

        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.size()").value(1))
                .andExpect(jsonPath("$.data[0].no").value(categoryResponseDto.getNo()))
                .andExpect(jsonPath("$.data[0].name").value(categoryResponseDto.getName()))
                .andExpect(jsonPath("$.data[0].level").value(categoryResponseDto.getLevel()))
                .andExpect(jsonPath("$.data[0].upperCategoryNo").value(categoryResponseDto.getUpperCategoryNo()))
                .andExpect(jsonPath("$.data[0].upperCategoryName").value(categoryResponseDto.getUpperCategoryName()))
                .andDo(print());

        verify(categoryService).findCategories();
    }

    @Test
    @DisplayName("카테고리 삭제 delete 요청")
    void categoryRemove() throws Exception{
        Integer categoryNo = 1;

        doNothing().when(categoryService).removeCategory(categoryNo);

        mockMvc.perform(delete("/categories/{categoryNo}", categoryNo))
                .andExpect(status().isOk())
                .andDo(print());

        verify(categoryService).removeCategory(categoryNo);
    }

    @Test
    @DisplayName("예외 처리 테스트")
    void handleException() throws Exception {
        Integer categoryNo = 9999;
        CategoryNotFoundException exception = new CategoryNotFoundException();
        when(categoryService.findCategory(categoryNo)).thenThrow(exception);

        mockMvc.perform(get("/categories/{categoryNo}", categoryNo))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.exception").value(exception.getClass().getSimpleName()))
                .andExpect(jsonPath("$.message").value(exception.getMessage()))
                .andDo(print());

        verify(categoryService).findCategory(categoryNo);
    }
}