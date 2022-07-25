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
    /**
     * 갱신기간 상태코드 값 수정.
     *
     * @param period String
     * @author 김세미
     */
    void modifyRenewalPeriod(String period);

    /**
     * 상태코드 그룹코드를 통해 해당 그룹 코드에 속해있는 상태코드 목록 조회.
     *
     * @param groupCodeName String
     * @return list StatusCodeResponseDto 를 담고 있는 List 반환.
     * @author 김세미
     */
    List<StatusCodeResponseDto> findStatusCodes(String groupCodeName);
}
