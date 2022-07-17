package shop.gaship.gashipshoppingmall.statuscode.service;

import java.util.List;
import shop.gaship.gashipshoppingmall.statuscode.dto.response.StatusCodeResponseDto;

/**
 * 상태코드 Service Interface.
 *
 * @author : 김세미
 * @since 1.0
 */
public interface StatusCodeService {
    void modifyRenewalPeriod(String period);

    List<StatusCodeResponseDto> findStatusCodes(String groupCodeName);
}
