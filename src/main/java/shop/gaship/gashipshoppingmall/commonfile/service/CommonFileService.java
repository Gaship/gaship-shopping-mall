package shop.gaship.gashipshoppingmall.commonfile.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import shop.gaship.gashipshoppingmall.commonfile.entity.CommonFile;
import shop.gaship.gashipshoppingmall.file.dto.FileRequestDto;

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
     * 멀티파트파일을 업로드하는 메서드입니다.
     *
     * @param file 멀티파트파일
     * @return fileRequestDto 파일 요청 dto
     */
    FileRequestDto uploadMultipartFile(MultipartFile file);

    /**
     * 파일경로를 입력받아 공통파일 엔티티를 생성하는 메서드입니다.
     *
     * @param fileRequestDto 파일 요청 dto
     * @return 공통파일 엔티티
     */
    CommonFile createCommonFile(FileRequestDto fileRequestDto);
}
