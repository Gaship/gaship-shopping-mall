package shop.gaship.gashipshoppingmall.employee.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import shop.gaship.gashipshoppingmall.employee.dto.request.CreateEmployeeRequestDto;
import shop.gaship.gashipshoppingmall.employee.dto.request.ModifyEmployeeRequestDto;
import shop.gaship.gashipshoppingmall.employee.dto.response.EmployeeInfoResponseDto;
import shop.gaship.gashipshoppingmall.employee.dummy.CreateEmployeeDtoDummy;
import shop.gaship.gashipshoppingmall.employee.dummy.GetEmployeeDummy;
import shop.gaship.gashipshoppingmall.employee.service.EmployeeService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * packageName    : shop.gaship.gashipshoppingmall.employee.controller
 * fileName       : EmployeeControllerTest author         : 유호철 date           : 2022/07/10 description    :
 * =========================================================== DATE              AUTHOR NOTE
 * ----------------------------------------------------------- 2022/07/10        유호철       최초 생성
 */
@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    EmployeeService service;

    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("직원 생성 controller post test")
    @Test
    void postEmployee() throws Exception {
        //given
        CreateEmployeeRequestDto dto = CreateEmployeeDtoDummy.dummy();

        //when
        doNothing().when(service).addEmployee(dto);

        mvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(dto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());

        verify(service, times(1)).addEmployee(dto);
    }

    @DisplayName("직원 생성 유효성 검사 실패 controller post test")
    @Test
    void postEmployeeFail() throws Exception {
        //given
        CreateEmployeeRequestDto dto = new CreateEmployeeRequestDto(1,1,"이름",null,"1231321","01010101");

        //when
        doNothing().when(service).addEmployee(dto);

        mvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(dto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("이메일을 입력해주세요"))
                .andDo(print());
    }

    @DisplayName("직원 정보 수정 controller test")
    @Test
    void putEmployee() throws Exception {
        //given
        ModifyEmployeeRequestDto dto = new ModifyEmployeeRequestDto(1, "aa", "test@mail.com", "000000");

        //when & then
        doNothing().when(service).modifyEmployee(dto);

        mvc.perform(put("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(dto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
        //then
        verify(service, times(1)).modifyEmployee(any());
    }

    @DisplayName("직원 정보 수정 유효성검사 실패 controller test")
    @Test
    void putEmployeeFail() throws Exception {
        //given
        ModifyEmployeeRequestDto dto = new ModifyEmployeeRequestDto(null, "aa", "test@mail.com", "000000");

        //when & then
        doNothing().when(service).modifyEmployee(dto);

        mvc.perform(put("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(dto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("직원번호를 입력해주세요"))
                .andDo(print());
    }

    @DisplayName("직원 단건 조회 test")
    @Test
    void getEmployee() throws Exception {
        //given
        EmployeeInfoResponseDto e1 = GetEmployeeDummy.dummy();

        //when
        when(service.findEmployee(1)).thenReturn(e1);

        MvcResult mvcResult = mvc.perform(get("/api/employees/" + 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        verify(service, times(1)).findEmployee(any());

        String response = mvcResult.getResponse().getContentAsString();
        String name = JsonPath.parse(response).read("$.name");

        assertThat(e1.getName()).isEqualTo(name);
    }

    @DisplayName("직원 다건 조회 test")
    @Test
    void getEmployees() throws Exception {
        //given
        EmployeeInfoResponseDto e1 = GetEmployeeDummy.dummy();
        EmployeeInfoResponseDto e2 = GetEmployeeDummy.dummy2();

        List<EmployeeInfoResponseDto> list = new ArrayList<>();
        list.add(e1);
        list.add(e2);
        //when
        when(service.findEmployees()).thenReturn(list);

        MvcResult mvcResult = mvc.perform(get("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().is2xxSuccessful())
                .andDo(print())
                .andReturn();

        verify(service, times(1)).findEmployees();

        String response = mvcResult.getResponse().getContentAsString();
        String name1 = JsonPath.parse(response).read("$[0].name");
        String email1 = JsonPath.parse(response).read("$[0].email");
        String phoneNo1 = JsonPath.parse(response).read("$[0].phoneNo");

        String name2 = JsonPath.parse(response).read("$[1].name");
        String email2 = JsonPath.parse(response).read("$[1].email");
        String phoneNo2 = JsonPath.parse(response).read("$[1].phoneNo");


        assertThat(e1.getEmail()).isEqualTo(email1);
        assertThat(e1.getPhoneNo()).isEqualTo(phoneNo1);
        assertThat(e1.getName()).isEqualTo(name1);

        assertThat(e2.getEmail()).isEqualTo(email2);
        assertThat(e2.getPhoneNo()).isEqualTo(phoneNo2);
        assertThat(e2.getName()).isEqualTo(name2);
    }
}
