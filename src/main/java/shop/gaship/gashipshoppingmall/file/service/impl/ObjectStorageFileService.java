package shop.gaship.gashipshoppingmall.file.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;
import shop.gaship.gashipshoppingmall.config.ObjectStorageConfig;
import shop.gaship.gashipshoppingmall.file.dto.FileRequestDto;
import shop.gaship.gashipshoppingmall.file.service.FileService;
import shop.gaship.gashipshoppingmall.storage.service.StorageAuthService;

/**
 * 오브젝트 스토리지 서비스입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class ObjectStorageFileService implements FileService {
    private final ObjectStorageConfig config;
    private final StorageAuthService storageAuthService;
    private static final String STORAGE_URL = "https://api-storage.cloud.toast.com/v1/";
    private static final String CONTAINER_NAME = "gaship_storage";
    private static final String AUTH_TOKEN_HEADER = "X-Auth-Token";
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    private String getUrl(@NonNull String objectName) {
        return STORAGE_URL + config.getAccount() + "/" + CONTAINER_NAME + "/" + objectName;
    }

    @Override
    public String upload(String objectName, InputStream inputStream) {
        String url = this.getUrl(objectName);

        // InputStream을 요청 본문에 추가할 수 있도록 RequestCallback 오버라이드
        final RequestCallback requestCallback = request -> {
            request.getHeaders().add(AUTH_TOKEN_HEADER, storageAuthService.requestToken());
            IOUtils.copy(inputStream, request.getBody());
        };

        // 오버라이드한 RequestCallback을 사용할 수 있도록 설정
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setBufferRequestBody(false);
        RestTemplate uploadRestTemplate = new RestTemplate(requestFactory);

        HttpMessageConverterExtractor<String> responseExtractor
                = new HttpMessageConverterExtractor<>(
                String.class, uploadRestTemplate.getMessageConverters());

        // API 호출
        uploadRestTemplate.execute(url, HttpMethod.PUT, requestCallback, responseExtractor);

        try {
            return objectMapper.writeValueAsString(parse(url));
        } catch (JsonProcessingException e) {
            throw new JsonParseException(e.getCause());
        }
    }

    @Override
    public InputStream download(String path) {
        // 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTH_TOKEN_HEADER, storageAuthService.requestToken());
        headers.setAccept(List.of(MediaType.APPLICATION_OCTET_STREAM));

        HttpEntity<String> requestHttpEntity = new HttpEntity<>(null, headers);

        // API 호출, 데이터를 바이트 배열로 받음
        ResponseEntity<byte[]> response
                = restTemplate.exchange(path, HttpMethod.GET, requestHttpEntity, byte[].class);

        // 바이트 배열 데이터를 InputStream으로 만들어 반환
        return new ByteArrayInputStream(Objects.requireNonNull(response.getBody()));
    }

    @Override
    public void delete(String path) {
        // 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTH_TOKEN_HEADER, storageAuthService.requestToken());
        HttpEntity<String> requestHttpEntity = new HttpEntity<>(null, headers);

        // API 호출
        restTemplate.exchange(path, HttpMethod.DELETE, requestHttpEntity, String.class);
    }

    private FileRequestDto parse(String url) {
        String originalName = url.substring(url.lastIndexOf("/") + 1);
        return FileRequestDto.builder()
                .path(url)
                .originalName(originalName)
                .extension(originalName.substring(originalName.lastIndexOf(".")) + 1)
                .build();

    }
}
