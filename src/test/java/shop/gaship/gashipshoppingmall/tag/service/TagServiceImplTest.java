package shop.gaship.gashipshoppingmall.tag.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipshoppingmall.tag.dto.TagResponseDto;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;
import shop.gaship.gashipshoppingmall.tag.repository.TagRepository;
import shop.gaship.gashipshoppingmall.tag.utils.TestUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * packageName    : shop.gaship.gashipshoppingmall.tag.service
 * fileName       : TagServiceImplTest
 * author         : choijungwoo
 * date           : 2022/07/12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/12        choijungwoo       최초 생성
 */
@ExtendWith(SpringExtension.class)
@Import(value = {TagServiceImpl.class})
class TagServiceImplTest {
    @Autowired
    TagService tagService;

    @MockBean
    private TagRepository tagRepository;

    private String title = "테스트 타이틀";
    private String modifiedTitle = "변경 테스트 타이틀";

    @DisplayName("tagService register 테스트")
    @Test
    void register() {
        when(tagRepository.save(any(Tag.class))).thenReturn(TestUtils.CreateTestTagEntity());
        tagService.register(TestUtils.CreateTestTagRequestDto());

        verify(tagRepository).save(any(Tag.class));
    }

    @DisplayName("tagService modify 테스트")
    @Test
    void modify() {
        Tag tag = TestUtils.CreateTestTagEntity();
        when(tagRepository.findById(any())).thenReturn(Optional.of(tag));
        when(tagRepository.save(any(Tag.class))).thenReturn(tag);

        tagService.modify(TestUtils.CreateTestTagRequestDto());

        verify(tagRepository).findById(any());
        verify(tagRepository).save(any(Tag.class));
    }

    @DisplayName("tagService delete 테스트")
    @Test
    void delete() {
        tagService.delete(1);

        verify(tagRepository).deleteById(any());
    }

    @DisplayName("tagService get 테스트")
    @Test
    void get() {
        Tag tag = TestUtils.CreateTestTagEntity();
        when(tagRepository.findById(any())).thenReturn(Optional.of(tag));

        tagService.get(0);

        verify(tagRepository).findById(any());
    }

    @DisplayName("tagService getList 테스트")
    @Test
    void getList() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("title"));
        List<Tag> tagList = TestUtils.CreateTestTagEntityList();
        Page<Tag> page = new PageImpl<>(tagList);
        when(tagRepository.findAll(pageable)).thenReturn(page);

        List<TagResponseDto> list = tagService.getList(pageable);

        verify(tagRepository).findAll(pageable);
        assertThat(list).hasSize(100);
    }
}