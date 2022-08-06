package shop.gaship.gashipshoppingmall.commonfile.service;

import org.springframework.core.io.Resource;

/**
 * 파일 서비스 인터페이스입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
public interface CommonFileService {
    Resource loadResource(Integer fileNo);
}
