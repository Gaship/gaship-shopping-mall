package shop.gaship.gashipshoppingmall.statuscode.dummy;

import shop.gaship.gashipshoppingmall.statuscode.dto.response.StatusCodeResponseDto;
import shop.gaship.gashipshoppingmall.statuscode.status.SalesStatus;

import java.util.List;

/**
 * 상태코드 관련 테스트 더미 data
 *
 * @author : 김세미
 * @since 1.0
 */
public class StatusCodeResponseDtoDummy {
    private StatusCodeResponseDtoDummy(){}

    public static StatusCodeResponseDto dummy(){
        StatusCodeResponseDto dummy = new StatusCodeResponseDto();
        dummy.setStatusCodeName(SalesStatus.SALE.getValue());
        dummy.setPriority(1);

        return dummy;
    }

    public static List<StatusCodeResponseDto> listDummy(){
        return List.of(dummy(), dummy(), dummy(), dummy());
    }
}
