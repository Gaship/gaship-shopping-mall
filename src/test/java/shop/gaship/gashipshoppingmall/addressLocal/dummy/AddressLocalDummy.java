package shop.gaship.gashipshoppingmall.addressLocal.dummy;

import shop.gaship.gashipshoppingmall.addressLocal.entity.AddressLocal;

/**
 * packageName    : shop.gaship.gashipshoppingmall.addressLocal.dummy
 * fileName       : AddressLocalDummt
 * author         : 유호철
 * date           : 2022/07/12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/12        유호철       최초 생성
 */
public class AddressLocalDummy {
     private AddressLocalDummy(){
     }

     public static AddressLocal dummy1(){
        return new AddressLocal("부산광역시",1,true,null,null,null);
     }

     public static AddressLocal dummy2(){
         return new AddressLocal("마산턱별시", 2, true, null, null,null);
     }

     public static AddressLocal dummy3(){
         return new AddressLocal("그냥시", 2, true, null, null,null);
     }
}
