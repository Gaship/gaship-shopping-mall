package shop.gaship.gashipshoppingmall.tag.dummy;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import shop.gaship.gashipshoppingmall.tag.dto.response.PageResponseDto;
import shop.gaship.gashipshoppingmall.tag.dto.request.TagAddRequestDto;
import shop.gaship.gashipshoppingmall.tag.dto.request.TagModifyRequestDto;
import shop.gaship.gashipshoppingmall.tag.dto.response.TagResponseDto;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * packageName    : shop.gaship.gashipshoppingmall.tag.utils
 * fileName       : TestDummy
 * author         : choijungwoo
 * date           : 2022/07/12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/12        choijungwoo       최초 생성
 */

public class TagDummy {
    public static TagAddRequestDto TagAddRequestDtoDummy() {
        return TagAddRequestDto
                .builder()
                .title("테스트 타이틀")
                .build();
    }

    public static TagModifyRequestDto TagModifyRequestDtoDummy() {
        return TagModifyRequestDto
                .builder()
                .tagNo(1)
                .title("테스트 타이틀")
                .build();
    }

    public static TagResponseDto TagResponseDtoDummy() {
        return TagResponseDto
                .builder()
                .tagNo(1)
                .title("태그 테스트 타이틀")
                .registerDatetime(LocalDateTime.now())
                .modifiedDatetime(LocalDateTime.now())
                .build();
    }

    public static List<Tag> TagDummyListUnPersist() {
        List<Tag> list = new ArrayList<>();
        IntStream.rangeClosed(1,5).forEach(i-> list.add(Tag.builder().title("title.." + i).build()));
        return list;
    }

    public static List<Tag> TagDummyListPersist() {
        List<Tag> list = new ArrayList<>();
        IntStream.rangeClosed(1,5).forEach(i-> list.add(Tag.builder().tagNo(i).title("title.." + i).build()));
        return list;
    }

    public static Tag TagDummyPersist() {
        return Tag.builder()
                .tagNo(1)
                .title("title....1")
                .build();
    }

    public static List<Tag> TagListDummyPersist() {
        List<Tag> list = new ArrayList<>();
        IntStream.rangeClosed(1, 100).forEach(i -> list.add(Tag.builder().tagNo(i).title("title....." + i).build()));
        return list;
    }

    public static List<TagResponseDto> TagResponseDtoListDummyPersist() {
        List<TagResponseDto> list = new ArrayList<>();
        IntStream.rangeClosed(1, 100).forEach(i -> list.add(TagResponseDto.builder().tagNo(i).title("title....." + i).registerDatetime(LocalDateTime.now()).modifiedDatetime(LocalDateTime.now()).build()));
        return list;
    }

    public static PageResponseDto<TagResponseDto,Tag> TagPageResponseDtoDummy() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Tag> list = new ArrayList<>();
        IntStream.rangeClosed(1, 100).forEach(i -> list.add(Tag.builder().title("title....." + i).build()));
        Page<Tag> page = new PageImpl<>(list, pageable, 100);
        Function<Tag, TagResponseDto> tagConvertor = (Tag tag)-> (TagResponseDto.builder()
                .title(tag.getTitle())
                .registerDatetime(tag.getRegisterDatetime())
                .modifiedDatetime(tag.getModifiedDatetime())
                .build());
        return new PageResponseDto<>(page,tagConvertor);
    }
}
