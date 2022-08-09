package shop.gaship.gashipshoppingmall.storage.service;

import java.io.IOException;
import java.io.InputStream;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;
import shop.gaship.gashipshoppingmall.config.ObjectStorageConfig;

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

    private String getUrl(@NonNull String containerName, @NonNull String objectName) {
        return STORAGE_URL + config.getAccount() + "/" + containerName + "/" + objectName;
    }

    public void uploadObject(String containerName, String objectName,
                             final InputStream inputStream) {
        String url = this.getUrl(containerName, objectName);

        // InputStream을 요청 본문에 추가할 수 있도록 RequestCallback 오버라이드
        final RequestCallback requestCallback = request -> {
            request.getHeaders().add("X-Auth-Token", storageAuthService.requestToken());
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
    }
}
