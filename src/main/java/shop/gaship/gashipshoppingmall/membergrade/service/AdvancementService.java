package shop.gaship.gashipshoppingmall.membergrade.service;

import java.util.List;
import shop.gaship.gashipshoppingmall.membergrade.dto.request.RenewalMemberGradeRequestDto;
import shop.gaship.gashipshoppingmall.membergrade.dto.response.AdvancementTargetResponseDto;

/**
 * 회원 승급 Service interface.
 *
 * @author : 김세미
 * @since 1.0
 */
public interface AdvancementService {
    /**
     * 등급 승급 대상이 되는 회원 다건 조회를 위한 메서드.
     *
     * @param renewalGradeDate 등급 승급 대상이 되는 회원을 조회하기 위한 기준 승급 날짜 (String).
     * @return List-AdvancementTargetResponseDto 등급 승급 대상이되는 회원 조회 결과인 dto 를 담은 list
     * @author 김세미
     */
    List<AdvancementTargetResponseDto> findTargetsByRenewalGradeDate(String renewalGradeDate);

    /**
     * 회원의 등급을 갱신하고 등급이력 등록을 위한 메서드.
     *
     * @param requestDto 등급 갱신 대상에 대한 정보와 등급 이력에 등록할 정보를 담은 dto.
     * @author 김세미
     */
    void renewalMemberGrade(RenewalMemberGradeRequestDto requestDto);
}
