package shop.gaship.gashipshoppingmall.tag.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;

/**
 * packageName    : shop.gaship.gashipshoppingmall.tag.repository
 * fileName       : TagRepository
 * author         : choijungwoo
 * date           : 2022/07/11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/11        choijungwoo       최초 생성
 */
public interface TagRepository extends JpaRepository<Tag,Integer> {
}
