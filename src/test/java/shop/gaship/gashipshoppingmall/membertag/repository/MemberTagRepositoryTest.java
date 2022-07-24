package shop.gaship.gashipshoppingmall.membertag.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.memberTestDummy.MemberTestDummy;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.membergrade.dto.request.MemberGradeAddRequestDto;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.membergrade.repository.MemberGradeRepository;
import shop.gaship.gashipshoppingmall.membertag.dummy.MemberTagDummy;
import shop.gaship.gashipshoppingmall.membertag.entity.MemberTag;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;
import shop.gaship.gashipshoppingmall.tag.dto.TagRequestDto;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;
import shop.gaship.gashipshoppingmall.tag.repository.TagRepository;
import shop.gaship.gashipshoppingmall.tag.utils.TestDummy;

import java.time.LocalDate;
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
    StatusCodeRepository statusCodeRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberGradeRepository memberGradeRepository;

    @Autowired
    TagRepository tagRepository;

    @DisplayName("회원이 미리 설정한 태그 전부 삭제하고 다시 설정하는 테스트")
    @Test
    void deleteAllByMember_MemberNo() {
        Tag tag1 = tagRepository.save(Tag.builder().tagNo(1).title("1").build());
        Tag tag2 = tagRepository.save(Tag.builder().tagNo(2).title("2").build());
        Tag tag3 = tagRepository.save(Tag.builder().tagNo(3).title("3").build());
        Tag tag4 = tagRepository.save(Tag.builder().tagNo(4).title("4").build());
        Tag tag5 = tagRepository.save(Tag.builder().tagNo(5).title("5").build());
        List<Tag> all = tagRepository.findAll();
        assertThat(all).hasSize(5);
        IntStream.rangeClosed(1,5).forEach(i-> assertThat(all.get(i).getTagNo()).isEqualTo(i));

        StatusCode statusCode = statusCodeRepository.save(StatusCode.builder().statusCodeName("a").priority(1).groupCodeName("1").explanation("1").build());
        MemberGradeAddRequestDto memberGradeAddRequestDto = new MemberGradeAddRequestDto();
        memberGradeAddRequestDto.setName("a");
        memberGradeAddRequestDto.setAccumulateAmount(0L);
        memberGradeAddRequestDto.setIsDefault(true);
        MemberGrade memberGrade = memberGradeRepository.save(MemberGrade.builder().renewalPeriod(statusCode).memberGradeAddRequestDto(memberGradeAddRequestDto).isDefault(true).build());

        Member member = memberRepository.save(Member.builder().recommendMember(null).memberStatusCodes(statusCode).memberGrades(memberGrade).email("jwoo1016@naver.com").password("1234123").phoneNumber("01012341234").name("최정우").birthDate(LocalDate.now()).nickname("최정우").gender("남").accumulatePurchaseAmount(0L).nextRenewalGradeDate(LocalDate.now()).build());

        assertThat(memberTagRepository.findAll().size()).isEqualTo(0);
        memberTagRepository.deleteAllByMember_MemberNo(member.getMemberNo());
        assertThat(memberTagRepository.findAll().size()).isEqualTo(0);
        assertThat(memberTagRepository.findAllByMember_MemberNo(member.getMemberNo())).hasSize(5);
        memberTagRepository.deleteAllByMember_MemberNo(member.getMemberNo());
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