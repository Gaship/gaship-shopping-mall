package shop.gaship.gashipshoppingmall.inquiry.service;

import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.gaship.gashipshoppingmall.inquiry.dto.request.InquiryAddRequestDto;
import shop.gaship.gashipshoppingmall.inquiry.dto.request.InquiryAnswerRequestDto;
import shop.gaship.gashipshoppingmall.inquiry.dto.response.InquiryDetailsResponseDto;
import shop.gaship.gashipshoppingmall.inquiry.dto.response.InquiryListResponseDto;
import shop.gaship.gashipshoppingmall.inquiry.entity.Inquiry;
import shop.gaship.gashipshoppingmall.inquiry.search.InquiryListSearch;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

/**
 * 문의요청을 처리할때 비지니스로직을 처리하는 service 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
public interface InquiryService {

    /**
     * 문의를 등록하기위해서 dto를 entity로 변환하는 작업을 처리합니다.
     *
     * @param inquiryAddRequestDto 상품문의 등록에 필요한 정보를 담고있는 DTO 객체입니다.
     * @author 최겸준
     */
    void addInquiry(InquiryAddRequestDto inquiryAddRequestDto);

    /**
     * 문의의 답변을 등록하기위해서 entity에 정보를 업데이트하는 작업을 처리합니다.
     *
     * @param inquiryAnswerRequestDto 상품문의 답변 등록에 필요한 정보를 담고 있는 DTO 객체입니다.
     * @author 최겸준
     */
    void addInquiryAnswer(InquiryAnswerRequestDto inquiryAnswerRequestDto);

    /**
     * 문의의 답변을 수정하기위해서 entity에 정보를 업데이트하는 작업을 처리합니다.
     *
     * @param inquiryAnswerRequestDto 상품문의 답변 수정에 필요한 정보를 담고 있는 DTO 객체입니다.
     * @author 최겸준
     */
    void modifyInquiryAnswer(InquiryAnswerRequestDto inquiryAnswerRequestDto);

    /**
     * 본인이 문의를 실삭제하도록 repository에 요청하는 기능입니다.
     *
     * @param inquiryNo 삭제대상 문의번호입니다.
     * @param memberNo 삭제하려는 문의를 작성한 사람의 번호입니다.
     * @author 최겸준
     */
    void deleteInquiry(Integer inquiryNo, Integer memberNo);

    /**
     * 관리자 또는 직원이 문의를 실삭제하도록 repository에 요청하는 기능입니다.
     *
     * @param inquiryNo 삭제대상 문의번호입니다.
     * @author 최겸준
     */
    void deleteInquiryManager(Integer inquiryNo);

    /**
     * 문의답변을 삭제하도록 repository에 요청하는 기능입니다.
     *
     * @param inquiryNo 삭제대상 문의번호입니다.
     * @author 최겸준
     */
    void deleteInquiryAnswer(Integer inquiryNo);

    /**
     * 아무 조건없이 모든 고객 또는 상품문의를(목록) 조회하기 위한 기능입니다.
     *
     * @param pageable 페이징 처리를 위한 파라미터입니다.
     * @return PageImpl 객체를 반환합니다.
     * @author 최겸준
     */
    Page<InquiryListResponseDto> findProductInquiriesAll(Pageable pageable, Boolean isProduct);

    /**
     * 답변상태를 통해서 상품문의 및 고객문의를 찾는 기능입니다.
     *
     * @param pageable       페이징 처리를 위해 사용합니다.
     * @param isProduct      상품문의인지 고객문의인지 구분하기 위해서 사용합니다.
     * @param statusCodeName 답변대기 또는 답변완료의 값을 가지고 있으며 해당 상태를 통해서 문의를 조회합니다.
     * @return PageImpl 객체를 반환합니다.
     * @author 최겸준
     */
    Page<InquiryListResponseDto> findInquiriesByStatusCodeNo(Pageable pageable, Boolean isProduct,
                                                             String statusCodeName);

    /**
     * 특정회원번호를 기준으로 상품 또는 고객문의를 찾는 기능입니다. 주로 고객이 마이페이지 등에서 본인의
     * 문의를 직접 확인하기 위해 사용합니다.
     *
     * @param pageable  페이징 처리를 위해 사용합니다.
     * @param isProduct 상품문의인지 고객문의인지 구분하기 위해서 사용합니다.
     * @param memberNo  기준이 될 회원의 번호입니다.
     * @return PageImpl 객체를 반환합니다.
     * @author 최겸준
     */
    Page<InquiryListResponseDto> findInquiriesByMemberNo(Pageable pageable, Boolean isProduct,
                                                         Integer memberNo);

    /**
     * 특정상품번호를 기준으로 상품문의를 찾는 기능입니다. 주로 상품상세페이지에서 나타납니다.
     *
     * @param pageable  페이징 처리를 위해 사용합니다.
     * @param productNo 기준이 될 상품번호입니다.
     * @return PageImpl 객체를 반환합니다.
     * @author 최겸준
     */
    Page<InquiryListResponseDto> findInquiriesByProductNo(Pageable pageable, Integer productNo);

    /**
     * 직원 또는 관리자가 문의를 상세조회하는 기능입니다.
     *
     * @param inquiryNo 조회시 기준이 될 문의번호입니다.
     * @return 상세조회 후 반환된 entity에서 필요한 정보만 담은 dto가 반환됩니다.
     * @author 최겸준
     */
    InquiryDetailsResponseDto findInquiry(int inquiryNo);

    /**
     * 본인고객의 상품문의를 상세조회하는 기능입니다.
     * 상품문의는 본인의 상품이 아니더라도 조회가 가능함으로 memberNo가 필요 없습니다.
     *
     * @param inquiryNo 조회시 기준이 될 문의번호입니다.
     * @return 상세조회 후 반환된 entity에서 필요한 정보만 담은 dto가 반환됩니다.
     * @author 최겸준
     */
    InquiryDetailsResponseDto findProductInquiryMemberSelf(Integer inquiryNo);

    /**
     * 본인고객의 고객문의를 상세조회하는 기능입니다.
     * 고객문의는 본인의 문의만 조회가능함으로 memberNo가 필요합니다.
     *
     * @param inquiryNo 조회시 기준이 될 문의번호입니다.
     * @param memberNo 작성자의 번호와 요청한 사람의 번호가 일치하는지 확인하기 위한 기준번호입니다.
     * @return 상세조회 후 반환된 entity에서 필요한 정보만 담은 dto가 반환됩니다.
     * @author 최겸준
     */
    InquiryDetailsResponseDto findCustomerInquiryMemberSelf(Integer inquiryNo, Integer memberNo);

    /**
     * 문의등록 요청시에 넘어온 requestDto객체를 이용하여 영속화되지 않은 entity를 반환하는 기능입니다.
     *
     * @param inquiryAddRequestDto 문의등록에 필요한 정보가 담긴 DTO입니다.
     * @param statusCode           처리상태를 나타내는 것으로서 답변대기, 답변완료 2가지 경우가 있습니다.
     * @return 영속화되지 않은 Inquiry entity를 반환합니다.
     * @author 최겸준
     */
    default Inquiry inquiryAddRequestDtoToInquiryEntityWhenCreation(
        InquiryAddRequestDto inquiryAddRequestDto, StatusCode statusCode) {
        return Inquiry.builder().title(inquiryAddRequestDto.getTitle())
            .inquiryContent(inquiryAddRequestDto.getInquiryContent()).processStatusCode(statusCode)
            .isProduct(inquiryAddRequestDto.getIsProduct()).registerDatetime(LocalDateTime.now())
            .build();
    }


    Page<InquiryListResponseDto> findCustomerInquiriesAllOrStatusCompletePrevPage(Pageable pageable,
                                                                                  Integer inquiryNo,
                                                                                  InquiryListSearch inquiryListSearch);

    Page<InquiryListResponseDto> findCustomerInquiriesAllOrStatusCompleteNextPage(Pageable pageable,
                                                                                  Integer inquiryNo,
                                                                                  InquiryListSearch inquiryListSearch);

    Page<InquiryListResponseDto> findCustomerInquiriesAllOrStatusComplete(Pageable pageable,
                                                                          InquiryListSearch inquiryListSearch);

}
