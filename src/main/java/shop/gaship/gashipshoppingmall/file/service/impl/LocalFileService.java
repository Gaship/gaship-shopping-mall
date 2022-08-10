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
public class LocalFileService implements FileService {
    @Value("${file.upload.url}")
    private String uploadBaseUrl;
    private final ObjectMapper objectMapper;

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

    @Override
    public InputStream download(String path) {
        try {
            return new FileInputStream(path);
        } catch (IOException e) {
            throw new ResourceLoadFailureException();
        }
    }

    @Override
    public void delete(String path) {
        try {
            Files.deleteIfExists(Paths.get(path));
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

    private FileRequestDto parse(String url) {
        String originalName = url.substring(url.lastIndexOf(File.separator) + 1);
        return FileRequestDto.builder()
                .path(url)
                .originalName(originalName)
                .extension(originalName.substring(originalName.lastIndexOf(".")) + 1)
                .build();

    }
}
