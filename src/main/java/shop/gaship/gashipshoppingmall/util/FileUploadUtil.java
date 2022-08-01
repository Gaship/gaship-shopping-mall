package shop.gaship.gashipshoppingmall.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import shop.gaship.gashipshoppingmall.error.FileDeleteException;
import shop.gaship.gashipshoppingmall.error.FileUploadException;

/**
 * 파일을 업로드하기 위한 유틸클래스 입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@Component
public class FileUploadUtil {

    @Value("${file.upload.url}")
    private String uploadBaseUrl;

    /**
     * 파일을 서버에 업로드하는 메서드입니다.
     *
     * @param uploadDir      업로드 할 디렉터리입니다.
     * @param multipartFiles 업로드할 파일 자료구조의 집합입니다.
     * @return 저장된 파일 path들의 집합입니다.
     * @throws FileUploadException 파일 저장에 오류가 발생하였을 때 에외를 던집니다.
     */
    public List<String> uploadFile(String uploadDir, List<MultipartFile> multipartFiles) {
        String date = File.separator + LocalDate.now();

        Path uploadPath = Paths.get(uploadBaseUrl + uploadDir + date);
        if (!Files.exists(uploadPath)) {
            createUploadPath(uploadPath);
        }

        return multipartFiles.stream()
                .map(multipartFile -> {
                    String fileLink = uploadPath + File.separator + getFileName(multipartFile);
                    transferFile(fileLink, multipartFile);
                    return fileLink;
                })
                .collect(Collectors.toList());
    }

    /**
     * String으로 전달받은 경로들의 파일을 모두 삭제하는 메서드입니다.
     *
     * @param fileLinks 삭제할 파일의 경로들입니다.
     * @throws FileDeleteException 파일 삭제에 오류가 발생하였을 때 에외를 던집니다.
     */
    public void cleanUpFiles(List<String> fileLinks) {
        fileLinks.stream()
                .map(Paths::get)
                .forEach(this::deleteFile);
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
            throw new FileUploadException();
        }
    }

    /**
     * MultipartFile을 업로드하는 메서드입니다.
     *
     * @param fileLink      업로드할 파일의 링크입니다.
     * @param multipartFile 업로드할 MultipartFile입니다.
     */
    private void transferFile(String fileLink, MultipartFile multipartFile) {
        try {
            multipartFile.transferTo(new File(fileLink));
        } catch (IOException e) {
            throw new FileUploadException();
        }
    }

    /**
     * 해당 경로의 파일을 삭제하는 메서드입니다.
     *
     * @param path 삭제할 파일의 경로입니다.
     */
    private void deleteFile(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new FileDeleteException();
        }
    }

    /**
     * 해당 파일의 이름을 UUID 값으로 부여하는 메서드입니다.
     *
     * @param multipartFile 파일 이름을 부여받을 MultipartFile입니다.
     */
    private String getFileName(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        String fileExtension =
            Objects.requireNonNull(originalFilename).substring(originalFilename.lastIndexOf("."));
        return UUID.randomUUID().toString().replace("-", "") + fileExtension;
    }
}
