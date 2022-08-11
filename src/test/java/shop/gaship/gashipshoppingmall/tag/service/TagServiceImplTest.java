package shop.gaship.gashipshoppingmall.tag.service;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipshoppingmall.response.PageResponse;
import shop.gaship.gashipshoppingmall.tag.dto.request.TagAddRequestDto;
import shop.gaship.gashipshoppingmall.tag.dto.request.TagModifyRequestDto;
import shop.gaship.gashipshoppingmall.tag.dto.response.TagResponseDto;
import shop.gaship.gashipshoppingmall.tag.dummy.TagDummy;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;
import shop.gaship.gashipshoppingmall.tag.exception.DuplicatedTagTitleException;
import shop.gaship.gashipshoppingmall.tag.exception.TagNotFoundException;
import shop.gaship.gashipshoppingmall.tag.repository.TagRepository;
import shop.gaship.gashipshoppingmall.tag.service.impl.TagServiceImpl;

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
        when(tagRepository.save(any(Tag.class))).thenReturn(TagDummy.TagDummyPersist(1,"엔틱"));

        tagService.addTag(TagDummy.TagAddRequestDtoDummy("모던"));

        verify(tagRepository).save(any(Tag.class));
    }

    @DisplayName("tagService addTag fail 테스트(title 중복)")
    @Test
    void addSameTitleTagTest() {
        when(tagRepository.existsByTitle(any())).thenReturn(true);
        when(tagRepository.save(any(Tag.class))).thenReturn(TagDummy.TagDummyPersist(1,"고풍스러운"));

        TagAddRequestDto dummy = TagDummy.TagAddRequestDtoDummy("고풍스러운");
        assertThatThrownBy(() -> tagService.addTag(dummy))
            .isInstanceOf(DuplicatedTagTitleException.class)
            .hasMessage("중복된 태그명입니다");

        verify(tagRepository).existsByTitle(any());
        verify(tagRepository, never()).save(any(Tag.class));
    }

    @DisplayName("tagService modifyTag 테스트")
    @Test
    void modifyTagTest() {
        Tag tag = TagDummy.TagDummyPersist(1,"고풍스러운");
        when(tagRepository.findById(any())).thenReturn(Optional.of(tag));
        when(tagRepository.existsByTitle(any())).thenReturn(false);

        tagService.modifyTag(TagDummy.TagModifyRequestDtoDummy(1,"엔틱"));

        verify(tagRepository).findById(any());
        verify(tagRepository).existsByTitle(any());
    }

    @DisplayName("tagService modifyTag 테스트(이미 등록 된 태그명으로 변경하려는 경우)")
    @Test
    void modifyTagTitleToExistTitleTest() {
        Tag tag = TagDummy.TagDummyPersist(1,"고풍스러운");
        when(tagRepository.findById(any())).thenReturn(Optional.of(tag));
        when(tagRepository.save(any(Tag.class))).thenReturn(tag);
        when(tagRepository.existsByTitle(any())).thenReturn(true);

        TagModifyRequestDto dummy = TagDummy.TagModifyRequestDtoDummy(1,"엔틱");
        assertThatThrownBy(() -> tagService.modifyTag(dummy))
            .isInstanceOf(DuplicatedTagTitleException.class)
            .hasMessage("중복된 태그명입니다");

        verify(tagRepository).existsByTitle(any());
        verify(tagRepository, never()).findById(any());
        verify(tagRepository, never()).save(any(Tag.class));
    }

    @DisplayName("tagService findTag 테스트")
    @Test
    void findTagTest() {
        Tag tag = TagDummy.TagDummyPersist(1,"화려한");
        when(tagRepository.findById(any())).thenReturn(Optional.of(tag));

        tagService.findTag(1);

        verify(tagRepository).findById(any());
    }

    @DisplayName("tagService findTag 테스트(못 찾는 경우)")
    @Test
    void findTagFailTest() {
        Tag tag = TagDummy.TagDummyPersist(1,"배고픈");
        when(tagRepository.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tagService.findTag(0)).isInstanceOf(TagNotFoundException.class).hasMessage("해당 태그를 찾을 수 없습니다");

        verify(tagRepository).findById(any());
    }

    @DisplayName("tagService findTags 테스트")
    @Test
    void findTagsTest() {
        when(tagRepository.findAll()).thenReturn(TagDummy.TagListDummyPersist());

        List<TagResponseDto> tags = tagService.findTags();

        verify(tagRepository).findAll();
        assertThat(tags).hasSize(33);
    }
}
