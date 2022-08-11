package shop.gaship.gashipshoppingmall.aspact;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipshoppingmall.employee.controller.EmployeeController;
import shop.gaship.gashipshoppingmall.employee.dto.request.CreateEmployeeRequestDto;
import shop.gaship.gashipshoppingmall.employee.service.EmployeeService;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 설명작성란
 *
 * @author 김민수
 * @since 1.0
 */
@WebMvcTest({EmployeeController.class, AuthorityAspect.class})
class AuthorityAspectTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    EmployeeService employeeService;

    @Test
    void inspectAdminAuthorityAuthorityAdminTest() throws Exception {
        CreateEmployeeRequestDto dto =
            new CreateEmployeeRequestDto(1, 1, "홍길동", "abc@naver.com", "password", "01011112222");

        String body = new ObjectMapper().writeValueAsString(dto);

        doNothing().when(employeeService).addEmployee(dto);

        mockMvc.perform(post("/api/employees").header("X-AUTH-ROLE", "ROLE_ADMIN")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(body))
            .andExpect(status().isCreated())
            .andDo(print());
    }

    @Test
    void inspectAdminAuthorityNoAuthorityTest() throws Exception {
        CreateEmployeeRequestDto dto =
            new CreateEmployeeRequestDto(1, 1, "홍길동", "abc@naver.com", "password", "01011112222");

        String body = new ObjectMapper().writeValueAsString(dto);

        doNothing().when(employeeService).addEmployee(dto);

        mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(body))
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$.message").value("접근권한이 없습니다."))
            .andDo(print());
    }


    @Test
    void inspectAdminAuthorityAuthorityMemberTest() throws Exception {
        CreateEmployeeRequestDto dto =
            new CreateEmployeeRequestDto(1, 1, "홍길동", "abc@naver.com", "password", "01011112222");

        String body = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(post("/api/employees").header("X-AUTH-ROLE", "ROLE_MEMBER")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(body))
            .andExpect(status().is4xxClientError())
            .andDo(print());
    }
}
