package shop.gaship.gashipshoppingmall.addresslist.repository;

import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import shop.gaship.gashipshoppingmall.addresslist.dummy.NotNullDummy;
import shop.gaship.gashipshoppingmall.addresslist.entity.AddressList;
import shop.gaship.gashipshoppingmall.addresslocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.addresslocal.repository.AddressLocalRepository;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.membergrade.repository.MemberGradeRepository;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;
import shop.gaship.gashipshoppingmall.statuscode.status.AddressStatus;

import static org.assertj.core.api.Assertions.assertThat;

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
        Member member1 = memberRepository.save(NotNullDummy.notNullRecommendedMemberDummy(statusCode2, memberGrade1));
        Member member2 = memberRepository.save(NotNullDummy.notNullMemberDummy1(member1, statusCode2, memberGrade1));

        System.out.println("=============================");
        IntStream.rangeClosed(1, 5).forEach(i ->
            addressListRepository
                .save(AddressList.builder()
                    .addressListsNo(i)
                    .addressLocal(addressLocal2)
                    .member(member2)
                    .statusCode(statusCode3)
                    .address("경기도 안양시 비산동")
                    .addressDetail("현대아파트 65층 화장실")
                    .zipCode("12344")
                    .build()));
        IntStream.rangeClosed(1, 8).forEach(i ->
            addressListRepository
                .save(AddressList.builder()
                    .addressListsNo(i + 15)
                    .addressLocal(addressLocal2)
                    .member(member2)
                    .statusCode(statusCode4)
                    .address("경기도 안양시 비산동")
                    .addressDetail("현대아파트 65층 화장실")
                    .zipCode("12344")
                    .build()));
        Pageable pageable = PageRequest.of(1, 3);

        System.out.println("-----------------------------");
        Page<AddressList> pageList = addressListRepository.findByMember_MemberNoAndStatusCode_StatusCodeName(member2.getMemberNo(), AddressStatus.USE.getValue(), pageable);
        pageList.stream().forEach(i -> System.out.println(i.getStatusCode().getStatusCodeName()));
        assertThat(pageList.getTotalPages()).isEqualTo(2);
        assertThat(pageList.getTotalElements()).isEqualTo(5);
    }
}
