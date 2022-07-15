package shop.gaship.gashipshoppingmall.product.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.product.entity.Product;

public interface ProductRepository 
        extends JpaRepository<Product, Integer> {
    List<Product> findAllByCategoryNo(Integer categoryNo);
}
