package shop.gaship.gashipshoppingmall.addresslist.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.*;
import shop.gaship.gashipshoppingmall.addressLocal.dummy.AddressLocalDummy;
import shop.gaship.gashipshoppingmall.addressLocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.addressLocal.repository.AddressLocalRepository;
import shop.gaship.gashipshoppingmall.addresslist.dummy.NotNullDummy;
import shop.gaship.gashipshoppingmall.addresslist.entity.AddressList;
import shop.gaship.gashipshoppingmall.member.dummy.MemberDummy;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.membergrade.dummy.StatusCodeDummy;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.membergrade.repository.MemberGradeRepository;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;
import shop.gaship.gashipshoppingmall.statuscode.status.AddressStatus;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 최정우
 * @since 1.0
 */
@DataJpaTest
class AddressListRepositoryTest {

    @Autowired
    private AddressListRepository addressListRepository;

    @Autowired
    private AddressLocalRepository addressLocalRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    StatusCodeRepository statusCodeRepository;

    @Autowired
    MemberGradeRepository memberGradeRepository;

    @DisplayName("배송지목록중 사용상태가 '사용' 인것만 페이징해서 값을 불러오는지 테스트")
    @Test
    void findAllByStatusCode_StatusCodeNameTest() {
        StatusCode statusCode1 = statusCodeRepository.save(NotNullDummy.notNullRenewalPeriodStatusDummy());
        StatusCode statusCode2 = statusCodeRepository.save(NotNullDummy.notNullMemberStatusUseDummy());
        StatusCode statusCode3 = statusCodeRepository.save(NotNullDummy.notNullAddressListUseStatusDummy());
        StatusCode statusCode4 = statusCodeRepository.save(NotNullDummy.notNullAddressListDeleteStatusDummy());
        AddressLocal addressLocal1 = addressLocalRepository.save(NotNullDummy.notNullAddressUpperLocalDummy());
        AddressLocal addressLocal2 = addressLocalRepository.save(NotNullDummy.notNullAddressLocalDummy(addressLocal1));
        MemberGrade memberGrade1 = memberGradeRepository.save(NotNullDummy.notNullMemberGradeDummy(statusCode1));
        Member member1 = memberRepository.save(NotNullDummy.notNullRecommendedMemberDummy(statusCode2,memberGrade1));
        Member member2 = memberRepository.save(NotNullDummy.notNullMemberDummy1(member1,statusCode2,memberGrade1));

        IntStream.rangeClosed(1,15).forEach(i ->
                addressListRepository
                        .save(AddressList.builder()
                                .addressListNo(i)
                                .addressLocal(addressLocal2)
                                .member(member2)
                                .statusCode(statusCode3)
                                .address("경기도 안양시 비산동")
                                .addressDetail("현대아파트 65층 화장실")
                                .zipCode("12344")
                                .build()));
        IntStream.rangeClosed(1,10).forEach(i ->
                addressListRepository
                        .save(AddressList.builder()
                                .addressListNo(i + 15)
                                .addressLocal(addressLocal2)
                                .member(member2)
                                .statusCode(statusCode4)
                                .address("경기도 안양시 비산동")
                                .addressDetail("현대아파트 65층 화장실")
                                .zipCode("12344")
                                .build()));
        Pageable pageable = PageRequest.of(1, 10);

        Page<AddressList> pageList = addressListRepository.findAllByStatusCode_StatusCodeName(AddressStatus.USE.getValue(), pageable);

        assertThat(pageList.getTotalPages()).isEqualTo(2);
        assertThat(pageList.getTotalElements()).isEqualTo(15);
    }
}