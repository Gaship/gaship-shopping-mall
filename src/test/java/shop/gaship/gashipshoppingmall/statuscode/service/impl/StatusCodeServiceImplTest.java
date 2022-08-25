package shop.gaship.gashipshoppingmall.statuscode.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipshoppingmall.statuscode.dto.response.StatusCodeResponseDto;
import shop.gaship.gashipshoppingmall.statuscode.dummy.StatusCodeDummy;
import shop.gaship.gashipshoppingmall.statuscode.dummy.StatusCodeResponseDtoDummy;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.exception.StatusCodeNotFoundException;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;
import shop.gaship.gashipshoppingmall.statuscode.service.StatusCodeService;
import shop.gaship.gashipshoppingmall.statuscode.status.SalesStatus;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * 상태코드 service 테스트
 *
 * @author : 김세미
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(StatusCodeServiceImpl.class)
class StatusCodeServiceImplTest {
    @Autowired
    private StatusCodeService statusCodeService;

    @MockBean
    private StatusCodeRepository statusCodeRepository;

    private StatusCode statusCodeDummy;

    @BeforeEach
    void setUp() {
        statusCodeDummy = StatusCodeDummy.dummy();
    }

    @DisplayName("회원등급 갱신기간을 수정하는 경우 - 성공하는 경우")
    @Test
    void modifyRenewalPeriod() {
        Integer testPeriod = 12;

        when(statusCodeRepository.findByStatusCodeName(any()))
                .thenReturn(Optional.ofNullable(statusCodeDummy));

        assertThatNoException()
                .isThrownBy(() -> statusCodeService.modifyRenewalPeriod(testPeriod));

        verify(statusCodeRepository).findByStatusCodeName(any());
    }

    @DisplayName("회원등급 갱신기간 수정시 해당 상태코드가 없는 경우")
    @Test
    void modifyRenewalPeriod_throwException() {
        Integer testPeriod = 12;

        when(statusCodeRepository.findByStatusCodeName(any()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> statusCodeService.modifyRenewalPeriod(testPeriod))
                .isInstanceOf(StatusCodeNotFoundException.class);

        verify(statusCodeRepository).findByStatusCodeName(any());
    }

    @DisplayName("특정 상태그룹에 속해있는 상태코드 목록 조회하는 경우")
    @Test
    void findStatusCodes() {
        when(statusCodeRepository.getStatusCodesByGroup(any()))
                .thenReturn(StatusCodeResponseDtoDummy.listDummy());

        List<StatusCodeResponseDto> result = statusCodeService
                .findStatusCodes(SalesStatus.GROUP);

        assertThat(result).hasSize(4);
        assertThat(result.get(0).getStatusCodeName())
                .isEqualTo(SalesStatus.SALE.getValue());
    }
}