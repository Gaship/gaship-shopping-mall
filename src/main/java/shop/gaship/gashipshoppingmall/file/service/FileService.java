package shop.gaship.gashipshoppingmall.file.service;

import org.springframework.core.io.Resource;

/**
 * 파일 서비스 인터페이스입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
public interface FileService {
    Resource loadResource(Integer fileNo);
}
