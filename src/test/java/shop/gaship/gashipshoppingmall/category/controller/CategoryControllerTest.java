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

@WebMvcTest({ CategoryController.class })
class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("root 카테고리 생성 post 요청")
    void rootCategoryCreate() throws Exception {
        CategoryCreateRequestDto createRequest = new CategoryCreateRequestDto(
                "카테고리",
                null
        );

        doNothing().when(categoryService).addRootCategory(createRequest);

        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        verify(categoryService).addRootCategory(createRequest);
    }

    @Test
    @DisplayName("lower 카테고리 생성 post 요청")
    void lowerCategoryCreate() throws Exception {
        CategoryCreateRequestDto createRequest = new CategoryCreateRequestDto(
                "카테고리",
                1
        );

        doNothing().when(categoryService).addLowerCategory(createRequest);

        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        verify(categoryService).addLowerCategory(createRequest);
    }

    @Test
    @DisplayName("카테고리 수정 put 요청")
    void categoryModify() throws Exception {
        CategoryModifyRequestDto modifyRequest = new CategoryModifyRequestDto(
                1,
                "수정 카테고리"
        );

        doNothing().when(categoryService).modifyCategory(modifyRequest);

        mockMvc.perform(put("/categories")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(modifyRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        verify(categoryService).modifyCategory(modifyRequest);
    }

    @Test
    @DisplayName("카테고리 단건 조회 get 요청")
    void categoryDetails() throws Exception{
        Integer categoryNo = 1;
        CategoryResponseDto categoryResponseDto = CategoryDummy.upperDtoDummy();

        when(categoryService.findCategory(categoryNo)).thenReturn(categoryResponseDto);

        mockMvc.perform(get("/categories/{categoryNo}", categoryNo))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.no").value(categoryResponseDto.getNo()))
                .andExpect(jsonPath("$.name").value(categoryResponseDto.getName()))
                .andExpect(jsonPath("$.level").value(categoryResponseDto.getLevel()))
                .andExpect(jsonPath("$.upperCategoryNo").value(categoryResponseDto.getUpperCategoryNo()))
                .andExpect(jsonPath("$.upperCategoryName").value(categoryResponseDto.getUpperCategoryName()))
                .andDo(print());

        verify(categoryService).findCategory(categoryNo);
    }

    @Test
    @DisplayName("카테고리 전체 조회 get 요청")
    void categoryList() throws Exception {
        CategoryResponseDto categoryResponseDto = CategoryDummy.upperDtoDummy();

        when(categoryService.findCategories()).thenReturn(List.of(categoryResponseDto));

        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].no").value(categoryResponseDto.getNo()))
                .andExpect(jsonPath("$[0].name").value(categoryResponseDto.getName()))
                .andExpect(jsonPath("$[0].level").value(categoryResponseDto.getLevel()))
                .andExpect(jsonPath("$[0].upperCategoryNo").value(categoryResponseDto.getUpperCategoryNo()))
                .andExpect(jsonPath("$[0].upperCategoryName").value(categoryResponseDto.getUpperCategoryName()))
                .andDo(print());

        verify(categoryService).findCategories();
    }

    @Test
    @DisplayName("하위 카테고리 조회 get 요청")
    void lowerCategoryList() throws Exception {
        Integer categoryNo = 1;
        CategoryResponseDto categoryResponseDto = CategoryDummy.dtoDummy();

        when(categoryService.findLowerCategories(categoryNo)).thenReturn(List.of(categoryResponseDto));

        mockMvc.perform(get("/categories/{categoryNo}/lower", categoryNo))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].no").value(categoryResponseDto.getNo()))
                .andExpect(jsonPath("$[0].name").value(categoryResponseDto.getName()))
                .andExpect(jsonPath("$[0].level").value(categoryResponseDto.getLevel()))
                .andExpect(jsonPath("$[0].upperCategoryNo").value(categoryResponseDto.getUpperCategoryNo()))
                .andExpect(jsonPath("$[0].upperCategoryName").value(categoryResponseDto.getUpperCategoryName()))
                .andDo(print());

        verify(categoryService).findLowerCategories(categoryNo);
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
                .andExpect(jsonPath("$.message").value(exception.getMessage()))
                .andDo(print());

        verify(categoryService).findCategory(categoryNo);
    }
}