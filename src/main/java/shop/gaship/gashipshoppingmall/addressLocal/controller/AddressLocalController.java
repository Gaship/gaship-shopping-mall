package shop.gaship.gashipshoppingmall.addressLocal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipshoppingmall.addressLocal.service.AddressLocalService;

/**
 * packageName    : shop.gaship.gashipshoppingmall.addressLocal.controller
 * fileName       : AddressLocalController
 * author         : 유호철
 * date           : 2022/07/12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/12        유호철       최초 생성
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/addressLocals")
public class AddressLocalController {
    private final AddressLocalService service;


}
