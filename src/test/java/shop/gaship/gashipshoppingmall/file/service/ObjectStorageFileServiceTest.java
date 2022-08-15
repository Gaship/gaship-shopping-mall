package shop.gaship.gashipshoppingmall.file.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;

/**
 * 오브젝트 스토리지 파일서비스 테스트입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@SpringBootTest(properties = { "file.service=object-storage" })
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ObjectStorageFileServiceTest {
    @Autowired
    FileService fileService;

    @Autowired
    ObjectMapper objectMapper;

    File file;
    String path = "https://api-storage.cloud.toast.com/v1/AUTH_8a2dd42738a0427180466a56561b5eef/gaship_storage/sample.jpg";

    @BeforeEach
    void setUp() {
        file = new File("src/test/resources/sample.jpg");
    }

    @DisplayName("오브젝트 스토리지 파일 업로드 테스트")
    @Order(1)
    @Test
    void upload() throws IOException {
        String objectName = file.getName();
        InputStream inputStream = new FileInputStream(file);

        String result = fileService.upload(objectName, inputStream);

        assertThat(result).contains(path, file.getName());

        inputStream.close();
    }

    @DisplayName("오브젝트 스토리지 파일 다운로드 테스트")
    @Order(2)
    @Test
    void download() throws IOException {
        InputStream inputStream = new FileInputStream(file);

        InputStream result = fileService.download(path);

        assertThat(result.readAllBytes()).isEqualTo(inputStream.readAllBytes());

        inputStream.close();
        result.close();
    }

    @DisplayName("오브젝트 스토리지 파일 삭제 테스트")
    @Order(3)
    @Test
    void delete() {
        fileService.delete(path);

        assertThatThrownBy(() -> fileService.download(path)).isInstanceOf(HttpClientErrorException.class);
    }
}