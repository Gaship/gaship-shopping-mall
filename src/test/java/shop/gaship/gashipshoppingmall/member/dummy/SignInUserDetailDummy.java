//package shop.gaship.gashipshoppingmall.member.dummy;
//
//import java.util.List;
//import shop.gaship.gashipshoppingmall.member.dto.SignInUserDetailsDto;
//import shop.gaship.gashipshoppingmall.member.entity.Member;
//
///**
// * 설명작성란
// *
// * @author 김민수
// * @since 1.0
// */
//public class SignInUserDetailDummy {
//    private SignInUserDetailDummy() {
//    }
//
//    public static SignInUserDetailsDto dummy() {
//        Member dummyMember = MemberDummy.dummy();
//
//        return SignInUserDetailsDto.builder()
//                .authorities(List.of(dummyMember.getMemberGrades().getName()))
//                .email(dummyMember.getEmail())
//                .identifyNo(dummyMember.getMemberNo())
//                .hashedPassword(dummyMember.getPassword())
//                .build();
//    }
//}
