package shop.gaship.gashipshoppingmall.member.service;

import org.springframework.data.domain.Pageable;
import shop.gaship.gashipshoppingmall.member.dto.MemberModifyRequestDto;
import shop.gaship.gashipshoppingmall.member.dto.MemberAddRequestDto;
import shop.gaship.gashipshoppingmall.member.dto.MemberPageResponseDto;
import shop.gaship.gashipshoppingmall.member.dto.MemberResponseDto;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.membergrade.exception.MemberGradeNotFoundException;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

import java.time.LocalDate;
import java.util.List;

/**
 * member crud를 담당하는 service 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
public interface MemberService {
    /**
     * 멤버를 등록하는 메서드입니다.
     *
     * @param memberRegisterRequestDto the member register request dto
     * @throws MemberGradeNotFoundException the member grade not found exception
     */
    void addMember(MemberAddRequestDto memberRegisterRequestDto) throws MemberGradeNotFoundException;

    /**
     * 멤버의 정보를 변경하는 메서드 입니다.
     *
     * @param memberModifyRequestDto the member modify request dto
     */
    void modifyMember(MemberModifyRequestDto memberModifyRequestDto);

    /**
     * 멤버를 삭제하는 메서드 입니다.
     *
     * @param memberNo the member no
     */
    void removeMember(Integer memberNo);

    /**
     * 멤버를 단건조회하는 메서드입니다.
     *
     * @param memberNo the member no
     * @return the member response dto
     * @throws MemberNotFoundException the member not found exception
     */
    MemberResponseDto findMember(Integer memberNo);

    /**
     * 멤버를 다건조회하는 메서드입니다.
     *
     * @param pageable the pageable
     * @return the list
     */
    MemberPageResponseDto<MemberResponseDto,Member> findMembers(Pageable pageable);

    /**
     * Dto to entity member.
     *
     * @param request         the request
     * @param recommendMember the recommend member
     * @param statusCode      the status code
     * @param memberGrade     the member grade
     * @param isBlackMember   the is black member
     * @return the member
     */
    default Member dtoToEntity(MemberAddRequestDto request, Member recommendMember, StatusCode statusCode, MemberGrade memberGrade, Boolean isBlackMember) {
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
                .build();
    }

    /**
     * Entity to dto member response dto.
     *
     * @param member the member
     * @return the member response dto
     */
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
