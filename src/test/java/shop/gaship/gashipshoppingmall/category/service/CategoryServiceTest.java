package shop.gaship.gashipshoppingmall.category.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;
import shop.gaship.gashipshoppingmall.category.request.CategoryCreateRequest;
import shop.gaship.gashipshoppingmall.category.entity.Category;
import shop.gaship.gashipshoppingmall.category.exception.CategoryNotFoundException;
import shop.gaship.gashipshoppingmall.category.repository.CategoryRepository;

import java.util.Optional;

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

    @Test
    void createCategorySuccess() {
        CategoryCreateRequest request = new CategoryCreateRequest("카테고리", 1, null);
        Category category = Category.builder()
                .no(1)
                .name(request.getName())
                .level(request.getLevel())
                .upperCategory(null)
                .build();

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
}