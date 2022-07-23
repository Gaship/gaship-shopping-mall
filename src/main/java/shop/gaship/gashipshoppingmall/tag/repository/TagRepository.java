package shop.gaship.gashipshoppingmall.tag.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;

/**
 * 배송지목록의 db 에 접근하기 위한 repository 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
public interface TagRepository extends JpaRepository<Tag, Integer>, TagRepositoryCustom {
    /**
     * title 이라는 태그명이 있는지 판단하는 메서드입니다.
     *
     * @param title 태그명이 담겨져있습니다.
     * @return title 매개변수와 이미 있는 태그들의 태그명이 일치하는 경우가 있으면 false 아니면 true 를 반환합니다.
     */
    boolean existsByTitle(String title);

}
