package shop.gaship.gashipshoppingmall.addressLocal.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.gaship.gashipshoppingmall.addressLocal.repository.AddressLocalRepository;
import shop.gaship.gashipshoppingmall.addressLocal.service.AddressLocalService;

/**
 * packageName    : shop.gaship.gashipshoppingmall.addressLocal.service.impl fileName       :
 * AddressLocalServiceipml author         : 유호철 date           : 2022/07/12 description    :
 * =========================================================== DATE              AUTHOR
 * NOTE ----------------------------------------------------------- 2022/07/12        유호철       최초
 * 생성
 */
@Service
@RequiredArgsConstructor
public class AddressLocalServiceipml implements AddressLocalService {

    private final AddressLocalRepository repository;

}
