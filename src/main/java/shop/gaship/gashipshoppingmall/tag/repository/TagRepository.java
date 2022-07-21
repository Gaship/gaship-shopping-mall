package shop.gaship.gashipshoppingmall.tag.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;

/**
 * tag에 관련된 db 정보를 관리하는 repository입니다.
 *
 * @author 최정우
 * @since 1.0
 */
public interface TagRepository extends JpaRepository<Tag, Integer> {
    /**
     * Exists by title boolean.
     *
     * @param title the title
     * @return the boolean
     */
    boolean existsByTitle(String title);

    /**
     * 회원이 선택한 태그들을 리스트형태로 가져올때 쓰이는 메서드
     *
     * @param tagIds the title
     * @return the List<Tag>
     */
    List<Tag> findByTagNoIn(List<Integer> tagIds);
}
