package shop.gaship.gashipshoppingmall.member.service;

import org.springframework.data.domain.Pageable;
import shop.gaship.gashipshoppingmall.member.dto.MemberModifyRequestDto;
import shop.gaship.gashipshoppingmall.member.dto.MemberRegisterRequestDto;
import shop.gaship.gashipshoppingmall.member.dto.MemberResponseDto;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

import java.time.LocalDate;
import java.util.List;

public interface MemberService {
    void register(MemberRegisterRequestDto memberRegisterRequestDto);

    void modify(MemberModifyRequestDto memberModifyRequestDto);

    void delete(Integer memberNo);

    MemberResponseDto get(Integer memberNo);

    List<MemberResponseDto> getList(Pageable pageable);

    default Member dtoToEntity(MemberRegisterRequestDto request, Member recommendMember, StatusCode statusCode, MemberGrade memberGrade,Boolean isBlackMember) {
        return Member.builder()
                .recommendMember(recommendMember)
                .memberStatusCodes(statusCode)
                .memberGrades(memberGrade)
                .email(request.getEmail())
                .password(request.getPassword())
                .phoneNumber(request.getPhoneNumber())
                .name(request.getName())
                .birthDate(request.getBirthDate())
                .nickname(request.getNickname())
                .gender(request.getGender())
                .accumulatePurchaseAmount(0L)
                .nextRenewalGradeDate(LocalDate.now())
                .isBlackMember(isBlackMember)
                .build();
    }

    default MemberResponseDto entityToDto(Member member) {
        return MemberResponseDto.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .phoneNumber(member.getPhoneNumber())
                .name(member.getName())
                .birthDate(member.getBirthDate())
                .nickname(member.getNickname())
                .gender(member.getGender())
                .accumulatePurchaseAmount(member.getAccumulatePurchaseAmount())
                .birthDate(member.getNextRenewalGradeDate())
                .registerDatetime(member.getRegisterDatetime())
                .modifyDatetime(member.getModifyDatetime())
                .build();
    }
}
