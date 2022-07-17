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
    Optional<StatusCode> findByGroupCodeName(String group);
}
