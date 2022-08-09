package shop.gaship.gashipshoppingmall.storage.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
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
import org.springframework.web.multipart.MultipartFile;
import shop.gaship.gashipshoppingmall.config.ObjectStorageConfig;
import shop.gaship.gashipshoppingmall.error.FileUploadFailureException;

/**
 * 오브젝트 스토리지 서비스입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class ObjectStorageService {
    private final ObjectStorageConfig config;
    private final StorageAuthService storageAuthService;
    private static final String STORAGE_URL = "https://api-storage.cloud.toast.com/v1/";
    private static final String CONTAINER_NAME = "gaship_storage";
    private static final String AUTH_TOKEN_HEADER = "X-Auth-Token";
    private final RestTemplate restTemplate;

    private String getUrl(@NonNull String objectName) {
        return STORAGE_URL + config.getAccount() + "/" + CONTAINER_NAME + "/" + objectName;
    }

    public String uploadObject(String objectName, final InputStream inputStream) {
        String url = this.getUrl(objectName);

        // InputStream을 요청 본문에 추가할 수 있도록 RequestCallback 오버라이드
        final RequestCallback requestCallback = request -> {
            request.getHeaders().add(AUTH_TOKEN_HEADER, storageAuthService.requestToken());
            IOUtils.copy(inputStream, request.getBody());
        };

        // 오버라이드한 RequestCallback을 사용할 수 있도록 설정
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setBufferRequestBody(false);
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        HttpMessageConverterExtractor<String> responseExtractor
                = new HttpMessageConverterExtractor<>(
                        String.class, restTemplate.getMessageConverters());

        // API 호출
        restTemplate.execute(url, HttpMethod.PUT, requestCallback, responseExtractor);

        return url;
    }

    public String uploadMultipartFile(MultipartFile multipartFile) {
        try {
            return uploadObject(getFileName(multipartFile), multipartFile.getInputStream());
        } catch (IOException e) {
            throw new FileUploadFailureException();
        }
    }

    public InputStream downloadObject(String objectName) {
        String url = getUrl(objectName);

        // 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTH_TOKEN_HEADER, storageAuthService.requestToken());
        headers.setAccept(List.of(MediaType.APPLICATION_OCTET_STREAM));

        HttpEntity<String> requestHttpEntity = new HttpEntity<>(null, headers);

        // API 호출, 데이터를 바이트 배열로 받음
        ResponseEntity<byte[]> response
                = restTemplate.exchange(url, HttpMethod.GET, requestHttpEntity, byte[].class);

        // 바이트 배열 데이터를 InputStream으로 만들어 반환
        return new ByteArrayInputStream(Objects.requireNonNull(response.getBody()));
    }

    public void deleteObject(String objectName) {
        String url = this.getUrl(objectName);

        // 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTH_TOKEN_HEADER, storageAuthService.requestToken());
        HttpEntity<String> requestHttpEntity = new HttpEntity<>(null, headers);

        // API 호출
        restTemplate.exchange(url, HttpMethod.DELETE, requestHttpEntity, String.class);
    }

    /**
     * 해당 파일의 이름을 UUID 값으로 부여하는 메서드입니다.
     *
     * @param multipartFile 파일 이름을 부여받을 MultipartFile입니다.
     */
    private String getFileName(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        String fileExtension =
                Objects.requireNonNull(originalFilename).substring(originalFilename.lastIndexOf("."));
        return UUID.randomUUID().toString().replace("-", "") + fileExtension;
    }
}
