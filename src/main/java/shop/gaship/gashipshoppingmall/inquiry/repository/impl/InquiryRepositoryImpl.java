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
import shop.gaship.gashipshoppingmall.employee.entity.QEmployee;
import shop.gaship.gashipshoppingmall.inquiry.dto.request.InquirySearchRequestDto;
import shop.gaship.gashipshoppingmall.inquiry.dto.response.InquiryListResponseDto;
import shop.gaship.gashipshoppingmall.inquiry.entity.Inquiry;
import shop.gaship.gashipshoppingmall.inquiry.entity.QInquiry;
import shop.gaship.gashipshoppingmall.inquiry.exception.InquiryNotFoundException;
import shop.gaship.gashipshoppingmall.inquiry.exception.InquirySearchBadRequestException;
import shop.gaship.gashipshoppingmall.inquiry.repository.custom.InquiryRepositoryCustom;
import shop.gaship.gashipshoppingmall.member.entity.QMember;
import shop.gaship.gashipshoppingmall.product.entity.QProduct;
import shop.gaship.gashipshoppingmall.statuscode.entity.QStatusCode;

/**
 * CustomInquiryRepository를 구현한 클래스입니다.
 *
 * @author 최겸준
 * @see shop.gaship.gashipshoppingmall.inquiry.repository.custom.InquiryRepositoryCustom
 * @since 1.0
 */
public class InquiryRepositoryImpl extends QuerydslRepositorySupport implements InquiryRepositoryCustom {

    public InquiryRepositoryImpl() {
        super(Inquiry.class);
    }

    @Override
    public Page<InquiryListResponseDto> findAllThroughSearch(Pageable pageable,
                                                             InquirySearchRequestDto dto) {
        QInquiry qInquiry = QInquiry.inquiry;
        QMember qMember = QMember.member;
        QEmployee qEmployee = QEmployee.employee;
        QStatusCode qStatusCode = QStatusCode.statusCode;
        QProduct qProduct = QProduct.product;

        JPQLQuery query = getQueryFrom(qInquiry, qMember, qEmployee, qStatusCode, qProduct);
        setQuerySelect(qInquiry, qMember, qEmployee, qStatusCode, qProduct, query);

        BooleanBuilder builder = new BooleanBuilder();
        setBuilder(dto, qInquiry, builder);

        query
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(qInquiry.inquiryNo.desc())
            .where(builder);

        List<InquiryListResponseDto> content = query.fetch();

        Page<InquiryListResponseDto> page = PageableExecutionUtils.getPage(
            content, pageable, () -> from(qInquiry).where(builder).fetch().size());
        return page;
    }

    private JPQLQuery<Inquiry> getQueryFrom(QInquiry qInquiry, QMember qMember, QEmployee qEmployee,
                                        QStatusCode qStatusCode, QProduct qProduct) {
        return from(qInquiry)
            .innerJoin(qInquiry.member, qMember)
            .innerJoin(qInquiry.processStatusCode, qStatusCode);
//            .leftJoin(qInquiry.employee, qEmployee)
//            .leftJoin(qInquiry.product, qProduct);
    }

    private void setQuerySelect(QInquiry qInquiry, QMember qMember, QEmployee qEmployee,
                                QStatusCode qStatusCode, QProduct qProduct, JPQLQuery query) {
        // 관리자페이지 고객문의, 상품상세페이지 상품문의
        // 제목                   답변상태        작성자     작성시간
        // 비밀번호를까먹었어요       답변대기        강한남자    2022-04-20T20:30:21.39
        // 침대가 고장난거같아요      답변완료        침대남     2022-04-20T20:30:21.39
        query.select(Projections.fields(InquiryListResponseDto.class,
                    qInquiry.inquiryNo,
                    qMember.nickname.as("memberNickname"),
                    qStatusCode.statusCodeName.as("processStatus"),
                    qInquiry.title,
                    qInquiry.registerDatetime));

//                    qEmployee.name.as("employeeName"),
//                    qInquiry.inquiryContent,
//                    qProduct.name.as("productName"),
//                    qInquiry.answerContent,
//                    qInquiry.answerRegisterDatetime,
//                    qInquiry.answerModifyDatetime
    }

    private void setBuilder(InquirySearchRequestDto dto,
                            QInquiry qInquiry, BooleanBuilder builder) {
        if (Objects.nonNull(dto.getMemberNo())) {
            builder.and(qInquiry.member.memberNo.eq(dto.getMemberNo()));
        }

        if (Objects.nonNull(dto.getStatusCodeNo())) {
            builder.and(qInquiry.processStatusCode.statusCodeNo.eq(dto.getStatusCodeNo()));
        }

        if (isProductFalseAndProductNoExists(dto)) {
            throw new InquirySearchBadRequestException();
        }

        if (isSearchByProductNo(dto)) {
            builder.and(qInquiry.product.no.eq(dto.getProductNo()));
        }

        builder.and(qInquiry.isProduct.eq(dto.getIsProduct()));
    }

    private boolean isProductFalseAndProductNoExists(InquirySearchRequestDto dto) {
        return Boolean.FALSE.equals(dto.getIsProduct())
            && Objects.nonNull(dto.getProductNo());
    }

    private boolean isSearchByProductNo(InquirySearchRequestDto dto) {
        return Boolean.TRUE.equals(dto.getIsProduct())
            && Objects.nonNull(dto.getProductNo());
    }
}
