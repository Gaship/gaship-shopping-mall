package shop.gaship.gashipshoppingmall.addresslist.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import shop.gaship.gashipshoppingmall.addresslist.dto.AddressListAddRequestDto;
import shop.gaship.gashipshoppingmall.addresslist.dto.AddressListModifyRequestDto;
import shop.gaship.gashipshoppingmall.addresslist.dummy.AddressListDummy;
import shop.gaship.gashipshoppingmall.addresslist.service.AddressListService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author 최정우
 * @since 1.0
 */

@WebMvcTest(AddressListController.class)
class AddressListControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    AddressListService addressListService;

    @DisplayName("배송지 목록 등록 테스트")
    @Test
    void addressListAdd() throws Exception {
        String body = objectMapper.writeValueAsString(AddressListDummy.addressListAddRequestDtoDummy());
        mockMvc.perform(post("/api/members/1/addressLists")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(addressListService,times(1)).addAddressList(any(AddressListAddRequestDto.class));
    }

    @DisplayName("배송지 목록 수정 테스트")
    @Test
    void addressListModify() throws Exception {
        String body = objectMapper.writeValueAsString(AddressListDummy.addressListModifyRequestDtoDummy());
        mockMvc.perform(put("/api/members/1/addressLists")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(addressListService,times(1)).addAddressList(any(AddressListModifyRequestDto.class));
        verify(addressListService,times(1)).modifyAddressList(any(AddressListModifyRequestDto.class));
    }

    @DisplayName("배송지 목록 삭제 테스트")
    @Test
    void addressListRemove() throws Exception {
        mockMvc.perform(delete("/api/members/1/addressLists/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(addressListService,times(1)).removeAddressList(any(Integer.class));
    }

    @DisplayName("배송지 목록 단건조회 테스트")
    @Test
    void addressListDetails() throws Exception {
        when(addressListService.findAddressList(any()))
                .thenReturn(AddressListDummy.addressListResponseDto1());

        mockMvc.perform(get("/api/members/1/addressLists/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.addressListNo").value(1))
                .andExpect(jsonPath("$.address").value("경기도 안양시 비산동"))
                .andExpect(jsonPath("$.addressDetail").value("현대아파트 65층 창문 앞"))
                .andExpect(jsonPath("$.zipCode").value("12344"));

        verify(addressListService,times(1)).findAddressList(any(Integer.class));
    }

    @DisplayName("배송지 목록 다건조회 테스트")
    @Test
    void addressListList() throws Exception {
        when(addressListService.findAddressLists(any(Integer.class),any(Pageable.class))).thenReturn(AddressListDummy.addressListPageResponseDto());

        mockMvc.perform(get("/api/members/1/addressLists")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dtoList[0].addressListNo").value(1))
                .andExpect(jsonPath("$.dtoList[1].addressListNo").value(2))
                .andExpect(jsonPath("$.totalPage").value(1))
                .andExpect(jsonPath("$.page").value(1))
                .andExpect(jsonPath("$.size").value(10))
                .andExpect(jsonPath("$.start").value(1))
                .andExpect(jsonPath("$.end").value(1))
                .andExpect(jsonPath("$.prev").value(false))
                .andExpect(jsonPath("$.next").value(false))
                .andExpect(jsonPath("$.end").value(1))
                .andExpect(jsonPath("$.pageList").isArray());

        verify(addressListService,times(1)).findAddressLists(any(),any());
    }
}