package shop.gaship.gashipshoppingmall.inquiry.repository.custom;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import shop.gaship.gashipshoppingmall.inquiry.dto.request.InquirySearchRequestDto;
import shop.gaship.gashipshoppingmall.inquiry.dto.response.InquiryDetailsResponseDto;
import shop.gaship.gashipshoppingmall.inquiry.dto.response.InquiryListResponseDto;

/**
 * 네이밍 쿼리 외의 방법으로 쿼리를 작성하고 요청하는 방법인 queryDsl을 사용하기위한 커스텀 interface입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@NoRepositoryBean
public interface InquiryRepositoryCustom {

    /**
     * 문의목록을 조회하는 기능입니다.
     *
     * @param pageable 페이징처리하기위한 정보가 들어있는 객체입니다.
     * @param inquirySearchRequestDto 검색에 필요한 조건정보가 들어있는 객체입니다.
     * @return InquiryListResponseDto타입을 List로 담는 Page객체를 반환합니다.
     * @author 최겸준
     */
    Page<InquiryListResponseDto> findAllThroughSearchDto(Pageable pageable,
        InquirySearchRequestDto inquirySearchRequestDto);

    /**
     * 문의를 상세조회하는 기능입니다.
     *
     * @param inquiryNo 문의를 조회할때 기준이 될 문의 번호입니다.
     * @return null 을 컨트롤하는 Optional 객체안에 InquiryDetailsResponseDto 들어가있는 형태로 반환합니다.
     * @author 최겸준
     */
    Optional<InquiryDetailsResponseDto> findDetailsById(int inquiryNo);
}
