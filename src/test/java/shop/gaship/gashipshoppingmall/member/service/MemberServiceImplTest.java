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
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;
import shop.gaship.gashipshoppingmall.member.memberTestDummy.MemberTestDummy;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.membergrade.repository.MemberGradeRepository;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
        when(memberRepository.save(any(Member.class))).thenReturn(MemberTestDummy.member1());
        when(memberRepository.findByNickname(any())).thenReturn(Optional.of(MemberTestDummy.member1()));
        when(memberGradeRepository.findById(0)).thenReturn(Optional.of(MemberTestDummy.memberGrade()));
        when(statusCodeRepository.findByStatusCodeName("활성")).thenReturn(Optional.of(MemberTestDummy.statusCode()));

        memberService.addMember(MemberTestDummy.memberRegisterRequestDto());

        verify(memberRepository, times(1))
                .save(any(Member.class));
    }

    @DisplayName("memberRepository modify Test")
    @Test
    void modifyTest() {
        when(memberRepository.findById(1)).thenReturn(Optional.of(MemberTestDummy.member1()));

        memberService.modifyMember(MemberTestDummy.memberModifyRequestDto());

        verify(memberRepository, times(1))
                .findById(any());
        verify(memberRepository, times(1))
                .save(any(Member.class));
    }

    @DisplayName("memberRepository modify fail Test(중복 pk)")
    @Test
    void modifyFailTest() {
        when(memberRepository.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(()-> memberService.modifyMember(MemberTestDummy.memberModifyRequestDto()))
                .isInstanceOf(MemberNotFoundException.class)
                        .hasMessage("해당 멤버를 찾을 수 없습니다");

        verify(memberRepository, times(1))
                .findById(any());

    }

    @DisplayName("memberRepository delete Test")
    @Test
    void deleteTest() {
        memberService.removeMember(1);

        verify(memberRepository).deleteById(1);
    }

    @DisplayName("memberRepository get Test")
    @Test
    void getTest() {
        when(memberRepository.findById(1)).thenReturn(Optional.of(MemberTestDummy.member1()));

        memberService.findMember(1);

        verify(memberRepository).findById(1);
    }

    @DisplayName("memberRepository getList Test")
    @Test
    void getListTest() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Member> memberList = List.of(MemberTestDummy.member1(), MemberTestDummy.member2());
        Page<Member> page = new PageImpl<>(memberList);
        when(memberRepository.findAll(any(Pageable.class))).thenReturn(page);

        List<MemberResponseDto> list = memberService.findMembers(pageable);

        assertThat(list).hasSize(2);
        verify(memberRepository).findAll(pageable);
    }
}