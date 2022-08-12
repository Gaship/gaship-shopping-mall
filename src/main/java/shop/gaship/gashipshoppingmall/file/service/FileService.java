package shop.gaship.gashipshoppingmall.file.service;

import java.io.InputStream;

/**
 * 파일 서비스 인터페이스 입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
public interface FileService {
    /**
     * 파일을 업로드하는 메서드입니다.
     *
     * @param objectName  파일 이름
     * @param inputStream 데이터를 입력받을 입력 스트림
     * @return string 업로드한 파일의 정보
     */
    String upload(String objectName, InputStream inputStream);

    /**
     * 파일을 다운받는 메서드입니다.
     *
     * @param path 다운받을 파일의 경로
     * @return inputStream 다운받은 파일의 입력스트림
     */
    InputStream download(String path);

    /**
     * 파일을 삭제하는 메서드입니다.
     *
     * @param path 삭제할 파일의 경로
     */
    void delete(String path);

}
