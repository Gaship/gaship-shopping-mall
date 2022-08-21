package shop.gaship.gashipshoppingmall.member.event;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.dataprotection.util.Aes;
import shop.gaship.gashipshoppingmall.dataprotection.util.Sha512;
import shop.gaship.gashipshoppingmall.member.adapter.MemberAdapter;
import shop.gaship.gashipshoppingmall.member.dto.request.MemberCreationRequest;
import shop.gaship.gashipshoppingmall.member.dummy.MemberCreationRequestDummy;
import shop.gaship.gashipshoppingmall.member.dummy.StatusCodeDummy;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.event.domain.SignedUpEvent;
import shop.gaship.gashipshoppingmall.member.memberTestDummy.MemberBaseDummy;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.member.service.MemberService;
import shop.gaship.gashipshoppingmall.membergrade.dummy.MemberGradeDtoDummy;
import shop.gaship.gashipshoppingmall.membergrade.dummy.MemberGradeDummy;
import shop.gaship.gashipshoppingmall.membergrade.repository.MemberGradeRepository;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;
import shop.gaship.gashipshoppingmall.statuscode.status.MemberStatus;
import shop.gaship.gashipshoppingmall.statuscode.status.RenewalPeriod;
import shop.gaship.gashipshoppingmall.statuscode.status.UserAuthority;

/**
 * @author : 최겸준
 * @since 1.0
 */
@SpringBootTest
@RecordApplicationEvents
class MemberEventTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private ApplicationEvents events;

    @MockBean
    Aes aes;

    @MockBean
    Sha512 sha512;

    @MockBean
    MemberRepository memberRepository;

    @MockBean
    MemberGradeRepository memberGradeRepository;

    @MockBean
    StatusCodeRepository statusCodeRepository;

    MemberAdapter memberAdapter;

    @MockBean
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입시에 이벤트가 잘 동작한다.")
    void registerMember_event() {
        MemberCreationRequest dummy = MemberCreationRequestDummy.dummy();
        Member member = MemberBaseDummy.member1();
        ReflectionTestUtils.setField(member, "memberNo", 1);
        given(memberRepository.findById(anyInt()))
            .willReturn(Optional.of(member));
        given(statusCodeRepository.findByStatusCodeName(MemberStatus.ACTIVATION.getValue()))
            .willReturn(Optional.of(StatusCodeDummy.dummy()));

        StatusCode statusCodeDummy = StatusCodeDummy.dummy();
        ReflectionTestUtils.setField(statusCodeDummy, "explanation", "12");
        given(statusCodeRepository.findByStatusCodeName(RenewalPeriod.PERIOD.getValue()))
            .willReturn(Optional.of(statusCodeDummy));

        given(memberGradeRepository.findByDefaultGrade()).willReturn(
            MemberGradeDummy.defaultDummy(
                MemberGradeDtoDummy.requestDummy("일반", 0L),
                StatusCodeDummy.dummy()
            ));
        given(statusCodeRepository.findByStatusCodeName(UserAuthority.MEMBER.getValue()))
            .willReturn(Optional.of(StatusCodeDummy.dummy()));

        memberService.addMember(dummy);

        int count = (int) events.stream(SignedUpEvent.class).count();
        assertThat(count)
            .isEqualTo(1);
    }
}
