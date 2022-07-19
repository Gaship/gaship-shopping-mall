package shop.gaship.gashipshoppingmall.gradehistory.service;

import shop.gaship.gashipshoppingmall.gradehistory.dto.request.GradeHistoryAddRequestDto;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;

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
     * @param request 등급이력 등록 요청 dto.
     * @throws MemberNotFoundException 회원을 찾을 수 없습니다.
     * @author 김세미
     */
    void addGradeHistory(GradeHistoryAddRequestDto request);
}
