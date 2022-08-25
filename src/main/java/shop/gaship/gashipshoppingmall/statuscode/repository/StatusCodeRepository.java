package shop.gaship.gashipshoppingmall.statuscode.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;


/**
 * 상태코드 Repository.
 *
 * @author : 김세미
 * @since 1.0
 */
public interface StatusCodeRepository
        extends JpaRepository<StatusCode, Integer>, StatusCodeRepositoryCustom {
    /**
     * 그룹코드 명을 통해 갱신기간 단건 조회.
     *
     * @param group 그룹코드 명으로 갱신기간을 조회하기 위한 상태 그룹 코드 변수 (String).
     * @return optional StatusCode 를 담는 Optional 반환.
     * @author 김세미
     */
    Optional<StatusCode> findByGroupCodeName(String group);

    /**
     * 상태코드 명을 통해 상태코드 단건 조회.
     *
     * @param statusCodeName 상태코드명으로 상태코드 단건 조회를 하기 위한 상태 코드 변수 (String).
     * @return optional StatusCode 를 담는 Optional 반환.
     * @author 김세미
     */
    Optional<StatusCode> findByStatusCodeName(String statusCodeName);
}
