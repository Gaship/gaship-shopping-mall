package shop.gaship.gashipshoppingmall.tag.repository;

import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import shop.gaship.gashipshoppingmall.tag.dto.response.TagResponseDto;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;

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
        IntStream.rangeClosed(1, 15).forEach(i -> tagRepository.save(Tag.builder().title("title....." + i).build()));
        Pageable pageable = PageRequest.of(0, 10);

        Page<TagResponseDto> tags = tagRepository.getTags(pageable);

        assertThat(tags).isNotEmpty();
        assertThat(tags.getTotalPages()).isEqualTo(2);
        assertThat(tags.getTotalElements()).isEqualTo(15);
    }
}