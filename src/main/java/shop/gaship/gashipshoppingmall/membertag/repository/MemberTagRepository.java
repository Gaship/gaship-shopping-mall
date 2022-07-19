package shop.gaship.gashipshoppingmall.membertag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.membertag.entity.MemberTag;

import java.util.List;

/**
 * memberTag DB에 접근할 수 있는 jpaRepository 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
public interface MemberTagRepository extends JpaRepository<MemberTag,Integer> {
    /**
     * 회원이 기존에 설정한 모든 태그를 지우고 등록하길 원하는 모든 태그를 등록하는 메서드입니다.
     *
     * @param memberNo the member no
     */
    void deleteAllByMember_MemberNo(Integer memberNo);

    /**
     * 회원이 기존에 설정한 모든 태그값을 DB에 접근해 가져오는 메서드입니다.
     *
     * @param memberNo the member no
     * @return the list
     */
    List<MemberTag> findAllByMember_MemberNo(Integer memberNo);
}
