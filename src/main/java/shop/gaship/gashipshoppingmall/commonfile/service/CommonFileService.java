package shop.gaship.gashipshoppingmall.commonfile.service;

import org.springframework.core.io.Resource;
import shop.gaship.gashipshoppingmall.commonfile.entity.CommonFile;

/**
 * 파일 서비스 인터페이스입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
public interface CommonFileService {
    /**
     * 해당 파일의 경로로 리소스를 반환하는 메서드입니다.
     *
     * @param fileNo 파일 번호
     * @return 파일 리소스
     */
    Resource loadResource(Integer fileNo);

    /**
     * 파일경로를 입력받아 공통파일 엔티티를 생성하는 메서드입니다.
     *
     * @param fileLink 파일경로
     * @return 공통파일 엔티티
     */
    CommonFile createCommonFile(String fileLink);
}
