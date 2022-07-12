package shop.gaship.gashipshoppingmall.category.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;
import shop.gaship.gashipshoppingmall.category.dto.response.CategoryResponseDto;
import shop.gaship.gashipshoppingmall.category.dummy.CategoryDummy;
import shop.gaship.gashipshoppingmall.category.exception.CategoryRemainLowerCategoryException;
import shop.gaship.gashipshoppingmall.category.exception.CategoryRemainProductException;
import shop.gaship.gashipshoppingmall.category.dto.request.CategoryCreateRequestDto;
import shop.gaship.gashipshoppingmall.category.entity.Category;
import shop.gaship.gashipshoppingmall.category.exception.CategoryNotFoundException;
import shop.gaship.gashipshoppingmall.category.repository.CategoryRepository;
import shop.gaship.gashipshoppingmall.category.dto.request.CategoryModifyRequestDto;
import shop.gaship.gashipshoppingmall.product.dummy.ProductDummy;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.product.repository.ProductRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * packageName    : shop.gaship.gashipshoppingmall.category.service
 * fileName       : CategoryServiceTest
 * author         : 김보민
 * date           : 2022-07-09
 * description    : 카테고리 서비스 테스트
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-07-09        김보민       최초 생성
 */
@SpringBootTest
@Transactional
class CategoryServiceTest {
    @Autowired
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private ProductRepository productRepository;

    private CategoryModifyRequestDto modifyRequestDto;
    private Category upperCategory;
    private Category category;

    @BeforeEach
    void setUp() {
        modifyRequestDto = CategoryDummy.modifyRequestDto();
        upperCategory = CategoryDummy.upperDummy();
        category = CategoryDummy.dummy();
    }

    @Test
    @DisplayName("카테고리 생성 성공")
    void addCategorySuccess() {
        CategoryCreateRequestDto request = CategoryCreateRequestDto.builder()
                .name("카테고리")
                .level(1)
                .build();
        ReflectionTestUtils.setField(upperCategory, "no", 1);

        when(categoryRepository.save(any(Category.class))).thenReturn(upperCategory);

        categoryService.addCategory(request);

        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    @DisplayName("카테고리 생성 실패 - 상위 카테고리 찾기 불가")
    void addCategoryFail() {
        CategoryCreateRequestDto request = CategoryCreateRequestDto.builder()
                .name("카테고리")
                .level(2)
                .upperCategoryNo(9999)
                .build();

        when(categoryRepository.findById(request.getUpperCategoryNo())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.addCategory(request)).isInstanceOf(CategoryNotFoundException.class);

        verify(categoryRepository).findById(request.getUpperCategoryNo());
    }

    @Test
    @DisplayName("카테고리 수정 성공")
    void modifyCategorySuccess() {
        Integer categoryNo = 1;
        ReflectionTestUtils.setField(upperCategory, "no", categoryNo);

        when(categoryRepository.findById(categoryNo)).thenReturn(Optional.of(upperCategory));

        categoryService.modifyCategory(categoryNo, modifyRequestDto);

        assertThat(upperCategory.getName()).isEqualTo(modifyRequestDto.getName());

        verify(categoryRepository).findById(categoryNo);
        verify(categoryRepository).save(upperCategory);
    }

    @Test
    @DisplayName("카테고리 수정 실패 - 해당 카테고리 찾기 불가")
    void modifyCategoryFail() {
        Integer categoryNo = 9999;

        when(categoryRepository.findById(categoryNo)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.modifyCategory(categoryNo, modifyRequestDto)).isInstanceOf(CategoryNotFoundException.class);

        verify(categoryRepository).findById(categoryNo);
    }

    @Test
    @DisplayName("카테고리 단건 조회")
    void findCategory() {
        Integer categoryNo = 1;
        CategoryResponseDto categoryResponseDto = CategoryDummy.dtoDummy(categoryNo);

        when(categoryRepository.findCategoryById(categoryNo)).thenReturn(Optional.of(categoryResponseDto));

        assertThat(categoryService.findCategory(categoryNo)).isEqualTo(categoryResponseDto);

        verify(categoryRepository).findCategoryById(categoryNo);
    }

    @Test
    @DisplayName("카테고리 전체 조회")
    void findCategories() {
        CategoryResponseDto categoryResponseDto = CategoryDummy.dtoDummy(1);

        when(categoryRepository.findAllCategories()).thenReturn(List.of(categoryResponseDto));

        List<CategoryResponseDto> categories = categoryService.findCategories();

        assertThat(categories.get(0)).isEqualTo(categoryResponseDto);

        verify(categoryRepository).findAllCategories();
    }

    @Test
    @DisplayName("카테고리 삭제 성공")
    void removeCategorySuccess() {
        ReflectionTestUtils.setField(category, "no", 2);

        when(categoryRepository.findById(category.getNo())).thenReturn(Optional.of(category));
        when(categoryRepository.findLowerCategories(category.getNo())).thenReturn(Collections.emptyList());
        when(productRepository.findAllByCategoryNo(category.getNo())).thenReturn(Collections.emptyList());

        categoryService.removeCategory(category.getNo());

        verify(categoryRepository).findById(category.getNo());
        verify(categoryRepository).findLowerCategories(category.getNo());
        verify(productRepository).findAllByCategoryNo(category.getNo());
        verify(categoryRepository).deleteById(category.getNo());
    }

    @Test
    @DisplayName("카테고리 삭제 실패 - 해당 카테고리 찾기 불가")
    void removeCategoryFail_notFoundCategory() {
        Integer categoryNo = 9999;

        when(categoryRepository.findById(categoryNo)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.removeCategory(categoryNo)).isInstanceOf(CategoryNotFoundException.class);

        verify(categoryRepository).findById(categoryNo);
    }

    @Test
    @DisplayName("카테고리 삭제 실패 - 하위 카테고리 존재")
    void removeCategoryFail_remainLowerCategory() {
        Integer categoryNo = 1;
        CategoryResponseDto lowerCategory = CategoryDummy.dtoDummy(2);
        ReflectionTestUtils.setField(upperCategory, "no", categoryNo);

        when(categoryRepository.findById(categoryNo)).thenReturn(Optional.of(upperCategory));
        when(categoryRepository.findLowerCategories(categoryNo)).thenReturn(List.of(lowerCategory));

        assertThatThrownBy(() -> categoryService.removeCategory(categoryNo)).isInstanceOf(CategoryRemainLowerCategoryException.class);

        verify(categoryRepository).findById(categoryNo);
        verify(categoryRepository).findLowerCategories(categoryNo);
    }

    @Test
    @DisplayName("카테고리 삭제 실패 - 해당 카테고리에 속한 상품 존재")
    void removeCategoryFail_remainProduct() {
        Integer categoryNo = 2;
        Product product = ProductDummy.dummy();
        ReflectionTestUtils.setField(category, "no", categoryNo);

        when(categoryRepository.findById(category.getNo())).thenReturn(Optional.of(category));
        when(categoryRepository.findLowerCategories(categoryNo)).thenReturn(Collections.emptyList());
        when(productRepository.findAllByCategoryNo(categoryNo)).thenReturn(List.of(product));

        assertThatThrownBy(() -> categoryService.removeCategory(category.getNo())).isInstanceOf(CategoryRemainProductException.class);

        verify(categoryRepository).findById(categoryNo);
        verify(categoryRepository).findLowerCategories(categoryNo);
        verify(productRepository).findAllByCategoryNo(category.getNo());
    }
}