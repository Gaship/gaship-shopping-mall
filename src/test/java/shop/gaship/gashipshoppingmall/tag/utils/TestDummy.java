package shop.gaship.gashipshoppingmall.tag.utils;

import shop.gaship.gashipshoppingmall.tag.dto.TagRequestDto;
import shop.gaship.gashipshoppingmall.tag.dto.TagResponseDto;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
public class TestDummy {
    public static TagRequestDto CreateTestTagRequestDto() {
        TagRequestDto tagRequestDto = TagRequestDto.builder().title("테스트 타이틀").build();

        return tagRequestDto;
    }

    public static TagResponseDto CreateTestTagResponseDto(String title) {
        return new TagResponseDto(title, LocalDateTime.now(), LocalDateTime.now());
    }

    public static Tag CreateTestTagEntity() {
        return Tag.builder().tagNo(1).title("title....1").build();
    }

    public static List<Tag> CreateTestTagEntityList() {
        List<Tag> list = new ArrayList<>();
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Tag tag = Tag.builder().tagNo(i).title("title....." + i).build();
            list.add(tag);
        });
        return list;
    }
}
