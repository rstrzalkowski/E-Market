package pl.marketapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.marketapi.domain.entity.OrderProduct;

import java.util.List;


public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

    List<OrderProduct> findOrderProductByOrder_Id(Long orderId);

}
