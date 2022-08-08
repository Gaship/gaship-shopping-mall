package shop.gaship.gashipshoppingmall.membertag.service;

import java.util.List;
import shop.gaship.gashipshoppingmall.membertag.dto.request.MemberTagRequestDto;
import shop.gaship.gashipshoppingmall.membertag.dto.response.MemberTagResponseDto;
import shop.gaship.gashipshoppingmall.membertag.entity.MemberTag;

/**
 * 멤버태그 서비스 인터페이스.
 *
 * @author 최정우
 * @since 1.0
 */
public interface MemberTagService {
    /**
     * 회원이 설정한 태그목록을 삭제하고 회원이 등록하려는 모든 태그를 등록하는 메서드입니다.
     *
     * @param memberTagRequestDto 회원의 id 와 태그들의 id 가 들어있습니다.
     */
    void deleteAllAndAddAllMemberTags(MemberTagRequestDto memberTagRequestDto);

    /**
     * 회원이 설정한 태그목록을 가져오는 메서드입니다.
     *
     * @param memberNo 회원의 id 입니다.
     * @return 태그들의 리스트가 있습니다.
     */
    List<MemberTagResponseDto> findMemberTags(Integer memberNo);

    /**
     * MemberTag 엔티티에서 MemberTagResponseDto 엔티티로 변환하는 메서드입니다.
     *
     * @param memberTag memberTag 엔티티 객체입니다.
     * @return MemberTagResponseDto 로 변환된 객체입니다.
     */
    default MemberTagResponseDto entityToDto(MemberTag memberTag) {
        return MemberTagResponseDto.builder()
                .tagNo(memberTag.getTag().getTagNo())
                .title(memberTag.getTag().getTitle())
                .build();

    }
}
