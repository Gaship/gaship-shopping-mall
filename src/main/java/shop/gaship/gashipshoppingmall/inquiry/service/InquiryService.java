package shop.gaship.gashipshoppingmall.inquiry.service;

import shop.gaship.gashipshoppingmall.inquiry.dto.request.InquiryAddRequestDto;
import shop.gaship.gashipshoppingmall.inquiry.dto.request.InquiryAnswerRequestDto;

/**
 * 문의요청을 처리할때 비지니스로직을 처리하는 service 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
public interface InquiryService {

    /**
     * 상품문의를 등록하기위해서 dto를 entity로 변환하는 작업을 처리합니다.
     *
     * @param inquiryAddRequestDto 상품문의 등록에 필요한 정보를 담고있는 DTO 객체입니다.
     * @author 최겸준
     */
    void addInquiry(InquiryAddRequestDto inquiryAddRequestDto);

    /**
     * 상품문의의 답변을 등록 또는 수정하기위해서 entity에 정보를 업데이트하는 작업을 처리합니다.
     *
     * @param inquiryAnswerRequestDto 상품문의 답변 등록에 필요한 정보를 담고 있는 DTO 객체입니다.
     * @author 최겸준
     */
    void addOrModifyInquiryAnswer(InquiryAnswerRequestDto inquiryAnswerRequestDto,
                                  Boolean isAddAnswer);

    /**
     * 문의를 실삭제하도록 repository에 요청하는 기능입니다.
     *
     * @param inquiryNo 삭제대상 문의번호입니다.
     * @author 최겸준
     */
    void deleteInquiry(Integer inquiryNo);

    /**
     * 문의답변을 삭제하도록 repository에 요청하는 기능입니다.
     *
     * @param inquiryNo 삭제대상 문의번호입니다.
     * @author 최겸준
     */
    void deleteAnswerInquiry(Integer inquiryNo);
}
