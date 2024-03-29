package shop.gaship.gashipshoppingmall.commonfile.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shop.gaship.gashipshoppingmall.commonfile.entity.CommonFile;
import shop.gaship.gashipshoppingmall.commonfile.exception.CommonFileNotFoundException;
import shop.gaship.gashipshoppingmall.commonfile.exception.ResourceLoadFailureException;
import shop.gaship.gashipshoppingmall.commonfile.repository.CommonFileRepository;
import shop.gaship.gashipshoppingmall.commonfile.service.CommonFileService;
import shop.gaship.gashipshoppingmall.error.FileUploadFailureException;
import shop.gaship.gashipshoppingmall.file.dto.FileRequestDto;
import shop.gaship.gashipshoppingmall.file.service.FileService;

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
    private final FileService fileService;
    private final ObjectMapper objectMapper;

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
            return new UrlResource(commonFile.getPath());
        } catch (MalformedURLException e) {
            throw new ResourceLoadFailureException();
        }
    }

    /**
     * {@inheritDoc}
     *
     * @throws FileUploadFailureException 파일 업로드가 실패할 시 발생하는 예외입니다.
     */
    @Override
    public FileRequestDto uploadMultipartFile(MultipartFile file) {
        try {
            return objectMapper.readValue(fileService.upload(getUuidName(
                            Objects.requireNonNull(file.getOriginalFilename())),
                            file.getInputStream()),
                    FileRequestDto.class);
        } catch (IOException e) {
            throw new FileUploadFailureException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommonFile createCommonFile(FileRequestDto fileRequestDto) {
        return CommonFile.builder()
                .path(fileRequestDto.getPath())
                .originalName(fileRequestDto.getOriginalName())
                .extension(fileRequestDto.getExtension())
                .build();
    }

    /**
     * UUID 이름을 생성하는 메서드입니다.
     *
     * @param originalFileName 파일의 이름
     * @return string UUID 형태로 변환된 이름
     */
    private String getUuidName(String originalFileName) {
        String fileExtension =
                originalFileName.substring(originalFileName.lastIndexOf('.'));
        return UUID.randomUUID().toString().replace("-", "") + fileExtension;
    }
}
