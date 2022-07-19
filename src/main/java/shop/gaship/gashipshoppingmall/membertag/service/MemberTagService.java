package shop.gaship.gashipshoppingmall.membertag.service;

import shop.gaship.gashipshoppingmall.membertag.dto.MemberTagRequestDto;
import shop.gaship.gashipshoppingmall.membertag.dto.MemberTagResponseDto;
import shop.gaship.gashipshoppingmall.membertag.entity.MemberTag;

import java.util.List;

/**
 * The interface Member tag service.
 *
 * @author 최정우
 * @since 1.0
 */
public interface MemberTagService {
    /**
     * 회원이 설정한 태그목록을 삭제하고 회원이 등록하려는 모든 태그를 등록하는 메서드입니다.
     *
     * @param memberTagRequestDto the member tag request dto
     */
    void deleteAllAndAddAllMemberTags(MemberTagRequestDto memberTagRequestDto);

    /**
     * 회원이 설정한 태그목록을 가져오는 메서드입니다.
     *
     * @param memberNo the member no
     * @return the list
     */
    List<MemberTagResponseDto> findMemberTags(Integer memberNo);

    default MemberTagResponseDto entityToDto(MemberTag memberTag){
        return MemberTagResponseDto
                .builder()
                .tag(memberTag.getTag())
                .build();
    }
}
