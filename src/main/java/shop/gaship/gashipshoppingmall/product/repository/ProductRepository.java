package shop.gaship.gashipshoppingmall.product.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.product.repository.custom.ProductRepositoryCustom;

public interface ProductRepository 
        extends JpaRepository<Product, Integer>, ProductRepositoryCustom {
    List<Product> findAllByCategoryNo(Integer categoryNo);
}
