package shop.gaship.gashipshoppingmall.statuscode.dummy;

import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

/**
 * packageName    : shop.gaship.gashipshoppingmall.statuscode.dummy
 * fileName       : StatusCodeDummy
 * author         : 유호철
 * date           : 2022/07/12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/12        유호철       최초 생성
 */
public class StatusCodeDummy {
    private StatusCodeDummy(){

    }

    public static StatusCode dummy(){
        return new StatusCode("test",1,"tests","TEST");
    }
}
