package shop.gaship.gashipshoppingmall.statuscode.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import shop.gaship.gashipshoppingmall.statuscode.dto.response.StatusCodeResponseDto;
import shop.gaship.gashipshoppingmall.statuscode.dummy.StatusCodeDummy;
import shop.gaship.gashipshoppingmall.statuscode.dummy.StatusCodeResponseDtoDummy;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.status.SalesStatus;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 상태코드 Repository 테스트.
 *
 * @author : 김세미
 * @since 1.0
 */
@DataJpaTest
class StatusCodeRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private StatusCodeRepository statusCodeRepository;

    @DisplayName("상태코드 그룹명으로 상태코드 조회하는 경우")
    @Test
    void findByGroupCodeName() {
        StatusCode dummy = StatusCodeDummy.dummy();
        testEntityManager.persistAndFlush(dummy);
        testEntityManager.clear();

        Optional<StatusCode> result = statusCodeRepository
                .findByGroupCodeName(SalesStatus.GROUP);

        assertThat(result).isPresent();
        assertThat(result.get())
                .isNotNull()
                .isEqualTo(dummy);
    }

    @DisplayName("상태코드 그룹명으로 상태코드 조회시 해당 상태코드가 없는 경우")
    @Test
    void findByGroupCodeName_whenStatusCodeIsEmpty() {
        Optional<StatusCode> result = statusCodeRepository
                .findByGroupCodeName(SalesStatus.GROUP);

        assertThat(result).isEmpty();
    }

    @DisplayName("상태코드 그룹명으로 상태코드 목록 조회하는 경우")
    @Test
    void getStatusCodesByGroup() {
        List.of(StatusCodeDummy.dummy(),
                        StatusCodeDummy.dummy(),
                        StatusCodeDummy.dummy())
                .forEach(dummy -> testEntityManager
                        .persistAndFlush(dummy));
        testEntityManager.clear();

        List<StatusCodeResponseDto> result = statusCodeRepository
                .getStatusCodesByGroup(SalesStatus.GROUP);

        assertThat(result)
                .isNotEmpty()
                .hasSize(3);
    }
}