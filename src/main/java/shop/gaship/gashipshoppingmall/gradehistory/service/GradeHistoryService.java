package shop.gaship.gashipshoppingmall.gradehistory.service;

import shop.gaship.gashipshoppingmall.gradehistory.dto.request.GradeHistoryAddRequestDto;
import shop.gaship.gashipshoppingmall.gradehistory.dto.request.GradeHistoryFindRequestDto;
import shop.gaship.gashipshoppingmall.gradehistory.dto.response.GradeHistoryResponseDto;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;
import shop.gaship.gashipshoppingmall.response.PageResponse;

/**
 * 등급이력 Service Interface.
 *
 * @author : 김세미
 * @since 1.0
 */
public interface GradeHistoryService {
    /**
     * 등급이력 등록 메서드.
     *
     * @param requestDto 등급이력 등록 요청 dto.
     * @throws MemberNotFoundException 회원을 찾을 수 없습니다.
     * @author 김세미
     */
    void addGradeHistory(GradeHistoryAddRequestDto requestDto);

    /**
     * 등급이력 다건조회 메서드.
     *
     * @param requestDto 등급이력 다건 조회 요청 dto.
     * @return pageResponse page 정보와 GradeHistoryFindRequestDto 목록을 담은 페이지 응답 객체 반환 (PageResponse)
     * @throws MemberNotFoundException 회원을 찾을 수 없습니다.
     * @author 김세미
     */
    PageResponse<GradeHistoryResponseDto> findGradeHistories(GradeHistoryFindRequestDto requestDto);
}
