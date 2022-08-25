package shop.gaship.gashipshoppingmall.statuscode.repository;

import java.util.List;
import java.util.Optional;
import shop.gaship.gashipshoppingmall.statuscode.dto.response.RenewalPeriodResponseDto;
import shop.gaship.gashipshoppingmall.statuscode.dto.response.StatusCodeResponseDto;


/**
 * 상태코드 Custom Repository Interface.
 *
 * @author : 김세미
 * @since 1.0
 */
public interface StatusCodeRepositoryCustom {
    List<StatusCodeResponseDto> getStatusCodesByGroup(String groupCodeName);

    Optional<RenewalPeriodResponseDto> getRenewalPeriod(String renewalPeriodCodeName);
}
