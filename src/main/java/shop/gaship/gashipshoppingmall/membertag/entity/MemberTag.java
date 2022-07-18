package shop.gaship.gashipshoppingmall.membertag.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;

import javax.persistence.*;

/**
 * @author 최정우
 * @since 1.0
 */
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "member_tags")
public class MemberTag {
    @ManyToOne
    @JoinColumn(name = "member_No")
    @Id
    private Member member;

    @ManyToOne
    @JoinColumn(name = "tag_No")
    @Id
    private Tag tag;
}
