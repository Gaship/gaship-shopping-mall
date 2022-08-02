package shop.gaship.gashipshoppingmall.inquiry.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import shop.gaship.gashipshoppingmall.inquiry.dto.request.InquirySearchRequestDto;
import shop.gaship.gashipshoppingmall.inquiry.dto.response.InquiryListResponseDto;

/**
 * 네이밍 쿼리 외의 방법으로 쿼리를 작성하고 요청하는 방법인 queryDsl을 사용하기위한 커스텀 interface입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
@NoRepositoryBean
public interface InquiryRepositoryCustom {

    Page<InquiryListResponseDto> findAllThroughSearch(Pageable pageable, InquirySearchRequestDto inquirySearchRequestDto);
}
