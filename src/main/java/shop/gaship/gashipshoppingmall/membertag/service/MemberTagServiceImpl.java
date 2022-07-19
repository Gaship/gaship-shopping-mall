package shop.gaship.gashipshoppingmall.membertag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.membertag.dto.MemberTagRequestDto;
import shop.gaship.gashipshoppingmall.membertag.dto.MemberTagResponseDto;
import shop.gaship.gashipshoppingmall.membertag.entity.MemberTag;
import shop.gaship.gashipshoppingmall.membertag.exception.IllegalTagSelectionException;
import shop.gaship.gashipshoppingmall.membertag.repository.MemberTagRepository;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;
import shop.gaship.gashipshoppingmall.tag.repository.TagRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author 최정우
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class MemberTagServiceImpl implements MemberTagService {
    private final MemberTagRepository memberTagRepository;
    private final MemberRepository memberRepository;
    private final TagRepository tagRepository;

    @Transactional
    @Override
    public void deleteAllAndAddAllMemberTags(MemberTagRequestDto memberTagRequestDto) {
        memberTagRepository.deleteAllByMember_MemberNo(memberTagRequestDto.getMemberNo());
        Member member = memberRepository.findById(memberTagRequestDto.getMemberNo()).orElseThrow(MemberNotFoundException::new);
        List<Tag> tagList = tagRepository.findByTagNoIn(memberTagRequestDto.getTagIds());
        if (tagList.size() != 5) {
            throw new IllegalTagSelectionException();
        }
        List<MemberTag> memberTags = new ArrayList<>();
        IntStream.rangeClosed(0, 4).forEach(i -> memberTags.add(MemberTag.builder().member(member).tag(tagList.get(i)).build()));
        memberTagRepository.saveAll(memberTags);
    }

    @Override
    public List<MemberTagResponseDto> findMemberTags(Integer memberNo) {
        List<MemberTag> memberTags = memberTagRepository.findAllByMember_MemberNo(memberNo);
        if (memberTags.size() != 5) {
            throw new IllegalTagSelectionException();
        }
        return memberTags.stream().map(this::entityToDto).collect(Collectors.toList());
    }
}
