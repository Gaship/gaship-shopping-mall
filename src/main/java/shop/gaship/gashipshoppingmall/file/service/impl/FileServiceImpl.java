package shop.gaship.gashipshoppingmall.file.service.impl;

import java.net.MalformedURLException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import shop.gaship.gashipshoppingmall.file.entity.File;
import shop.gaship.gashipshoppingmall.file.exception.FileNotFoundException;
import shop.gaship.gashipshoppingmall.file.exception.ResourceLoadFailureException;
import shop.gaship.gashipshoppingmall.file.repository.FileRepository;
import shop.gaship.gashipshoppingmall.file.service.FileService;

/**
 * 파일 서비스 구현체입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;

    @Override
    public Resource loadResource(Integer fileNo) {
        File file = fileRepository.findById(fileNo).orElseThrow(FileNotFoundException::new);

        try {
            return new UrlResource("file:" + file.getPath());
        } catch (MalformedURLException e) {
            throw new ResourceLoadFailureException();
        }
    }
}
