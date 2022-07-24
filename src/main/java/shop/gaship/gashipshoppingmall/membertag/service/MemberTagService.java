package shop.gaship.gashipshoppingmall.membertag.service;

import shop.gaship.gashipshoppingmall.membertag.dto.request.MemberTagRequestDto;
import shop.gaship.gashipshoppingmall.membertag.dto.response.MemberTagResponseDto;
import shop.gaship.gashipshoppingmall.membertag.entity.MemberTag;

import java.util.List;

/**
 * MemberTag service Interface 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
public interface MemberTagService {
    /**
     * 회원이 설정한 태그목록을 삭제하고 등록하려는 모든 태그를 등록하는 메서드입니다.
     *
     * @param memberTagRequestDto 회원의 식별번호와 등록하고자하는 태그의 식별번호들을 담고있는 Dto 입니다.
     */
    void deleteAllAndAddAllMemberTags(MemberTagRequestDto memberTagRequestDto);

    /**
     * 회원이 설정한 태그목록을 가져오는 메서드입니다.
     *
     * @param memberNo 멤버의 식별번호
     * @return 멤버가 등록한 멤버태그리스트(5개)
     */
    MemberTagResponseDto findMemberTags(Integer memberNo);
}
