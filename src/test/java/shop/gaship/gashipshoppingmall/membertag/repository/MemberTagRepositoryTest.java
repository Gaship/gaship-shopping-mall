package shop.gaship.gashipshoppingmall.membertag.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.memberTestDummy.MemberTestDummy;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.membertag.dummy.MemberTagDummy;
import shop.gaship.gashipshoppingmall.membertag.entity.MemberTag;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;
import shop.gaship.gashipshoppingmall.tag.repository.TagRepository;
import shop.gaship.gashipshoppingmall.tag.utils.TestDummy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 최정우
 * @since 1.0
 */
@DataJpaTest
class MemberTagRepositoryTest {
    @Autowired
    MemberTagRepository memberTagRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TagRepository tagRepository;

    @DisplayName("회원이 미리 설정한 태그 전부 삭제하고 다시 설정하는 테스트")
    @Test
    void deleteAllByMember_MemberNo() {
        Member member = memberRepository.save(MemberTestDummy.memberEntityNotFlushed());
        List<Tag> tags = tagRepository.saveAll(TestDummy.Create5SizeTestTagEntityList());
        List<MemberTag> memberTagList = new ArrayList<>();
        IntStream.rangeClosed(0,4).forEach(i-> memberTagList.add(MemberTag.builder().member(member).tag(tags.get(i)).build()));
        memberTagRepository.saveAll(memberTagList);
        assertThat(memberTagRepository.findAllByMember_MemberNo(1)).hasSize(5);
        memberTagRepository.deleteAllByMember_MemberNo(1);
        assertThat(memberTagRepository.findAllByMember_MemberNo(1)).isEmpty();
    }

    @DisplayName("회원이 미리 설정한 태그 리스트를 조회하는 테스트")
    @Test
    void findAllByMember_MemberNo() {
        Member member = memberRepository.save(MemberTestDummy.memberEntityNotFlushed());
        List<Tag> tags = tagRepository.saveAll(TestDummy.Create5SizeTestTagEntityList());
        List<MemberTag> memberTagList = new ArrayList<>();
        IntStream.rangeClosed(0,4).forEach(i-> memberTagList.add(MemberTag.builder().member(member).tag(tags.get(i)).build()));
        memberTagRepository.saveAll(memberTagList);
        assertThat(memberTagRepository.findAllByMember_MemberNo(1)).hasSize(5);
        List<MemberTag> memberTags = memberTagRepository.findAllByMember_MemberNo(member.getMemberNo());
        assertThat(memberTags).hasSize(5);
    }
}