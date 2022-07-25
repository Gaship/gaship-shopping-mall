package shop.gaship.gashipshoppingmall.membertag.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;

/**
 * @author 최정우
 * @description 멤버태그 테이블에 객체를 담을 엔티티입니다.
 * @since 1.0
 */
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "member_tags")
public class MemberTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberTagNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_no")
    private Tag tag;
}
