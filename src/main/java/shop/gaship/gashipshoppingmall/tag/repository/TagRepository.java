package shop.gaship.gashipshoppingmall.tag.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;

/**
 * 배송지목록의 db 에 접근하기 위한 repository 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
public interface TagRepository extends JpaRepository<Tag, Integer> {
    /**
     * title 이라는 태그명이 있는지 판단하는 메서드입니다.
     *
     * @param title 태그명이 담겨져있습니다.
     * @return title 매개변수와 이미 있는 태그들의 태그명이 일치하는 경우가 있으면 false 아니면 true 를 반환합니다.
     */
    boolean existsByTitle(String title);

    /**
     * 회원이 선택한 태그들을 리스트형태로 가져올때 쓰이는 메서드입니다.
     *
     * @param tagIds 아이디 목록
     * @return 아이디 값에 대응되는 태그리스트
     */
    List<Tag> findByTagNoIn(List<Integer> tagIds);

    @Override
    List<Tag> findAllById(Iterable<Integer> tagIds);

    /**
     * 모든 태그를 가져오는 메서드입니다.
     *
     * @return 아이디 값에 대응되는 태그리스트
     */
    List<Tag> findAll();

}
