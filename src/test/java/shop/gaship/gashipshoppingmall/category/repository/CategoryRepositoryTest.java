package shop.gaship.gashipshoppingmall.category.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import shop.gaship.gashipshoppingmall.category.dto.CategoryDto;
import shop.gaship.gashipshoppingmall.category.dummy.CategoryDummy;
import shop.gaship.gashipshoppingmall.category.entity.Category;


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * packageName    : shop.gaship.gashipshoppingmall.category.repository
 * fileName       : CategoryRepositoryTest
 * author         : 김보민
 * date           : 2022-07-09
 * description    : 카테고리 레퍼지토리 테스트
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-07-08        김보민       최초 생성
 */
@DataJpaTest
class CategoryRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category upperCategory;
    private Category category;

    @BeforeEach
    void setUp() {
        upperCategory = CategoryDummy.upperDummy(null);
        category = CategoryDummy.dummy(null);
    }

    @Test
    void saveCategory() {
        Category savedCategory = categoryRepository.save(category);

        assertThat(savedCategory.getName()).isEqualTo(category.getName());
        assertThat(savedCategory.getLevel()).isEqualTo(category.getLevel());
        assertThat(savedCategory.getUpperCategory().getName()).isEqualTo(upperCategory.getName());
        assertThat(savedCategory.getUpperCategory().getLevel()).isEqualTo(upperCategory.getLevel());
    }

    @Test
    void findById() {
        Category savedCategory = categoryRepository.save(category);

        Optional<Category> foundCategory = categoryRepository.findById(savedCategory.getNo());

        assertThat(foundCategory).contains(category);
    }

    @Test
    void findDtoById() {
        Category savedCategory = categoryRepository.save(category);

        Optional<CategoryDto> categoryDto = categoryRepository.findCategoryById(savedCategory.getNo());

        assertThat(categoryDto).isPresent();
        assertThat(categoryDto.get().getNo()).isEqualTo(savedCategory.getNo());
        assertThat(categoryDto.get().getName()).isEqualTo(savedCategory.getName());
        assertThat(categoryDto.get().getLevel()).isEqualTo(savedCategory.getLevel());
        assertThat(categoryDto.get().getUpperCategoryNo()).isEqualTo(savedCategory.getUpperCategory().getNo());
        assertThat(categoryDto.get().getUpperCategoryName()).isEqualTo(savedCategory.getUpperCategory().getName());
    }

    @Test
    void findAllCategories() {
        Category savedCategory = categoryRepository.save(category);
        List<CategoryDto> categories = categoryRepository.findAllCategories();

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setNo(savedCategory.getNo());
        categoryDto.setName(savedCategory.getName());
        categoryDto.setLevel(savedCategory.getLevel());
        categoryDto.setUpperCategoryNo(savedCategory.getUpperCategory().getNo());
        categoryDto.setUpperCategoryName(savedCategory.getUpperCategory().getName());

        assertThat(categories).hasSize(2);
        assertThat(categories.get(1)).isEqualTo(categoryDto);
    }

    @Test
    void findAllLowerCategories() {
        Category savedCategory = categoryRepository.saveAndFlush(category);

        List<CategoryDto> lowerCategories = categoryRepository.findLowerCategories(savedCategory.getUpperCategory().getNo());

        assertThat(lowerCategories).hasSize(1);
    }

    @Test
    void deleteCategory() {
        Category savedCategory = categoryRepository.save(category);

        categoryRepository.deleteById(savedCategory.getNo());

        assertThat(categoryRepository.findById(savedCategory.getNo())).isEmpty();
    }
}