package shop.gaship.gashipshoppingmall.dataprotection.dto;

import lombok.Getter;

/**
 * packageName    : shop.gaship.gashipshoppingmall.dataprotection.vo <br/>
 * fileName       : SecureKeyResponse <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/10 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/10           김민수               최초 생성                         <br/>
 */
@Getter
public class SecureKeyResponse {
    private Header header;
    private Body body;


    @Getter
    public static class Header {
        private Integer resultCode;
        private String resultMessage;
        private Boolean isSuccessful;
    }

    @Getter
    public static class Body {
        private String secret;
    }
}
