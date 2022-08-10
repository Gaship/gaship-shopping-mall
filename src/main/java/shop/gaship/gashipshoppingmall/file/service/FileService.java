package shop.gaship.gashipshoppingmall.file.service;

import java.io.InputStream;

/**
 * 설명작성란
 *
 * @author : 김보민
 * @since 1.0
 */
public interface FileService {
    String upload(String objectName, InputStream inputStream);

    InputStream download(String path);

    void delete(String path);

}
