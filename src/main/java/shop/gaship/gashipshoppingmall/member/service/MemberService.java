package shop.gaship.gashipshoppingmall.member.service;

import org.springframework.stereotype.Service;
import shop.gaship.gashipshoppingmall.member.dto.MemberCreationRequest;

public interface MemberService {

    void registerMember(MemberCreationRequest memberCreationRequest);

}
