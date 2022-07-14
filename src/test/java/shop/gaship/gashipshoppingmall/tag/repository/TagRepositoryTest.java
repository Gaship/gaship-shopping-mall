package shop.gaship.gashipshoppingmall.tag.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;
import shop.gaship.gashipshoppingmall.tag.service.TagService;
import shop.gaship.gashipshoppingmall.tag.utils.TestUtils;

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
@ExtendWith(SpringExtension.class)
@DataJpaTest
class TagRepositoryTest {
    @Autowired
    private TagRepository tagRepository;

    @DisplayName("레포지토리 태그 등록 테스트")
    @Test
    void saveTagTest(){
        Tag tag = TestUtils.CreateTestTagEntity();

        Tag savedTag = tagRepository.save(tag);

        assertThat(tag.getTagNo()).isEqualTo(savedTag.getTagNo());
        assertThat(tag.getTitle()).isEqualTo(savedTag.getTitle());
    }
}