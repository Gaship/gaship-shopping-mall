package shop.gaship.gashipshoppingmall.addresslist.repository.impl;

import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import shop.gaship.gashipshoppingmall.addresslist.dto.response.AddressListResponseDto;
import shop.gaship.gashipshoppingmall.addresslist.entity.AddressList;
import shop.gaship.gashipshoppingmall.addresslist.entity.QAddressList;
import shop.gaship.gashipshoppingmall.addresslist.repository.AddressListRepositoryCustom;
import shop.gaship.gashipshoppingmall.addresslocal.entity.QAddressLocal;
import shop.gaship.gashipshoppingmall.member.entity.QMember;
import shop.gaship.gashipshoppingmall.statuscode.entity.QStatusCode;
import shop.gaship.gashipshoppingmall.statuscode.status.AddressStatus;

/**
 * 배송지목록 custom repository 구현체.
 *
 * @author 최정우
 * @since 1.0
 */
@Repository
public class AddressListRepositoryImpl
        extends QuerydslRepositorySupport implements AddressListRepositoryCustom {

    public AddressListRepositoryImpl() {
        super(AddressList.class);
    }

    @Override
    public Page<AddressListResponseDto> findAddressListByMemberId(Integer memberNo,
                                                                  Pageable pageable) {
        QAddressList addressList = QAddressList.addressList;
        QAddressLocal addressLocal = QAddressLocal.addressLocal;
        QStatusCode statusCode = QStatusCode.statusCode;
        QMember member = QMember.member;
        List<AddressListResponseDto> content =
                from(addressList)
                        .innerJoin(addressList.addressLocal, addressLocal)
                        .innerJoin(addressList.statusCode, statusCode)
                        .innerJoin(addressList.member, member)
                        .where(addressList.statusCode.statusCodeName
                                .eq(AddressStatus.USE.getValue())
                                .and(addressList.member.memberNo.eq(memberNo)))
                        .limit(Math.min(pageable.getPageSize(), 10))
                        .offset(pageable.getOffset())
                        .orderBy(addressList.addressListsNo.desc())
                        .select(Projections.constructor(AddressListResponseDto.class,
                                addressList.addressListsNo.as("addressListNo"),
                                addressList.addressLocal.addressName,
                                addressList.addressLocal.allowDelivery,
                                addressList.address,
                                addressList.addressDetail,
                                addressList.zipCode))
                        .fetch();

        return PageableExecutionUtils.getPage(content,
                pageable,
                () -> from(addressList)
                        .fetch()
                        .size());
    }
}
