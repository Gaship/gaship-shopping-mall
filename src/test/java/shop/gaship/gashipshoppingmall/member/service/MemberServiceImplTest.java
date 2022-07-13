package shop.gaship.gashipshoppingmall.member.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.membergrade.repository.MemberGradeRepository;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;

import static org.junit.jupiter.api.Assertions.*;

/**
 * packageName    : shop.gaship.gashipshoppingmall.member.service
 * fileName       : MemberServiceImplTest
 * author         : choijungwoo
 * date           : 2022/07/13
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/13        choijungwoo       최초 생성
 */
@ExtendWith({SpringExtension.class})
@Import(MemberServiceImpl.class)
class MemberServiceImplTest {
    @MockBean
    MemberRepository memberRepository;

    @MockBean
    StatusCodeRepository statusCodeRepository;

    @MockBean
    MemberGradeRepository memberGradeRepository;

    @Autowired
    MemberService memberService;


    @Test
    void register() {
    }

    @Test
    void modify() {
    }

    @Test
    void delete() {
    }

    @Test
    void get() {
    }

    @Test
    void getList() {
    }
}