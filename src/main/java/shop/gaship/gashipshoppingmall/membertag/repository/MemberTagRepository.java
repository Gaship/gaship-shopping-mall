package shop.gaship.gashipshoppingmall.membertag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.membertag.entity.MemberTag;

import java.util.List;

/**
 * @author 최정우
 * @since 1.0
 */
public interface MemberTagRepository extends JpaRepository<MemberTag,Integer> {
    void deleteAllByMember_MemberNo(Integer memberNo);
    List<MemberTag> findAllByMember_MemberNo(Integer memberNo);
}
