package shop.gaship.gashipshoppingmall.member.controller;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipshoppingmall.member.dummy.MemberCreationRequestDummy;
import shop.gaship.gashipshoppingmall.member.service.MemberService;

/**
 * packageName    : shop.gaship.gashipshoppingmall.member.controller <br/>
 * fileName       : MemberControllerTest <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/12 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/12           김민수               최초 생성                         <br/>
 */
@WebMvcTest(MemberController.class)
class MemberControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberService memberService;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void signUpMemberTest() throws Exception {
        String contentBody = objectMapper.registerModule(new JavaTimeModule())
            .writeValueAsString(MemberCreationRequestDummy.dummy());

        doNothing().when(memberService)
            .registerMember(MemberCreationRequestDummy.dummy());

        mockMvc.perform(post("/members/signUp")
            .contentType(MediaType.APPLICATION_JSON)
            .content(contentBody))
            .andDo(print())
            .andExpect(status().isCreated());
    }
}
