package shop.gaship.gashipshoppingmall.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

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

    public List<String> uploadFile(String uploadDir, List<MultipartFile> multipartFiles)
            throws IOException {
        List<String> fileLinks = new ArrayList<>();
        String date = File.separator + LocalDate.now();

        Path uploadPath = Paths.get(uploadBaseUrl + uploadDir + date);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        for (MultipartFile multipartFile : multipartFiles) {
            String fileLink = uploadPath + File.separator + getFileName(multipartFile);
            multipartFile.transferTo(new File(fileLink));
            fileLinks.add(fileLink);
        }

        return fileLinks;
    }

    public void deleteFiles(List<String> fileLinks) {
        fileLinks.stream()
                .map(File::new)
                .filter(File::exists)
                .forEach(File::delete);
    }

    private String getFileName(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        String fileExtension =  Objects.requireNonNull(originalFilename)
                .substring(originalFilename.lastIndexOf("."));
        return UUID.randomUUID().toString().replace("-", "") + fileExtension;
    }
}
