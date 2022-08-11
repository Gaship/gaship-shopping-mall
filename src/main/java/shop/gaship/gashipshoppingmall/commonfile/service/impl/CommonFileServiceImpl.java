package shop.gaship.gashipshoppingmall.commonfile.service.impl;

import java.io.File;
import java.net.MalformedURLException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import shop.gaship.gashipshoppingmall.commonfile.entity.CommonFile;
import shop.gaship.gashipshoppingmall.commonfile.exception.CommonFileNotFoundException;
import shop.gaship.gashipshoppingmall.commonfile.exception.ResourceLoadFailureException;
import shop.gaship.gashipshoppingmall.commonfile.repository.CommonFileRepository;
import shop.gaship.gashipshoppingmall.commonfile.service.CommonFileService;

/**
 * 파일 서비스 구현체입니다.
 *
 * @see CommonFileService
 * @author : 김보민
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CommonFileServiceImpl implements CommonFileService {
    private final CommonFileRepository commonFileRepository;

    /**
     * {@inheritDoc}
     *
     * @throws ResourceLoadFailureException 리소스의 프로토콜이 올바르지않으면 발생하는 예외입니다.
     */
    @Override
    public Resource loadResource(Integer fileNo) {
        CommonFile commonFile = commonFileRepository.findById(fileNo)
                .orElseThrow(CommonFileNotFoundException::new);

        try {
            return new UrlResource("file:" + commonFile.getPath());
        } catch (MalformedURLException e) {
            throw new ResourceLoadFailureException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommonFile createCommonFile(String fileLink) {
        String fileName = fileLink.substring(fileLink.lastIndexOf(File.separator) + 1);
        return CommonFile.builder()
                .path(fileLink)
                .originalName(fileName)
                .extension(fileName.substring(fileName.lastIndexOf(".") + 1))
                .build();
    }
}
