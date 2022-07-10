package shop.gaship.gashipshoppingmall.member.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shop.gaship.gashipshoppingmall.dataprotection.protection.Aes;
import shop.gaship.gashipshoppingmall.member.dto.MemberCreationRequest;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.member.service.MemberService;

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
    private final MemberRepository memberRepository;
    private final Aes aes;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void registerMember(MemberCreationRequest memberCreationRequest) {
        encodePrivacyUserInformation(memberCreationRequest);
        Member member = memberCreationRequest.creationRequestToMemberEntity(memberCreationRequest);

        memberRepository.saveAndFlush(member);
    }

    private void encodePrivacyUserInformation(MemberCreationRequest memberCreationRequest) {
        memberCreationRequest.setEmail(aes.aesECBEncode(memberCreationRequest.getEmail()));
        memberCreationRequest.setName(aes.aesECBEncode(memberCreationRequest.getName()));
        memberCreationRequest.setPhoneNumber(
            aes.aesECBEncode(memberCreationRequest.getPhoneNumber()));
        memberCreationRequest.setPassword(
            passwordEncoder.encode(memberCreationRequest.getPassword()));
    }
}
