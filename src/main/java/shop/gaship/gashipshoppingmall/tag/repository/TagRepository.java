package shop.gaship.gashipshoppingmall.tag.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;

public interface TagRepository extends JpaRepository<Tag,Integer> {
}
