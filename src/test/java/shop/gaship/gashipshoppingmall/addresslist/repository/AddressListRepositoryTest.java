package shop.gaship.gashipshoppingmall.addresslist.repository;

import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import shop.gaship.gashipshoppingmall.addresslist.dto.response.AddressListResponseDto;
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

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author 최정우
 * @since 1.0
 */
@DataJpaTest
class AddressListRepositoryTest {

    @Autowired
    StatusCodeRepository statusCodeRepository;
    @Autowired
    MemberGradeRepository memberGradeRepository;
    @Autowired
    private AddressListRepository addressListRepository;
    @Autowired
    private AddressLocalRepository addressLocalRepository;
    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("배송지목록중 사용상태가 '사용' 인것만 페이징해서 값을 불러오는지 테스트")
    @Test
    void findAddressListByMemberId() {
        StatusCode statusCode1 = statusCodeRepository.save(NotNullDummy.notNullAddressListUseStatusDummy());
        StatusCode statusCode2 = statusCodeRepository.save(NotNullDummy.notNullAddressListUseStatusDummy());
        StatusCode statusCode3 = statusCodeRepository.save(NotNullDummy.notNullAddressListDeleteStatusDummy());
        AddressLocal addressLocal1 = addressLocalRepository.save(NotNullDummy.notNullAddressUpperLocalDummy());
        MemberGrade memberGrade1 = memberGradeRepository.save(NotNullDummy.notNullMemberGradeDummy(statusCode1));
        Member member1 = memberRepository.save(NotNullDummy.notNullRecommendedMemberDummy(statusCode2, memberGrade1));

        System.out.println("=============================");
        IntStream.rangeClosed(1, 13).forEach(i ->
                addressListRepository
                        .save(AddressList.builder()
                                .addressListsNo(i)
                                .addressLocal(addressLocal1)
                                .member(member1)
                                .statusCode(statusCode2)
                                .address("경기도 안양시 비산동")
                                .addressDetail("현대아파트 65층 화장실")
                                .zipCode("12344")
                                .build()));
        IntStream.rangeClosed(13, 25).forEach(i ->
                addressListRepository
                        .save(AddressList.builder()
                                .addressListsNo(i + 5)
                                .addressLocal(addressLocal1)
                                .member(member1)
                                .statusCode(statusCode3)
                                .address("경기도 안양시 비산동")
                                .addressDetail("현대아파트 65층 화장실")
                                .zipCode("12344")
                                .build()));
        Pageable pageable = PageRequest.of(3, 4);

        System.out.println("-----------------------------");
        System.out.println();
        Page<AddressListResponseDto> pageList = addressListRepository.findAddressListByMemberId(member1.getMemberNo(), pageable);
        assertThat(pageList.getContent()).hasSize(1);
        assertThat(pageList.getTotalElements()).isEqualTo(13);
    }
}