package pl.marketapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.marketapi.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
