package shop.gaship.gashipshoppingmall.category.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import shop.gaship.gashipshoppingmall.category.entity.Category;


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void saveCategory() {
        Category upperCategory = Category.builder()
                .name("상위 카테고리")
                .level(1)
                .upperCategory(null)
                .build();

        Category category = Category.builder()
                .name("카테고리")
                .level(2)
                .upperCategory(upperCategory)
                .build();

        Category savedCategory = categoryRepository.save(category);
        assertThat(savedCategory.getName()).isEqualTo(category.getName());
        assertThat(savedCategory.getLevel()).isEqualTo(category.getLevel());
        assertThat(savedCategory.getUpperCategory().getName()).isEqualTo(upperCategory.getName());
        assertThat(savedCategory.getUpperCategory().getLevel()).isEqualTo(upperCategory.getLevel());
    }

    @Test
    void deleteCategory() {

    }

    @Test
    void findAllCategories() {

    }

    @Test
    void findAllByLevel() {

    }

    @Test
    void findById() {
        Integer categoryNo = 1;
        Category category = Category.builder()
                .no(categoryNo)
                .name("카테고리")
                .level(1)
                .upperCategory(null)
                .build();

        categoryRepository.save(category);

        Optional<Category> foundCategory = categoryRepository.findById(categoryNo);

        assertThat(foundCategory).contains(category);
    }
}