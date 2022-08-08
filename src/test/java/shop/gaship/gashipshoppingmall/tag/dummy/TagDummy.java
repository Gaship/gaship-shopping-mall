package shop.gaship.gashipshoppingmall.tag.dummy;

import org.springframework.data.domain.*;
import shop.gaship.gashipshoppingmall.response.PageResponse;
import shop.gaship.gashipshoppingmall.tag.dto.request.TagAddRequestDto;
import shop.gaship.gashipshoppingmall.tag.dto.request.TagModifyRequestDto;
import shop.gaship.gashipshoppingmall.tag.dto.response.TagResponseDto;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class TagDummy {
    public static TagAddRequestDto TagAddRequestDtoDummy(String title) {
        return new TagAddRequestDto(title);
    }

    public static TagModifyRequestDto TagModifyRequestDtoDummy(Integer tagNo, String title) {
        return new TagModifyRequestDto(tagNo,title);
    }

    public static TagResponseDto TagResponseDtoDummy(Integer tagNo,String title) {
        return TagResponseDto
                .builder()
                .tagNo(tagNo)
                .title(title)
                .registerDatetime(LocalDateTime.now())
                .modifyDatetime(LocalDateTime.now())
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

    public static Tag TagDummyPersist(Integer tagNo, String title) {
        return Tag.builder()
                .tagNo(tagNo)
                .title(title)
                .build();
    }

    public static List<Tag> TagListDummyPersist() {
        List<Tag> list = new ArrayList<>();
        IntStream.rangeClosed(1, 33).forEach(i -> list.add(Tag.builder().tagNo(i).title("title....." + i).build()));
        return list;
    }


    public static List<TagResponseDto> TagResponseDtoListDummy(){
        List<TagResponseDto> list = new ArrayList<>();
        IntStream.rangeClosed(1, 33).forEach(i -> list.add(TagResponseDto.builder().tagNo(i).title("title..." + i).registerDatetime(LocalDateTime.now()).modifyDatetime(LocalDateTime.now()).build()));
        return list;
    }
}
