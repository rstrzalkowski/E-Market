package pl.marketapi.service;

import org.springframework.data.domain.Page;
import pl.marketapi.domain.dto.request.PlaceOrderRequest;
import pl.marketapi.domain.entity.Order;
import pl.marketapi.domain.entity.Product;

import java.util.List;

public interface OrderService {

    List<Order> getAll();

    Order placeOrder(PlaceOrderRequest placeOrderRequest);

    Page<Product> findOrderContent(Long orderId);
}
