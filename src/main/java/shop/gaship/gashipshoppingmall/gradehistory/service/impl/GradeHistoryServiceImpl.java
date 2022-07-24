package shop.gaship.gashipshoppingmall.gradehistory.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import shop.gaship.gashipshoppingmall.gradehistory.dto.request.GradeHistoryAddRequestDto;
import shop.gaship.gashipshoppingmall.gradehistory.dto.request.GradeHistoryFindRequestDto;
import shop.gaship.gashipshoppingmall.gradehistory.dto.response.GradeHistoryResponseDto;
import shop.gaship.gashipshoppingmall.gradehistory.entity.GradeHistory;
import shop.gaship.gashipshoppingmall.gradehistory.repository.GradeHistoryRepository;
import shop.gaship.gashipshoppingmall.gradehistory.service.GradeHistoryService;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.response.PageResponse;

/**
 * 등급이력 Service Interface 구현체.
 *
 * @author : 김세미
 * @see shop.gaship.gashipshoppingmall.gradehistory.service.GradeHistoryService
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
public class GradeHistoryServiceImpl implements GradeHistoryService {
    private final GradeHistoryRepository gradeHistoryRepository;
    private final MemberRepository memberRepository;

    /**
     * {@inheritDoc}
     *
     * @throws MemberNotFoundException 회원을 찾을 수 없습니다.
     */
    @Override
    public void addGradeHistory(GradeHistoryAddRequestDto requestDto) {
        Member member = memberRepository.findById(requestDto.getMemberNo())
                .orElseThrow(MemberNotFoundException::new);

        GradeHistory gradeHistory = GradeHistory.builder()
                .member(member)
                .request(requestDto)
                .build();

        gradeHistoryRepository.save(gradeHistory);
    }

    /**
     * {@inheritDoc}
     *
     * @throws MemberNotFoundException 회원을 찾을 수 없습니다.
     */
    @Override
    public PageResponse<GradeHistoryResponseDto>
        findGradeHistories(GradeHistoryFindRequestDto requestDto) {
        Member member = memberRepository.findById(requestDto.getMemberNo())
                .orElseThrow(MemberNotFoundException::new);

        return new PageResponse<>(gradeHistoryRepository
                .getGradeHistoriesByMember(member.getMemberNo(),
                        PageRequest.of(requestDto.getPage(), requestDto.getSize())));
    }
}
