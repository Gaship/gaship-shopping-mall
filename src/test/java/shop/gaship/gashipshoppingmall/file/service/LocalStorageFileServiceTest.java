package shop.gaship.gashipshoppingmall.file.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import shop.gaship.gashipshoppingmall.file.service.impl.LocalFileService;

/**
 * 로컬 스토리지 파일서비스 테스트 입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@SpringBootTest(properties = { "file.service=local-storage" })
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LocalStorageFileServiceTest {
    @Autowired
    FileService fileService;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${file.upload.url}")
    private String uploadBaseUrl;

    File file;
    String path;

    @BeforeEach
    void setUp() {
        file = new File("src/test/resources/sample.jpg");
        path = uploadBaseUrl + File.separator + LocalDate.now() + File.separator + "sample.jpg";
    }

    @DisplayName("로컬 스토리지 파일 업로드 테스트")
    @Order(1)
    @Test
    void upload() throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(file);

        String result = fileService.upload(file.getName(), inputStream);

        assertThat(result).contains(file.getName());
    }

    @DisplayName("로컬 스토리지 파일 다운로드 테스트")
    @Order(2)
    @Test
    void download() throws IOException {
        InputStream inputStream = new FileInputStream(file);

        InputStream result = fileService.download(path);

        assertThat(result.readAllBytes()).isEqualTo(inputStream.readAllBytes());

        inputStream.close();
    }

}
