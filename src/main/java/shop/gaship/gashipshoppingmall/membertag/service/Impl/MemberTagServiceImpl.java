package shop.gaship.gashipshoppingmall.membertag.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.membertag.dto.request.MemberTagRequestDto;
import shop.gaship.gashipshoppingmall.membertag.dto.response.MemberTagResponseDto;
import shop.gaship.gashipshoppingmall.membertag.entity.MemberTag;
import shop.gaship.gashipshoppingmall.membertag.exception.IllegalTagSelectionException;
import shop.gaship.gashipshoppingmall.membertag.repository.MemberTagRepository;
import shop.gaship.gashipshoppingmall.membertag.service.MemberTagService;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;
import shop.gaship.gashipshoppingmall.tag.repository.TagRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 멤버태그 service interface 의 구현체입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class MemberTagServiceImpl implements MemberTagService {
    private final MemberTagRepository memberTagRepository;
    private final MemberRepository memberRepository;
    private final TagRepository tagRepository;

    /**
     * 멤버태그 삭제,등록을 하기 위한 메서드
     *
     * @param request 삭제에 필요한 memberNo 와 등록에 필요한 태그 고유번호(5개)를 담는 dto 입니다.
     * @Exception IllegalTagSelectionException dto 를 사용해 조회를 할 때 태그의 갯수가 5개가 되지 않으면 나오는 예외입니다.
     * @Exception MemberNotFoundException 멤버조회에 실패하면 나오는 예외입니다.
     */
    @Transactional
    @Override
    public void deleteAllAndAddAllMemberTags(MemberTagRequestDto request) {
        memberTagRepository.deleteAllByMember_MemberNo(request.getMemberNo());
        Member member = memberRepository.findById(request.getMemberNo()).orElseThrow(MemberNotFoundException::new);
        List<Tag> tagList = tagRepository.findByTagNoIn(request.getTagNoList());
        if (tagList.size() != 5) {
            throw new IllegalTagSelectionException();
        }
        List<MemberTag> memberTags = new ArrayList<>();
        IntStream.rangeClosed(0, 4)
                .forEach(i -> memberTags.add(
                        MemberTag.builder()
                                .member(member)
                                .tag(tagList.get(i))
                                .build()));
        memberTagRepository.saveAll(memberTags);
    }

    /**
     * 멤버태그 삭제,등록을 하기 위한 메서드
     *
     * @param memberNo 회원의 식별번호입니다.
     * @Exception IllegalTagSelectionException dto 를 사용해 조회를 할 때 태그의 갯수가 5개가 되지 않으면 나오는 예외입니다.
     * @return MemberTagResponseDto
     */
    @Override
    public MemberTagResponseDto findMemberTags(Integer memberNo) {
        List<MemberTag> memberTags = memberTagRepository.findAllByMember_MemberNo(memberNo);
        if (memberTags.size() != 5) {
            throw new IllegalTagSelectionException();
        }
        return new MemberTagResponseDto(memberTags);
    }
}
