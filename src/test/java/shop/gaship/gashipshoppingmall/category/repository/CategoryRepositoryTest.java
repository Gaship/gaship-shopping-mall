package shop.gaship.gashipshoppingmall.category.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shop.gaship.gashipshoppingmall.category.dto.response.CategoryResponseDto;
import shop.gaship.gashipshoppingmall.category.dummy.CategoryDummy;
import shop.gaship.gashipshoppingmall.category.entity.Category;


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    private Category upperCategory;
    private Category category;

    @BeforeEach
    void setUp() {
        upperCategory = CategoryDummy.upperDummy();
        category = CategoryDummy.dummy();
    }

    @Test
    @DisplayName("카테고리 등록 및 수정")
    void saveCategory() {
        Category savedCategory = categoryRepository.save(category);

        assertThat(savedCategory.getName()).isEqualTo(category.getName());
        assertThat(savedCategory.getLevel()).isEqualTo(category.getLevel());
        assertThat(savedCategory.getUpperCategory().getName()).isEqualTo(upperCategory.getName());
        assertThat(savedCategory.getUpperCategory().getLevel()).isEqualTo(upperCategory.getLevel());
    }

    @Test
    @DisplayName("카테고리 엔티티 단건 조회")
    void findById() {
        Category savedCategory = categoryRepository.save(category);

        Optional<Category> foundCategory = categoryRepository.findById(savedCategory.getNo());

        assertThat(foundCategory).contains(category);
    }

    @Test
    @DisplayName("카테고리 단건 조회")
    void findDtoById() {
        Category savedCategory = categoryRepository.save(category);

        Optional<CategoryResponseDto> categoryDto = categoryRepository.findCategoryById(savedCategory.getNo());

        assertThat(categoryDto).isPresent();
        assertThat(categoryDto.get().getNo()).isEqualTo(savedCategory.getNo());
        assertThat(categoryDto.get().getName()).isEqualTo(savedCategory.getName());
        assertThat(categoryDto.get().getLevel()).isEqualTo(savedCategory.getLevel());
        assertThat(categoryDto.get().getUpperCategoryNo()).isEqualTo(savedCategory.getUpperCategory().getNo());
        assertThat(categoryDto.get().getUpperCategoryName()).isEqualTo(savedCategory.getUpperCategory().getName());
    }

    @Test
    @DisplayName("부모 카테고리 조회")
    void findByUpperCategoryNoIsNull() {
        categoryRepository.save(category);
        List<Category> categories = categoryRepository.findByUpperCategoryNoIsNull();

        assertThat(categories).hasSize(1);
        assertThat(categories.get(0).getName()).isEqualTo(upperCategory.getName());
        assertThat(categories.get(0).getLevel()).isEqualTo(upperCategory.getLevel());
        assertThat(categories.get(0).getUpperCategory()).isNull();
    }

    @Test
    @DisplayName("하위 카테고리 조회")
    void findAllLowerCategories() {
        Category savedCategory = categoryRepository.save(category);

        List<CategoryResponseDto> lowerCategories = categoryRepository.findAllLowerCategories(savedCategory.getUpperCategory().getNo());

        assertThat(lowerCategories).hasSize(1);
    }

    @Test
    @DisplayName("카테고리 삭제")
    void deleteCategory() {
        Category savedCategory = categoryRepository.save(category);

        categoryRepository.deleteById(savedCategory.getNo());

        assertThat(categoryRepository.findById(savedCategory.getNo())).isEmpty();
    }

    @Test
    @DisplayName("하위 카테고리 등록")
    void saveLowerCategory() {
        Category savedCategory = categoryRepository.save(upperCategory);

        Category lowerCategory = new Category(
                "하위 카테고리",
                2
        );

        savedCategory.insertLowerCategory(lowerCategory);

        List<CategoryResponseDto> lowerCategories = categoryRepository.findAllLowerCategories(savedCategory.getNo());

        assertThat(lowerCategories).hasSize(1);
        assertThat(lowerCategories.get(0).getName()).isEqualTo(lowerCategory.getName());
        assertThat(lowerCategories.get(0).getLevel()).isEqualTo(lowerCategory.getLevel());
    }
}