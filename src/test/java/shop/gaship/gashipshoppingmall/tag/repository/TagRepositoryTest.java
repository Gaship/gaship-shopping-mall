package shop.gaship.gashipshoppingmall.tag.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;
import shop.gaship.gashipshoppingmall.tag.utils.TestDummy;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * packageName    : shop.gaship.gashipshoppingmall.tag.repository
 * fileName       : TagRepositoryTest
 * author         : choijungwoo
 * date           : 2022/07/12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/12        choijungwoo       최초 생성
 */

@DataJpaTest
class TagRepositoryTest {
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    private TagRepository tagRepository;

    @DisplayName("레포지토리 태그 등록 테스트")
    @Test
    void saveTagTest() {
        Tag tag = TestDummy.createTestTagEntity();

        Tag savedTag = tagRepository.save(tag);

        assertThat(savedTag.getTitle()).isEqualTo(tag.getTitle());
        assertThat(tagRepository.existsByTitle("title....1")).isTrue();
    }

    @DisplayName("레포지토리 태그 등록 테스트2")
    @Test
    void saveTagTest2() {
        Tag tag = TestDummy.createTestTagEntity();

        tagRepository.save(tag);

        assertThat(tagRepository.existsByTitle("title....1")).isTrue();
    }
}
