package shop.gaship.gashipshoppingmall.category.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;
import shop.gaship.gashipshoppingmall.category.dto.CategoryDto;
import shop.gaship.gashipshoppingmall.category.dummy.CategoryDummy;
import shop.gaship.gashipshoppingmall.category.exception.CategoryRemainLowerCategory;
import shop.gaship.gashipshoppingmall.category.exception.CategoryRemainProductException;
import shop.gaship.gashipshoppingmall.category.request.CategoryCreateRequest;
import shop.gaship.gashipshoppingmall.category.entity.Category;
import shop.gaship.gashipshoppingmall.category.exception.CategoryNotFoundException;
import shop.gaship.gashipshoppingmall.category.repository.CategoryRepository;
import shop.gaship.gashipshoppingmall.category.request.CategoryModifyRequest;
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

    @Test
    void createCategorySuccess() {
        CategoryCreateRequest request = new CategoryCreateRequest("카테고리", 1, null);
        Category category = CategoryDummy.upperDummy(1);

        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        categoryService.createCategory(request);

        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void createCategoryFail() {
        CategoryCreateRequest request = new CategoryCreateRequest("카테고리", 2, 1);

        when(categoryRepository.findById(request.getUpperCategoryNo())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.createCategory(request)).isInstanceOf(CategoryNotFoundException.class);

        verify(categoryRepository).findById(request.getUpperCategoryNo());
    }

    @Test
    void modifyCategorySuccess() {
        Integer categoryNo = 1;
        CategoryModifyRequest request = new CategoryModifyRequest("수정 카테고리");
        Category category = CategoryDummy.upperDummy(1);

        when(categoryRepository.findById(categoryNo)).thenReturn(Optional.of(category));

        categoryService.modifyCategory(categoryNo, request);

        assertThat(category.getName()).isEqualTo(request.getName());

        verify(categoryRepository).findById(categoryNo);
        verify(categoryRepository).save(category);
    }

    @Test
    void getCategory() {
        Integer categoryNo = 1;
        CategoryDto categoryDto = CategoryDummy.dtoDummy(categoryNo);

        when(categoryRepository.findCategoryById(categoryNo)).thenReturn(Optional.of(categoryDto));

        assertThat(categoryService.getCategory(categoryNo)).isEqualTo(categoryDto);

        verify(categoryRepository).findCategoryById(categoryNo);
    }

    @Test
    void getCategories() {
        CategoryDto categoryDto = CategoryDummy.dtoDummy(1);

        when(categoryRepository.findAllCategories()).thenReturn(List.of(categoryDto));

        List<CategoryDto> categories = categoryService.getCategories();

        assertThat(categories.get(0)).isEqualTo(categoryDto);

        verify(categoryRepository).findAllCategories();
    }

    @Test
    void removeCategorySuccess() {
        Category category = CategoryDummy.dummy(2);

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
    void removeCategoryFail_notFoundCategory() {
        Integer categoryNo = 9999;
        when(categoryRepository.findById(categoryNo)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.removeCategory(categoryNo)).isInstanceOf(CategoryNotFoundException.class);

        verify(categoryRepository).findById(categoryNo);
    }

    @Test
    void removeCategoryFail_remainLowerCategory() {
        Integer categoryNo = 1;
        Category category = CategoryDummy.upperDummy(categoryNo);
        CategoryDto lowerCategory = CategoryDummy.dtoDummy(2);

        when(categoryRepository.findById(categoryNo)).thenReturn(Optional.of(category));
        when(categoryRepository.findLowerCategories(categoryNo)).thenReturn(List.of(lowerCategory));

        assertThatThrownBy(() -> categoryService.removeCategory(categoryNo)).isInstanceOf(CategoryRemainLowerCategory.class);

        verify(categoryRepository).findById(categoryNo);
        verify(categoryRepository).findLowerCategories(categoryNo);
    }

    @Test
    void removeCategoryFail_remainProduct() {
        Integer categoryNo = 2;
        Category category = CategoryDummy.dummy(categoryNo);
        Product product = ProductDummy.dummy();

        when(categoryRepository.findById(category.getNo())).thenReturn(Optional.of(category));
        when(categoryRepository.findLowerCategories(categoryNo)).thenReturn(Collections.emptyList());
        when(productRepository.findAllByCategoryNo(categoryNo)).thenReturn(List.of(product));

        assertThatThrownBy(() -> categoryService.removeCategory(category.getNo())).isInstanceOf(CategoryRemainProductException.class);

        verify(categoryRepository).findById(categoryNo);
        verify(categoryRepository).findLowerCategories(categoryNo);
        verify(productRepository).findAllByCategoryNo(category.getNo());
    }
}