package shop.gaship.gashipshoppingmall.statuscode.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.gaship.gashipshoppingmall.statuscode.dto.response.RenewalPeriodResponseDto;
import shop.gaship.gashipshoppingmall.statuscode.dto.response.StatusCodeResponseDto;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.exception.StatusCodeNotFoundException;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;
import shop.gaship.gashipshoppingmall.statuscode.service.StatusCodeService;
import shop.gaship.gashipshoppingmall.statuscode.status.RenewalPeriod;

/**
 * 상태코드 Service 구현체.
 *
 * @author : 김세미
 * @see shop.gaship.gashipshoppingmall.statuscode.service.StatusCodeService
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class StatusCodeServiceImpl implements StatusCodeService {
    private final StatusCodeRepository statusCodeRepository;

    @Override
    public void modifyRenewalPeriod(Integer period) {
        StatusCode renewalPeriod = statusCodeRepository
                .findByStatusCodeName(RenewalPeriod.PERIOD.getValue())
                .orElseThrow(StatusCodeNotFoundException::new);

        renewalPeriod.modifyRenewalPeriod(period);
    }

    @Override
    public List<StatusCodeResponseDto> findStatusCodes(String groupCodeName) {
        return statusCodeRepository.getStatusCodesByGroup(groupCodeName);
    }

    @Override
    public RenewalPeriodResponseDto findRenewalPeriod() {
        return statusCodeRepository.getRenewalPeriod(RenewalPeriod.PERIOD.getValue())
                .orElseThrow(StatusCodeNotFoundException::new);
    }
}
