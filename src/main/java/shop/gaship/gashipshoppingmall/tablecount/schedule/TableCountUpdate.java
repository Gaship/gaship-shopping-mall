package shop.gaship.gashipshoppingmall.tablecount.schedule;

import static shop.gaship.gashipshoppingmall.tablecount.nameenum.TableName.CUSTOMER_INQUIRY_ALL_TABLE_COUNT_NAME;
import static shop.gaship.gashipshoppingmall.tablecount.nameenum.TableName.CUSTOMER_INQUIRY_ANSWER_COMPLETE_TABLE_COUNT_NAME;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import shop.gaship.gashipshoppingmall.inquiry.repository.InquiryRepository;
import shop.gaship.gashipshoppingmall.inquiry.search.InquiryListSearch;
import shop.gaship.gashipshoppingmall.tablecount.entity.TableCount;
import shop.gaship.gashipshoppingmall.tablecount.service.TableCountService;

/**
 * 특정시간마다 카운트 쿼리를 발생시켜서 TableCount의 count와 동기화시키는 클래스입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TableCountUpdate {

    private final TableCountService tableCountService;
    private final InquiryRepository inquiryRepository;

    @Scheduled(fixedDelay = 24, timeUnit = TimeUnit.HOURS)
    @Transactional
    public void updateCustomerInquiryAnswerCount() {
        TableCount allTableCount = tableCountService.findByName(
            CUSTOMER_INQUIRY_ALL_TABLE_COUNT_NAME.getValue());
        allTableCount.setCount(inquiryRepository.getCustomerInquiryCount(new InquiryListSearch(false, null, null, null)));

        TableCount completeTableCount = tableCountService.findByName(
            CUSTOMER_INQUIRY_ANSWER_COMPLETE_TABLE_COUNT_NAME.getValue());
        completeTableCount.setCount(inquiryRepository.getCustomerInquiryCount(new InquiryListSearch(false, 15, null, null)));
        log.debug("-------------------------------------------------------------------- 고객문의 전체 count : " + allTableCount.getCount());
        log.debug("-------------------------------------------------------------------- 고객문의 답변완료 count : " + completeTableCount.getCount());
    }
}
