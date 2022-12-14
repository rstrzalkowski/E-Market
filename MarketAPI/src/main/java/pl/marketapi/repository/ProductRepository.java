package pl.marketapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.marketapi.domain.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable page);

}
