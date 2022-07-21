package shop.gaship.gashipshoppingmall.addresslist.dto;

import lombok.Builder;
import lombok.Getter;
import shop.gaship.gashipshoppingmall.addressLocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

import javax.persistence.*;

/**
 * @author 최정우
 * @since 1.0
 */

@Getter
public class AddressListResponseDto {
    private final Integer addressListNo;

    private final AddressLocal addressLocal;

    private final String address;

    private final String addressDetail;

    private final String zipCode;

    @Builder
    public AddressListResponseDto(Integer addressListNo, AddressLocal addressLocal, String address, String addressDetail, String zipCode) {
        this.addressListNo = addressListNo;
        this.addressLocal = addressLocal;
        this.address = address;
        this.addressDetail = addressDetail;
        this.zipCode = zipCode;
    }
}
