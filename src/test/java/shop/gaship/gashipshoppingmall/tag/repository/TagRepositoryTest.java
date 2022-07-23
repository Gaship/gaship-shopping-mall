package shop.gaship.gashipshoppingmall.tag.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import shop.gaship.gashipshoppingmall.tag.dummy.TagDummy;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 태그 Repository 테스트.
 *
 * @author : 최정우
 * @since 1.0
 */
@DataJpaTest
class TagRepositoryTest {
    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @DisplayName("태그 다건 조회 테스트")
    @Test
    void getAllTagsTest() {
        IntStream.rangeClosed(1, 100).forEach(i -> tagRepository.save(Tag.builder().tagNo(i).title("title....." + i).build()));

    }
}