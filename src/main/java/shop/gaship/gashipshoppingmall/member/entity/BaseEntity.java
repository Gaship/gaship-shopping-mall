package shop.gaship.gashipshoppingmall.member.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * packageName    : shop.gaship.gashipshoppingmall.member.entity
 * fileName       : BaseEntity
 * author         : choijungwoo
 * date           : 2022/07/13
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/13        choijungwoo       최초 생성
 */
@MappedSuperclass
@Getter
@EntityListeners(value = {AuditingEntityListener.class})
public class BaseEntity {
    @CreatedDate
    @Column(name = "register_datetime",updatable=false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modified_datetime")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime modifiedDatetime;
}
