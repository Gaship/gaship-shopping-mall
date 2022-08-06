package shop.gaship.gashipshoppingmall.producttag.repository.custom;

import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;


/**
 * QueryDsl 을쓰기위한 인터페이스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
@NoRepositoryBean
public interface ProductTagRepositoryCustom {

    /**
     * 기입된 제품번호로 tag 를 찾는 메서드입니다.
     *
     * @param productNo 제품번호가 기입됩니다.
     * @return list 태그의 값이 반환됩니다.
     * @author 유호철
     */
    List<String> findTagsByProductNo(Integer productNo);

}
