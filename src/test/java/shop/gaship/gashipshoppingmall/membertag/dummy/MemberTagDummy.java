package shop.gaship.gashipshoppingmall.membertag.dummy;

import shop.gaship.gashipshoppingmall.member.memberTestDummy.MemberTestDummy;
import shop.gaship.gashipshoppingmall.membertag.dto.MemberTagRequestDto;
import shop.gaship.gashipshoppingmall.membertag.dto.MemberTagResponseDto;
import shop.gaship.gashipshoppingmall.membertag.entity.MemberTag;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;
import shop.gaship.gashipshoppingmall.tag.utils.TestDummy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author 최정우
 * @since 1.0
 */
public class MemberTagDummy {
    public static MemberTagRequestDto memberTagRequestDtoDummy() {
        return MemberTagRequestDto
                .builder()
                .memberNo(1)
                .tagIds(List.of(1, 2, 3, 4, 5))
                .build();
    }

    public static List<MemberTagResponseDto> memberTagResponseDtoList() {
        List<MemberTagResponseDto> list = new ArrayList();
        IntStream.rangeClosed(0, 4).forEach(i -> {
            MemberTagResponseDto dto = MemberTagResponseDto.builder().tag(Tag.builder().tagNo(i).title("title......" + i).build()).build();
            list.add(dto);
        });
        return list;
    }

    public static List<MemberTag> memberTagList() {
        List<MemberTag> memberTagList = new ArrayList<>();
        IntStream.rangeClosed(0, 4).forEach(i -> {
            MemberTag memberTag = MemberTag.builder()
                    .member(MemberTestDummy.memberEntityNotFlushed())
                    .tag(Tag.builder().tagNo(i).title("title....." + i).build())
                    .build();
            memberTagList.add(memberTag);
        });
        return memberTagList;
    }

    public static List<MemberTag> memberTagList100Size() {
        List<MemberTag> memberTagList = new ArrayList<>();
        IntStream.rangeClosed(1, 100).forEach(i -> {
            MemberTag memberTag = MemberTag.builder()
                    .member(MemberTestDummy.member1())
                    .tag(Tag.builder().tagNo(i).title("title" + i).build())
                    .build();
            memberTagList.add(memberTag);
        });
        return memberTagList;
    }
}
