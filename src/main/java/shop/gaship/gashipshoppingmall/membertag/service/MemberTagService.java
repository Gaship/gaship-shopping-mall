package shop.gaship.gashipshoppingmall.membertag.service;

import shop.gaship.gashipshoppingmall.membertag.dto.MemberTagRequestDto;
import shop.gaship.gashipshoppingmall.membertag.entity.MemberTag;

import java.util.List;

/**
 * @author 최정우
 * @since 1.0
 */
public interface MemberTagService {
    void deleteAllAndAddAllMemberTags(MemberTagRequestDto memberTagRequestDto);

    List<MemberTag> findMemberTags(Integer memberNo);
}
