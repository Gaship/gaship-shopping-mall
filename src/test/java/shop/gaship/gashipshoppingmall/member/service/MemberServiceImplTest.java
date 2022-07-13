package shop.gaship.gashipshoppingmall.member.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipshoppingmall.member.dto.MemberResponseDto;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.memberTestUtils.MemberTestUtils;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.membergrade.repository.MemberGradeRepository;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * packageName    : shop.gaship.gashipshoppingmall.member.service
 * fileName       : MemberServiceImplTest
 * author         : choijungwoo
 * date           : 2022/07/13
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/13        choijungwoo       최초 생성
 */
@ExtendWith({SpringExtension.class})
@Import(MemberServiceImpl.class)
class MemberServiceImplTest {
    @MockBean
    MemberRepository memberRepository;

    @MockBean
    StatusCodeRepository statusCodeRepository;

    @MockBean
    MemberGradeRepository memberGradeRepository;

    @Autowired
    MemberService memberService;

    @DisplayName("memberRepository register Test")
    @Test
    void registerTest() {
        when(memberRepository.save(any(Member.class))).thenReturn(MemberTestUtils.member1());
        when(memberRepository.findByNickname(any())).thenReturn(Optional.of(MemberTestUtils.member1()));
        when(memberGradeRepository.findById(0)).thenReturn(Optional.of(MemberTestUtils.memberGrade()));
        when(statusCodeRepository.findByStatusCodeName("활성")).thenReturn(Optional.of(MemberTestUtils.statusCode()));

        memberService.register(MemberTestUtils.memberRegisterRequestDto());

        verify(memberRepository, times(1))
                .save(any(Member.class));
    }

    @DisplayName("memberRepository modify Test")
    @Test
    void modifyTest() {
        when(memberRepository.findById(1)).thenReturn(Optional.of(MemberTestUtils.member1()));

        memberService.modify(MemberTestUtils.memberModifyRequestDto());

        verify(memberRepository, times(1))
                .findById(any());
        verify(memberRepository, times(1))
                .save(any(Member.class));
    }

    @DisplayName("memberRepository delete Test")
    @Test
    void deleteTest() {
        memberService.delete(1);

        verify(memberRepository).deleteById(1);
    }

    @DisplayName("memberRepository get Test")
    @Test
    void getTest() {
        when(memberRepository.findById(1)).thenReturn(Optional.of(MemberTestUtils.member1()));

        memberService.get(1);

        verify(memberRepository).findById(1);
    }

    @DisplayName("memberRepository getList Test")
    @Test
    void getListTest() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Member> memberList = List.of(MemberTestUtils.member1(), MemberTestUtils.member2());
        Page<Member> page = new PageImpl<>(memberList);
        when(memberRepository.findAll(any(Pageable.class))).thenReturn(page);

        List<MemberResponseDto> list = memberService.getList(pageable);

        assertThat(list).hasSize(2);
        verify(memberRepository).findAll(pageable);
    }
}