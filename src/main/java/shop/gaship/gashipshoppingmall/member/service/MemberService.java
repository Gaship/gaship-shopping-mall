package shop.gaship.gashipshoppingmall.member.service;

import shop.gaship.gashipshoppingmall.member.dto.MemberCreationRequest;

public interface MemberService {

    void registerMember(MemberCreationRequest memberCreationRequest);

    boolean isAvailableEmail(String email);

}
