package shop.gaship.gashipshoppingmall.commonfile.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipshoppingmall.commonfile.service.CommonFileService;

/**
 * 파일 컨트롤러입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@Slf4j
public class CommonFileController {
    private final CommonFileService commonFileService;

    @GetMapping("/download/{fileNo}")
    public ResponseEntity<Resource> fileDownload(@PathVariable Integer fileNo,
                                                 HttpServletRequest request) {
        Resource resource = commonFileService.loadResource(fileNo);

        String contentType = "application/octet-stream";
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("파일 타입을 지정할 수 없습니다.");
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename())
                .body(resource);
    }
}
