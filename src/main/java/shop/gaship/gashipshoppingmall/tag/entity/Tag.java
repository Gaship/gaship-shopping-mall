package shop.gaship.gashipshoppingmall.tag.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.gaship.gashipshoppingmall.tag.dto.request.TagModifyRequestDto;

import javax.persistence.*;

/**
 * 태그의 엔티티입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Entity
@Table(name = "tags")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Tag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_no")
    private Integer tagNo;

    @Column(unique = true)
    private String title;

    public void modifyEntity(TagModifyRequestDto tagRequestDto) {
        this.title = tagRequestDto.getTitle();
    }
}
