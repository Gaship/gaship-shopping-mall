package shop.gaship.gashipshoppingmall.addresslist.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipshoppingmall.addresslist.dto.request.AddressListAddRequestDto;
import shop.gaship.gashipshoppingmall.addresslist.dto.request.AddressListModifyRequestDto;
import shop.gaship.gashipshoppingmall.addresslist.dummy.AddressListDummy;
import shop.gaship.gashipshoppingmall.addresslist.service.AddressListService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        String body = objectMapper.writeValueAsString(AddressListDummy.addressListAddRequestDtoDummy(1, 1, "경기", "안양", "12345"));
        mockMvc.perform(post("/api/members/1/addressLists")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(addressListService, times(1)).addAddressList(any(AddressListAddRequestDto.class));
    }

    @DisplayName("배송지 목록 수정 테스트")
    @Test
    void addressListModify() throws Exception {
        String body = objectMapper.writeValueAsString(AddressListDummy.addressListModifyRequestDtoDummy(1, 1, 1, "경기", "안양", "12345"));
        mockMvc.perform(put("/api/members/1/addressLists/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(addressListService, times(1)).modifyAndAddAddressList(any(AddressListModifyRequestDto.class));
    }

    @DisplayName("배송지 목록 삭제 테스트")
    @Test
    void addressListRemove() throws Exception {
        mockMvc.perform(delete("/api/members/1/addressLists/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(addressListService, times(1)).removeAddressList(any(Integer.class));
    }

    @DisplayName("배송지 목록 단건조회 테스트")
    @Test
    void addressListDetails() throws Exception {
        when(addressListService.findAddressList(any()))
                .thenReturn(AddressListDummy.addressListResponseDtoDummy(1, "1", true, "경기", "안양", "12334"));

        mockMvc.perform(get("/api/members/1/addressLists/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.addressListNo").value(1));

        verify(addressListService, times(1)).findAddressList(any(Integer.class));
    }

    @DisplayName("배송지 목록 다건조회 테스트")
    @Test
    void addressListList() throws Exception {
        when(addressListService.findAddressLists(any(Integer.class), any(Pageable.class))).thenReturn(AddressListDummy.addressListPageResponseDtoDummy());

        mockMvc.perform(get("/api/members/1/addressLists")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].addressListNo").value(1));

        verify(addressListService, times(1)).findAddressLists(any(), any());
    }
}