package shop.gaship.gashipshoppingmall.commonfile.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipshoppingmall.commonfile.entity.CommonFile;
import shop.gaship.gashipshoppingmall.commonfile.repository.CommonFileRepository;
import shop.gaship.gashipshoppingmall.commonfile.service.impl.CommonFileServiceImpl;
import shop.gaship.gashipshoppingmall.error.FileUploadFailureException;
import shop.gaship.gashipshoppingmall.file.dto.FileRequestDto;
import shop.gaship.gashipshoppingmall.file.service.FileService;

/**
 * 설명작성란
 *
 * @author : 김보민
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(CommonFileServiceImpl.class)
class CommonFileServiceTest {
    @Autowired
    CommonFileService commonFileService;

    @MockBean
    CommonFileRepository commonFileRepository;

    @MockBean
    FileService fileService;

    @MockBean
    ObjectMapper objectMapper;

    CommonFile commonFile;
    FileRequestDto fileRequest;

    File file;
    MockMultipartFile multipartFile;

    @BeforeEach
    void setUp() throws IOException {
        file = new File("src/test/resources/sample.jpg");
        multipartFile = new MockMultipartFile(
                "image", "sample.jpg", "multipart/mixed", new FileInputStream(file));

        fileRequest = FileRequestDto.builder()
                .path("file:" + file.getAbsolutePath())
                .originalName(multipartFile.getOriginalFilename())
                .extension("jpg")
                .build();

        commonFile = CommonFile.builder()
                .path(fileRequest.getPath())
                .originalName(fileRequest.getOriginalName())
                .extension(fileRequest.getExtension())
                .build();
    }

    @DisplayName("리소스 로드 테스트")
    @Test
    void loadResource() throws IOException {
        Integer fileNo = 1;
        Resource resource = new UrlResource("file:" + file.getAbsolutePath());

        when(commonFileRepository.findById(fileNo)).thenReturn(Optional.of(commonFile));

        Resource result = commonFileService.loadResource(fileNo);
        assertThat(result.getFilename()).isEqualTo(resource.getFilename());

        verify(commonFileRepository).findById(fileNo);
    }

    @DisplayName("멀티파트파일 업로드 테스트")
    @Test
    void uploadMultipartFile() throws JsonProcessingException {
        when(fileService.upload(any(), any())).thenReturn("fileRequestDto");
        when(objectMapper.readValue((String) any(), (Class<Object>) any())).thenReturn(fileRequest);

        FileRequestDto result = commonFileService.uploadMultipartFile(multipartFile);
        assertThat(result.getPath()).isEqualTo(fileRequest.getPath());
        assertThat(result.getOriginalName()).isEqualTo(fileRequest.getOriginalName());
        assertThat(result.getExtension()).isEqualTo(fileRequest.getExtension());

        verify(fileService).upload(any(), any());
        verify(objectMapper).readValue((String) any(), (Class<Object>) any());
    }

    @DisplayName("공통파일 엔티티 생성 테스트")
    @Test
    void createCommonFile() {
        CommonFile result = commonFileService.createCommonFile(fileRequest);
        assertThat(result.getPath()).isEqualTo(fileRequest.getPath());
        assertThat(result.getOriginalName()).isEqualTo(fileRequest.getOriginalName());
        assertThat(result.getExtension()).isEqualTo(fileRequest.getExtension());
    }
}