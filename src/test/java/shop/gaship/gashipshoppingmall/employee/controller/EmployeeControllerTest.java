package shop.gaship.gashipshoppingmall.employee.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import shop.gaship.gashipshoppingmall.employee.dto.request.CreateEmployeeRequestDto;
import shop.gaship.gashipshoppingmall.employee.dto.request.InstallOrderRequestDto;
import shop.gaship.gashipshoppingmall.employee.dto.request.ModifyEmployeeRequestDto;
import shop.gaship.gashipshoppingmall.employee.dto.response.EmployeeInfoResponseDto;
import shop.gaship.gashipshoppingmall.employee.dto.response.InstallOrderResponseDto;
import shop.gaship.gashipshoppingmall.employee.dummy.CreateEmployeeDtoDummy;
import shop.gaship.gashipshoppingmall.employee.dummy.GetEmployeeDummy;
import shop.gaship.gashipshoppingmall.employee.service.EmployeeService;
import shop.gaship.gashipshoppingmall.error.ExceptionAdviceController;
import shop.gaship.gashipshoppingmall.error.adapter.LogAndCrashAdapter;
import shop.gaship.gashipshoppingmall.util.PageResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
@Import({ExceptionAdviceController.class})
class EmployeeControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    EmployeeService service;

    @MockBean
    LogAndCrashAdapter logAndCrashAdapter;

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

        verify(service, times(1)).addEmployee(any(CreateEmployeeRequestDto.class));
    }

    @DisplayName("직원 생성 유효성 검사 실패 controller post test")
    @Test
    void postEmployeeFail() throws Exception {
        //given
        CreateEmployeeRequestDto dto = new CreateEmployeeRequestDto(1, 1, "이름", null, "1231321", "01010101");

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
        ModifyEmployeeRequestDto dto = new ModifyEmployeeRequestDto(1, "yhc", "yhc", "000000", 1);

        //when & then
        doNothing().when(service).modifyEmployee(dto);

        mvc.perform(put("/api/employees/" + 1)
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
        ModifyEmployeeRequestDto dto = new ModifyEmployeeRequestDto(1, "test", null, "000000", 1);

        //when & then
        doNothing().when(service).modifyEmployee(dto);

        mvc.perform(put("/api/employees/" + 1)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(objectMapper.writeValueAsString(dto))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError())
            .andExpect(jsonPath("$.message").value("이름을 입력해주세요"))
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
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<EmployeeInfoResponseDto> list = new ArrayList<>();
        list.add(e1);
        list.add(e2);

        PageImpl<EmployeeInfoResponseDto> page = new PageImpl<>(list, pageRequest, pageRequest.getPageSize());
        PageResponse<EmployeeInfoResponseDto> response = new PageResponse<>(page);
        //when
        when(service.findEmployees(pageRequest)).thenReturn(response);

        MvcResult mvcResult = mvc.perform(get("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .queryParam("page", objectMapper.writeValueAsString(pageRequest.getPageNumber()))
                .queryParam("size", objectMapper.writeValueAsString(pageRequest.getPageSize())))
            .andExpect(status().is2xxSuccessful())
            .andDo(print())
            .andReturn();

        verify(service, times(1)).findEmployees(pageRequest);

        String result = mvcResult.getResponse().getContentAsString();
        String name1 = JsonPath.parse(result).read("$.content.[0].name");
        String email1 = JsonPath.parse(result).read("$.content.[0].email");
        String phoneNo1 = JsonPath.parse(result).read("$.content.[0].phoneNo");

        String name2 = JsonPath.parse(result).read("$.content.[1].name");
        String email2 = JsonPath.parse(result).read("$.content.[1].email");
        String phoneNo2 = JsonPath.parse(result).read("$.content.[1].phoneNo");


        assertThat(e1.getEmail()).isEqualTo(email1);
        assertThat(e1.getPhoneNo()).isEqualTo(phoneNo1);
        assertThat(e1.getName()).isEqualTo(name1);

        assertThat(e2.getEmail()).isEqualTo(email2);
        assertThat(e2.getPhoneNo()).isEqualTo(phoneNo2);
        assertThat(e2.getName()).isEqualTo(name2);
    }

    @Test
    void employeeInstallOrdersFindTest() throws Exception {

        InstallOrderResponseDto dto = InstallOrderResponseDto.builder()
            .address("경상남도 김해시")
            .addressDetail("ㅇㅇ길 정우빌딩 5층")
            .orderNo(1)
            .memberName("정우")
            .phoneNumber("01011132222")
            .zipCode("12345")
            .build();
        List<InstallOrderResponseDto> orderList = IntStream.range(0, 25)
            .mapToObj(value -> dto)
            .collect(Collectors.toUnmodifiableList());

        given(service.findInstallOrdersFromEmployeeLocation(any(Pageable.class), anyInt()))
            .willReturn(new PageImpl<>(orderList, PageRequest.of(0, 10), 30));

        MvcResult mvcResult = mvc.perform(get("/api/employees/{employeeNo}/orders", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .queryParam("page", "0")
                .queryParam("size", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.totalPage").value(3))
            .andExpect(jsonPath("$.size").value(10))
            .andExpect(jsonPath("$.hasPrev").value(false))
            .andExpect(jsonPath("$.hasNext").value(true))
            .andExpect(jsonPath("$.installOrders").isArray())
            .andExpect(jsonPath("$.installOrders").exists())
            .andExpect(jsonPath("$.installOrders[0].orderNo").value(dto.getOrderNo()))
            .andExpect(jsonPath("$.installOrders[0].address").value(dto.getAddress()))
            .andExpect(jsonPath("$.installOrders[0].addressDetail").value(dto.getAddressDetail()))
            .andExpect(jsonPath("$.installOrders[0].zipCode").value(dto.getZipCode()))
            .andExpect(jsonPath("$.installOrders[0].memberName").value(dto.getMemberName()))
            .andExpect(jsonPath("$.installOrders[0].phoneNumber").value(dto.getPhoneNumber()))
            .andDo(print())
            .andReturn();
    }

    @Test
    void acceptInstallOrderTest() throws Exception {
        InstallOrderRequestDto installOrderRequestDto = new InstallOrderRequestDto();
        ReflectionTestUtils.setField(installOrderRequestDto, "employeeNo", 1);
        ReflectionTestUtils.setField(installOrderRequestDto, "orderNo", 1);

        willDoNothing()
            .given(service)
            .acceptInstallOrder(1, 1);

        mvc.perform(post("/api/employees/{employeeNo}/orders/{orderNo}", 1, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(installOrderRequestDto))
                .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    void completeDeliveryTest() throws Exception {
        InstallOrderRequestDto installOrderRequestDto = new InstallOrderRequestDto();
        ReflectionTestUtils.setField(installOrderRequestDto, "employeeNo", 1);
        ReflectionTestUtils.setField(installOrderRequestDto, "orderNo", 1);

        willDoNothing()
            .given(service)
            .completeDelivery(1, 1);

        mvc.perform(put("/api/employees/{employeeNo}/orders/{orderNo}", 1, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(installOrderRequestDto))
                .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
            .andDo(print());
    }
}
