package shop.gaship.gashipshoppingmall.member.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * entity가 등록 또는 수정될때 자동으로 컬럼값이 바뀌게 해주는 어노테이션이 붙은 엔티티입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@MappedSuperclass
@Getter
@EntityListeners(value = {AuditingEntityListener.class})
public class BaseEntity {
    @CreatedDate
    @Column(name = "register_datetime",updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDateTime modifiedDatetime;
}
