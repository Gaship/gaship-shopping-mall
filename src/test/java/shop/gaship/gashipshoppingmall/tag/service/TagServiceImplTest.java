package shop.gaship.gashipshoppingmall.tag.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipshoppingmall.tag.dto.TagPageResponseDto;
import shop.gaship.gashipshoppingmall.tag.dto.TagResponseDto;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;
import shop.gaship.gashipshoppingmall.tag.exception.DuplicatedTagTitleException;
import shop.gaship.gashipshoppingmall.tag.exception.TagNotFoundException;
import shop.gaship.gashipshoppingmall.tag.repository.TagRepository;
import shop.gaship.gashipshoppingmall.tag.utils.TestDummy;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    @DisplayName("tagService addTag 테스트")
    @Test
    void addTagTest() {
        when(tagRepository.save(any(Tag.class))).thenReturn(TestDummy.CreateTestTagEntity());
        tagService.addTag(TestDummy.CreateTestTagRequestDto());

        verify(tagRepository).save(any(Tag.class));
    }

    @DisplayName("tagService addTag fail 테스트(title 중복)")
    @Test
    void addSameTitleTagTest() {
        when(tagRepository.existsByTitle(any())).thenReturn(true);
        when(tagRepository.save(any(Tag.class))).thenReturn(TestDummy.CreateTestTagEntity());
        assertThatThrownBy(() -> tagService.addTag(TestDummy.CreateTestTagRequestDto()))
                .isInstanceOf(DuplicatedTagTitleException.class)
                        .hasMessage("중복된 태그명입니다");

        verify(tagRepository).existsByTitle(any());
        verify(tagRepository, never()).save(any(Tag.class));
    }

    @DisplayName("tagService modifyTag 테스트")
    @Test
    void modifyTagTest() {
        Tag tag = TestDummy.CreateTestTagEntity();
        when(tagRepository.findById(any())).thenReturn(Optional.of(tag));
        when(tagRepository.save(any(Tag.class))).thenReturn(tag);
        when(tagRepository.existsByTitle(any())).thenReturn(false);

        tagService.modifyTag(TestDummy.CreateTestTagRequestDto());

        verify(tagRepository).findById(any());
        verify(tagRepository).existsByTitle(any());
        verify(tagRepository).save(any(Tag.class));
    }

    @DisplayName("tagService modifyTag 테스트(이미 등록 된 태그명으로 변경하려는 경우)")
    @Test
    void modifyTagTitleToExistTitleTest() {
        Tag tag = TestDummy.CreateTestTagEntity();
        when(tagRepository.findById(any())).thenReturn(Optional.of(tag));
        when(tagRepository.save(any(Tag.class))).thenReturn(tag);
        when(tagRepository.existsByTitle(any())).thenReturn(true);

        assertThatThrownBy(()->tagService.modifyTag(TestDummy.CreateTestTagRequestDto()))
                .isInstanceOf(DuplicatedTagTitleException.class)
                        .hasMessage("중복된 태그명입니다");

        verify(tagRepository).existsByTitle(any());
        verify(tagRepository,never()).findById(any());
        verify(tagRepository,never()).save(any(Tag.class));
    }

    @DisplayName("tagService removeTag 테스트")
    @Test
    void removeTagTest() {
        tagService.removeTag(1);

        verify(tagRepository).deleteById(any());
    }

    @DisplayName("tagService findTag 테스트")
    @Test
    void findTagTest() {
        Tag tag = TestDummy.CreateTestTagEntity();
        when(tagRepository.findById(any())).thenReturn(Optional.of(tag));

        tagService.findTag(0);

        verify(tagRepository).findById(any());
    }

    @DisplayName("tagService findTag 테스트(못 찾는 경우)")
    @Test
    void findTagFailTest() {
        Tag tag = TestDummy.CreateTestTagEntity();
        when(tagRepository.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(()->tagService.findTag(0))
                .isInstanceOf(TagNotFoundException.class)
                        .hasMessage("해당 태그를 찾을 수 없습니다");

        verify(tagRepository).findById(any());
    }

    @DisplayName("tagService findTags 테스트")
    @Test
    void findTagsTest() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("title"));
        Page<Tag> page = new PageImpl<>(TestDummy.CreateTestTagEntityList(),pageable,100);
        when(tagRepository.findAll(pageable)).thenReturn(page);

        TagPageResponseDto<TagResponseDto, Tag> tags = tagService.findTags(pageable);

        verify(tagRepository).findAll(pageable);
        assertThat(tags.getDtoList()).hasSize(100);
        assertThat(tags.getTotalPage()).isEqualTo(10);
    }
}