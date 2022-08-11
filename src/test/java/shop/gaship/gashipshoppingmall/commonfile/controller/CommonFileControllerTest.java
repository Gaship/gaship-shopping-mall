package shop.gaship.gashipshoppingmall.commonfile.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipshoppingmall.commonfile.service.CommonFileService;

/**
 * 공통파일 컨트롤러 테스트입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@WebMvcTest(CommonFileController.class)
class CommonFileControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    CommonFileService commonFileService;

    @DisplayName("파일 다운로드 성공 테스트")
    @Test
    void fileDownloadSuccess() throws Exception {
        Integer fileNo = 1;
        Resource resource = new UrlResource("file:src/test/resources/sample.jpg");

        when(commonFileService.loadResource(fileNo)).thenReturn(resource);

        mockMvc.perform(get("/api/files/{fileNo}/download", fileNo))
                .andExpect(status().isOk())
                .andExpect(content().contentType("image/jpeg"))
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename()))
                .andDo(print());
    }

    @DisplayName("파일 다운로드 실패 테스트")
    @Test
    void fileDownloadFail() throws Exception {
        Integer fileNo = 1;
        Resource resource = new UrlResource("file:src/test/resources/sample.aflkjealsfjalsef");

        when(commonFileService.loadResource(fileNo)).thenReturn(resource);

        mockMvc.perform(get("/api/files/{fileNo}/download", fileNo))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", containsString("Invalid mime type")))
                .andDo(print());
    }
}