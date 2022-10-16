package pl.marketapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.marketapi.domain.entity.OrderProduct;


public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {


}
