package shop.gaship.gashipshoppingmall.membertag.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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

/**
 * MemberTagService 구현 클래스.
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
     * {@inheritDoc}
     *
     * @throws MemberNotFoundException      회원 id 값으로 회원 Repository 를 조회하는데 실패할 경우 나오는 예외입니다.
     * @throws IllegalTagSelectionException 회원 태그 리스트의 사이즈가 5가 아닐경우 발생하는 에외입니다.
     * @author 최정우
     */
    @Transactional
    @Override
    public void deleteAllAndAddAllMemberTags(MemberTagRequestDto memberTagRequestDto, Integer memberNo) {
        Member member = memberRepository.findById(memberNo)
                .orElseThrow(MemberNotFoundException::new);
        memberTagRepository.deleteAllByMember_MemberNo(memberNo);
        List<Tag> tagList = tagRepository.findAllById(memberTagRequestDto.getTagIds());
        if (tagList.size() != 5) {
            throw new IllegalTagSelectionException();
        }
        List<MemberTag> memberTags = new ArrayList<>();
        IntStream.rangeClosed(0, 4).forEach(
                i -> memberTags.add(MemberTag.builder()
                        .member(member)
                        .tag(tagList.get(i))
                        .build()));
        memberTagRepository.findAllByMember_MemberNo(member.getMemberNo());
        memberTagRepository.saveAll(memberTags);
    }

    /**
     * {@inheritDoc}
     *
     * @throws MemberNotFoundException      회원 id 값으로 회원 Repository 를 조회하는데 실패할 경우 나오는 예외입니다.
     * @throws IllegalTagSelectionException 회원 태그 리스트의 사이즈가 5가 아닐경우 발생하는 에외입니다.
     * @author 최정우
     */
    @Override
    public List<MemberTagResponseDto> findMemberTags(Integer memberNo) {
        if (!memberRepository.existsByMemberNo(memberNo)) {
            throw new MemberNotFoundException();
        }
        List<MemberTag> memberTags = memberTagRepository.findAllByMember_MemberNo(memberNo);
        if (memberTags.size() != 5 && memberTags.size() != 0) {
            throw new IllegalTagSelectionException();
        }
        return memberTags.stream().map(this::entityToDto).collect(Collectors.toList());
    }
}
