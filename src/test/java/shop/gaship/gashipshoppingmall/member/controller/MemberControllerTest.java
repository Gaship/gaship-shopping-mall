package shop.gaship.gashipshoppingmall.member.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.service.MemberService;

import static org.junit.jupiter.api.Assertions.*;

/**
 * packageName    : shop.gaship.gashipshoppingmall.member.controller
 * fileName       : MemberControllerTest
 * author         : choijungwoo
 * date           : 2022/07/11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/11        choijungwoo       최초 생성
 */
@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @MockBean
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