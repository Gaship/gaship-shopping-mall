package shop.gaship.gashipshoppingmall.tag.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Test
    @DisplayName("tagService register success 테스트")
    void register() {

        when(tagRepository.save(any(Tag.class))).thenReturn(Tag.builder().tagNo(1).title(title).build());
        TagResponseDto tagResponseDto = tagService.register(TestUtils.CreateTestTagRequestDto(title));

        assertThat(tagResponseDto.getTagNo()).isEqualTo(1);
        assertThat(tagResponseDto.getTitle()).isEqualTo(title);
        verify(tagRepository).save(any(Tag.class));
    }

    @Test
    void modify() {
        Tag tag = Tag.builder().tagNo(1).title(title).build();
        Tag modifiedTag = Tag.builder().tagNo(1).title(modifiedTitle).build();
        when(tagRepository.findById(any())).thenReturn(Optional.of(tag));
        when(tagRepository.save(any(Tag.class))).thenReturn(modifiedTag);

        assertThat(tag.getTitle()).isEqualTo(title);

        TagResponseDto tagResponseDto = tagService.modify(TestUtils.CreateTestTagRequestDto(modifiedTitle), tag.getTagNo());
        assertThat(tag.getTagNo()).isEqualTo(1);
        assertThat(tag.getTitle()).isEqualTo(modifiedTitle);
        assertThat(tagResponseDto.getTagNo()).isEqualTo(tag.getTagNo());
        assertThat(tagResponseDto.getTitle()).isEqualTo(modifiedTitle);
        verify(tagRepository).findById(any());
        verify(tagRepository).save(any(Tag.class));


    }

    @Test
    void delete() {
        int TagNo = 1;
        tagService.delete(1);
        verify(tagRepository).deleteById(any());
    }

    @Test
    void get() {
        int TagNo = 1;
        Tag tag = Tag.builder().tagNo(1).title(title).build();
        when(tagRepository.findById(any())).thenReturn(Optional.ofNullable(tag));
        TagResponseDto tagResponseDto = tagService.get(TagNo);
        assertThat(tagResponseDto.getTagNo()).isEqualTo(tag.getTagNo());
        assertThat(tagResponseDto.getTitle()).isEqualTo(tag.getTitle());


    }

    @Test
    void getList() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("title"));
        Tag tag1 = Tag.builder().tagNo(1).title(title).build();
        Tag tag2 = Tag.builder().tagNo(2).title(title).build();
        when(tagRepository.findAll(pageable)).thenReturn(new PageImpl<Tag>(List.of(tag1,tag2)));
        List<TagResponseDto> list = tagService.getList(pageable);
        verify(tagRepository).findAll(pageable);
        assertThat(list).hasSize(2);
    }
}