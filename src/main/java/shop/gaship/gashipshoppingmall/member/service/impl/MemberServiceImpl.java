package shop.gaship.gashipshoppingmall.member.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.gaship.gashipshoppingmall.dataprotection.util.Aes;
import shop.gaship.gashipshoppingmall.member.dto.MemberCreationRequest;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.member.service.MemberService;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.membergrade.exception.MemberGradeNotFoundException;
import shop.gaship.gashipshoppingmall.membergrade.repository.MemberGradeRepository;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.exception.StatusCodeNotFoundException;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;

/**
 * packageName    : shop.gaship.gashipshoppingmall.member.service.impl <br/>
 * fileName       : MemberServiceImpl <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/10 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/10           김민수               최초 생성                         <br/>
 */
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private static final int MEMBER_STATUS_ID = 2;
    private static final int MEMBER_GRADE_ID = 1;

    private final MemberRepository memberRepository;
    private final StatusCodeRepository statusCodeRepository;
    private final MemberGradeRepository memberGradeRepository;
    private final Aes aes;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void registerMember(MemberCreationRequest memberCreationRequest) {
        Member recommendMember = memberRepository
            .findById(memberCreationRequest.getRecommendMemberNo())
            .orElse(null);
        StatusCode defaultStatus = statusCodeRepository.findById(MEMBER_STATUS_ID)
            .orElseThrow(StatusCodeNotFoundException::new);
        MemberGrade defaultGrade = memberGradeRepository.findById(MEMBER_GRADE_ID)
            .orElseThrow(MemberGradeNotFoundException::new);

        Member savedMember = creationRequestToMemberEntity(
            encodePrivacyUserInformation(memberCreationRequest),
            recommendMember,
            defaultStatus,
            defaultGrade
        );

        memberRepository.saveAndFlush(savedMember);
    }

    private MemberCreationRequest encodePrivacyUserInformation(
        MemberCreationRequest memberCreationRequest) {
        memberCreationRequest.setEmail(aes.aesECBEncode(memberCreationRequest.getEmail()));
        memberCreationRequest.setName(aes.aesECBEncode(memberCreationRequest.getName()));
        memberCreationRequest.setPhoneNumber(
            aes.aesECBEncode(memberCreationRequest.getPhoneNumber()));
        memberCreationRequest.setPassword(
            passwordEncoder.encode(memberCreationRequest.getPassword()));

        return memberCreationRequest;
    }

    @Override
    public boolean isAvailableEmail(String email) {
        try{
            findMemberFromEmail(email);
        } catch (MemberNotFoundException e){
            return false;
        }
        return true;
    }

    @Override
    public Member findMemberFromEmail(String email) {
        return memberRepository.findByEmail(email)
            .orElseThrow(MemberNotFoundException::new);
    }

    @Override
    public Member findMemberFromNickname(String nickName) {
        return null;
    }
}
