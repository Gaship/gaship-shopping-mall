package shop.gaship.gashipshoppingmall.addresslist.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.*;
import shop.gaship.gashipshoppingmall.addressLocal.dummy.AddressLocalDummy;
import shop.gaship.gashipshoppingmall.addresslist.entity.AddressList;
import shop.gaship.gashipshoppingmall.member.dummy.MemberDummy;
import shop.gaship.gashipshoppingmall.membergrade.dummy.StatusCodeDummy;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;

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

    @DisplayName("배송지목록중 사용상태가 '사용' 인것만 페이징해서 값을 불러오는지 테스트")
    @Test
    void findAllByStatusCode_StatusCodeNameTest() {
        IntStream.rangeClosed(1,15).forEach(i ->
                addressListRepository
                        .save(AddressList.builder()
                                .addressListNo(i)
                                .addressLocal(null)
                                .member(null)
                                .statusCode(StatusCodeDummy.forAddressListTestStatusCodeNameUseDummy())
                                .address("경기도 안양시 비산동")
                                .addressDetail("현대아파트 65층 화장실")
                                .zipCode("12344")
                                .build()));
        IntStream.rangeClosed(1,10).forEach(i ->
                addressListRepository
                        .save(AddressList.builder()
                                .addressListNo(i + 15)
                                .addressLocal(null)
                                .member(null)
                                .statusCode(StatusCodeDummy.forAddressListTestStatusCodeNameDeleteDummy())
                                .address("경기도 안양시 비산동")
                                .addressDetail("현대아파트 65층 화장실")
                                .zipCode("12344")
                                .build()));
        Pageable pageable = PageRequest.of(1, 10);

        Page<AddressList> pageList = addressListRepository.findAllByStatusCode_StatusCodeName("사용", pageable);

        assertThat(pageList.getTotalPages()).isEqualTo(2);
        assertThat(pageList.getTotalElements()).isEqualTo(15);
        pageList.stream().forEach(i -> System.out.println(i.getStatusCode().getStatusCodeName()));
    }
}