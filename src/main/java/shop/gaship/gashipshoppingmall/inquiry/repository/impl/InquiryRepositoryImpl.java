package shop.gaship.gashipshoppingmall.inquiry.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import java.util.Objects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import shop.gaship.gashipshoppingmall.employee.entity.QEmployee;
import shop.gaship.gashipshoppingmall.inquiry.dto.request.InquirySearchRequestDto;
import shop.gaship.gashipshoppingmall.inquiry.dto.response.InquiryResponseDto;
import shop.gaship.gashipshoppingmall.inquiry.entity.Inquiry;
import shop.gaship.gashipshoppingmall.inquiry.entity.QInquiry;
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
    public Page<InquiryResponseDto> findAllThroughSearch(Pageable pageable,
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
            .offset(pageable.getPageNumber())
            .limit(pageable.getPageSize())
            .orderBy(qInquiry.inquiryNo.desc())
            .where(builder);

        List<InquiryResponseDto> content = query.fetch();

        return PageableExecutionUtils.getPage(content, pageable, () -> getQueryFrom(qInquiry, qMember, qEmployee, qStatusCode, qProduct)
            .where(builder).fetch().size());
    }

    private JPQLQuery<Inquiry> getQueryFrom(QInquiry qInquiry, QMember qMember, QEmployee qEmployee,
                                        QStatusCode qStatusCode, QProduct qProduct) {
        return from(qInquiry)
            .innerJoin(qInquiry.member, qMember)
            .innerJoin(qInquiry.processStatusCode, qStatusCode)
            .leftJoin(qInquiry.employee, qEmployee)
            .leftJoin(qInquiry.product, qProduct);
    }

    private void setQuerySelect(QInquiry qInquiry, QMember qMember, QEmployee qEmployee,
                                QStatusCode qStatusCode, QProduct qProduct, JPQLQuery query) {
        query.select(Projections.fields(InquiryResponseDto.class,
                    qInquiry.inquiryNo,
                    qMember.nickname.as("memberNickname"),
                    qEmployee.name.as("employeeName"),
                    qStatusCode.statusCodeName.as("processStatus"),
                    qProduct.name.as("productName"),
                    qInquiry.title,
                    qInquiry.inquiryContent,
                    qInquiry.registerDatetime,
                    qInquiry.answerContent,
                    qInquiry.answerRegisterDatetime,
                    qInquiry.answerModifyDatetime));
    }

    private void setBuilder(InquirySearchRequestDto dto,
                            QInquiry qInquiry, BooleanBuilder builder) {
        if (Objects.nonNull(dto.getMemberNo())) {
            builder.and(qInquiry.member.memberNo.eq(dto.getMemberNo()));
        }

        if (Objects.nonNull(dto.getStatus())) {
            builder.and(qInquiry.processStatusCode.statusCodeName.eq(dto.getStatus()));
        }

        if (Objects.nonNull(dto.getProductNo())) {
            builder.and(qInquiry.product.no.eq(dto.getProductNo()));
        }

        builder.and(qInquiry.isProduct.eq(dto.getIsProduct()));
    }
}
