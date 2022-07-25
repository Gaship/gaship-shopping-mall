package shop.gaship.gashipshoppingmall.tag.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * entity가 등록 또는 수정될때 자동으로 컬럼값이 바뀌게 해주는 어노테이션이 붙은 엔티티입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
abstract class BaseEntity {
    @CreatedDate
    @Column(name = "register_datetime", updatable = false)
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modified_datetime")
    private LocalDateTime modifiedDatetime;
}
