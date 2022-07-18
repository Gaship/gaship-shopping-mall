package shop.gaship.gashipshoppingmall.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

/**
 * 파일을 업로드하기 위한 유틸클래스 입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
public class FileUploadUtil {
    private static final String UPLOAD_BASE_URL = "C:\\Users\\qnt01\\Downloads";

    private FileUploadUtil() {}

    public static String uploadFile(String uploadDir, MultipartFile multipartFile)
            throws IOException {
        Path uploadPath = Paths.get(UPLOAD_BASE_URL + uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = getFileName(multipartFile);
        File destination = new File(uploadPath + File.separator + fileName);
        multipartFile.transferTo(destination);

        return fileName;
    }

    private static String getFileName(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        String fileExtension =  Objects.requireNonNull(originalFilename)
                .substring(originalFilename.lastIndexOf("."));
        return UUID.randomUUID().toString().replace("-", "") + fileExtension;
    }
}
