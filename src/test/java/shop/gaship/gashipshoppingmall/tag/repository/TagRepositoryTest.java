package shop.gaship.gashipshoppingmall.tag.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;

import javax.persistence.EntityManager;
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
    TagRepository tagRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @DisplayName("태그 다건 조회 테스트")
    @Test
    void getAllTagsTest() {

        IntStream.rangeClosed(1, 100).forEach(i -> tagRepository.save(Tag.builder().title("title....." + i).build()));
        Pageable pageable = PageRequest.of(0,10, Sort.by("title"));
        Page<Tag> allTags = tagRepository.getAllTags(pageable);
        assertThat(allTags).isNotEmpty();
        assertThat(allTags.getTotalPages()).isEqualTo(10);
        assertThat(allTags.getTotalElements()).isEqualTo(100);
        allTags.stream().forEach(i-> System.out.println(i.getTitle()));
    }
}