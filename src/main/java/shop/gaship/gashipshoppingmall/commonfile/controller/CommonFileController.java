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

    /**
     * 로컬의 파일 이미지를 다운로드하는 메서드입니다.
     *
     * @param fileNo  다운로드할 파일번호
     * @param request http 요청
     * @return responseEntity 파일 리소스를 포함하고 있습니다.
     */
    @GetMapping("/{fileNo}/download")
    public ResponseEntity<Resource> fileDownload(@PathVariable Integer fileNo,
                                                 HttpServletRequest request) {
        Resource resource = commonFileService.loadResource(fileNo);

        String contentType = "application/octet-stream";
        try {
            contentType = request.getServletContext()
                    .getMimeType(resource.getURL().getPath());
        } catch (IOException ex) {
            log.info("리소스 경로를 가져올 수 없습니다.");
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + resource.getFilename())
                .body(resource);
    }
}
