package shop.gaship.gashipshoppingmall.inquiry.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import java.util.Objects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import shop.gaship.gashipshoppingmall.inquiry.dto.request.InquirySearchRequestDto;
import shop.gaship.gashipshoppingmall.inquiry.dto.response.InquiryListResponseDto;
import shop.gaship.gashipshoppingmall.inquiry.entity.Inquiry;
import shop.gaship.gashipshoppingmall.inquiry.entity.QInquiry;
import shop.gaship.gashipshoppingmall.inquiry.exception.InquirySearchBadRequestException;
import shop.gaship.gashipshoppingmall.inquiry.repository.custom.InquiryRepositoryCustom;
import shop.gaship.gashipshoppingmall.member.entity.QMember;
import shop.gaship.gashipshoppingmall.statuscode.entity.QStatusCode;

/**
 * CustomInquiryRepository를 구현한 클래스입니다.
 *
 * @author 최겸준
 * @see shop.gaship.gashipshoppingmall.inquiry.repository.custom.InquiryRepositoryCustom
 * @since 1.0
 */
public class InquiryRepositoryImpl extends QuerydslRepositorySupport
    implements InquiryRepositoryCustom {

    public InquiryRepositoryImpl() {
        super(Inquiry.class);
    }

    @Override
    public Page<InquiryListResponseDto> findAllThroughSearchDto(Pageable pageable,
                                                                InquirySearchRequestDto inquirySearchRequestDto) {
        QInquiry inquiry = QInquiry.inquiry;
        QMember member = QMember.member;
        QStatusCode statusCode = QStatusCode.statusCode;

        JPQLQuery query = getQueryFrom(inquiry, member, statusCode);
        setQuerySelect(inquiry, member, statusCode, query);

        BooleanBuilder builder = new BooleanBuilder();
        setBuilder(inquirySearchRequestDto, inquiry, builder);

        query.offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(inquiry.inquiryNo.desc())
            .where(builder);

        List<InquiryListResponseDto> content = query.fetch();

        return PageableExecutionUtils.getPage(content, pageable,
            () -> from(inquiry).where(builder).fetch().size());
    }

    private JPQLQuery<Inquiry> getQueryFrom(QInquiry inquiry, QMember member,
                                            QStatusCode statusCode) {
        return from(inquiry).innerJoin(inquiry.member, member)
            .innerJoin(inquiry.processStatusCode, statusCode);
    }

    private void setQuerySelect(QInquiry inquiry, QMember member, QStatusCode statusCode,
                                JPQLQuery query) {
        query.select(Projections.fields(InquiryListResponseDto.class, inquiry.inquiryNo,
            member.nickname.as("memberNickname"), statusCode.statusCodeName.as("processStatus"),
            inquiry.title, inquiry.registerDatetime));
    }

    private void setBuilder(InquirySearchRequestDto inquirySearchRequestDto, QInquiry inquiry,
                            BooleanBuilder builder) {
        if (Objects.nonNull(inquirySearchRequestDto.getMemberNo())) {
            builder.and(inquiry.member.memberNo.eq(inquirySearchRequestDto.getMemberNo()));
        }

        if (Objects.nonNull(inquirySearchRequestDto.getStatusCodeNo())) {
            builder.and(inquiry.processStatusCode.statusCodeNo.eq(inquirySearchRequestDto.getStatusCodeNo()));
        }

        if (isProductFalseAndProductNoExists(inquirySearchRequestDto)) {
            throw new InquirySearchBadRequestException();
        }

        if (isSearchByProductNo(inquirySearchRequestDto)) {
            builder.and(inquiry.product.no.eq(inquirySearchRequestDto.getProductNo()));
        }

        builder.and(inquiry.isProduct.eq(inquirySearchRequestDto.getIsProduct()));
    }

    private boolean isProductFalseAndProductNoExists(InquirySearchRequestDto inquirySearchRequestDto) {
        return Boolean.FALSE.equals(inquirySearchRequestDto.getIsProduct()) && Objects.nonNull(inquirySearchRequestDto.getProductNo());
    }

    private boolean isSearchByProductNo(InquirySearchRequestDto dto) {
        return Boolean.TRUE.equals(dto.getIsProduct()) && Objects.nonNull(dto.getProductNo());
    }
}
