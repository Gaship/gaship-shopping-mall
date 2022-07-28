package shop.gaship.gashipshoppingmall.inquiry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.inquiry.entity.Inquiry;

/**
 * jpa를 이용하여 db에 값을 조회하고 변경하는 등의 작업을 실시하는 repository입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
public interface InquiryRepository extends JpaRepository<Inquiry, Integer> {

}
