package shop.gaship.gashipshoppingmall.membertag.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shop.gaship.gashipshoppingmall.member.memberTestDummy.MemberTestDummy;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.membertag.dummy.MemberTagDummy;
import shop.gaship.gashipshoppingmall.membertag.entity.MemberTag;
import shop.gaship.gashipshoppingmall.tag.repository.TagRepository;
import shop.gaship.gashipshoppingmall.tag.utils.TestDummy;

import java.util.List;

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
        memberRepository.save(MemberTestDummy.member1());
        tagRepository.saveAll(TestDummy.Create5SizeTestTagEntityList());
        List<MemberTag> memberTagList = MemberTagDummy.memberTagList();
        memberTagRepository.saveAll(memberTagList);
        assertThat(memberTagRepository.findAllByMember_MemberNo(0)).hasSize(5);
        memberTagRepository.deleteAllByMember_MemberNo(0);
        assertThat(memberTagRepository.findAllByMember_MemberNo(0)).isEmpty();
    }

    @Test
    void findAllByMember_MemberNo() {
    }
}