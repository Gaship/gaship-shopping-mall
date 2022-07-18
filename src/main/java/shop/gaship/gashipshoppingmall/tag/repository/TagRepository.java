package shop.gaship.gashipshoppingmall.tag.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;

/**
 * tag에 관련된 db 정보를 관리하는 repository입니다.
 *
 * @author 최정우
 * @since 1.0
 */
public interface TagRepository extends JpaRepository<Tag,Integer> {
    /**
     * Exists by title boolean.
     *
     * @param title the title
     * @return the boolean
     */
    boolean existsByTitle(String title);
}
