package shop.gaship.gashipshoppingmall.file.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.json.JsonParseException;
import org.springframework.stereotype.Service;
import shop.gaship.gashipshoppingmall.commonfile.exception.ResourceLoadFailureException;
import shop.gaship.gashipshoppingmall.error.FileDeleteFailureException;
import shop.gaship.gashipshoppingmall.error.FileUploadFailureException;
import shop.gaship.gashipshoppingmall.file.dto.FileRequestDto;
import shop.gaship.gashipshoppingmall.file.service.FileService;

/**
 * 설명작성란
 *
 * @author : 김보민
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(value = "file.service", havingValue = "local-storage", matchIfMissing = true)
public class LocalFileService implements FileService {
    @Value("${file.upload.url}")
    private String uploadBaseUrl;
    private final ObjectMapper objectMapper;
    private static final String PROTOCOL = "file:";

    /**
     * {@inheritDoc}
     *
     * @throws JsonParseException json 파싱이 실패할 시 발생하는 예외입니다.
     */
    @Override
    public String upload(String objectName, InputStream inputStream) {
        String date = File.separator + LocalDate.now();

        Path uploadPath = Paths.get(uploadBaseUrl + date);
        if (!Files.exists(uploadPath)) {
            createUploadPath(uploadPath);
        }

        String url = uploadPath + File.separator + objectName;

        try {
            Files.write(Path.of(url), inputStream.readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            return objectMapper.writeValueAsString(parse(url));
        } catch (JsonProcessingException e) {
            throw new JsonParseException(e.getCause());
        }
    }

    /**
     * {@inheritDoc}
     *
     * @throws ResourceLoadFailureException 파일을 불러오는데 실패했을 때 던질 예외입니다.
     */
    @Override
    public InputStream download(String path) {
        try {
            return new FileInputStream(path.replace(PROTOCOL, ""));
        } catch (IOException e) {
            throw new ResourceLoadFailureException();
        }
    }

    /**
     * {@inheritDoc}
     *
     * @throws FileDeleteFailureException 파일을 삭제하는데 실패했을 때 던질 예외입니다.
     */
    @Override
    public void delete(String path) {
        try {
            Files.deleteIfExists(Paths.get(path.replace(PROTOCOL, "")));
        } catch (IOException e) {
            throw new FileDeleteFailureException();
        }
    }

    /**
     * 업로드 경로의 디렉토리를 생성하는 메서드입니다.
     *
     * @param uploadPath 업로드 경로입니다.
     */
    private void createUploadPath(Path uploadPath) {
        try {
            Files.createDirectories(uploadPath);
        } catch (IOException e) {
            throw new FileUploadFailureException();
        }
    }

    /**
     * url을 FileRequestDto로 파싱합니다.
     *
     * @param url 파일을 업로드한 url
     * @return fileRequestDto 파일 요청 dto
     */
    private FileRequestDto parse(String url) {
        String originalName = url.substring(url.lastIndexOf(File.separator) + 1);
        return FileRequestDto.builder()
                .path(PROTOCOL + url)
                .originalName(originalName)
                .extension(originalName.substring(originalName.lastIndexOf(".") + 1))
                .build();

    }
}
