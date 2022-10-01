package pl.marketapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import pl.marketapi.domain.dto.request.PlaceOrderRequest;
import pl.marketapi.domain.entity.Order;

import java.util.List;

public interface OrderService {

    List<Order> getAll();

    Page<Order> getByUser(String email, Pageable page);

    ResponseEntity placeOrder(PlaceOrderRequest placeOrderRequest);


}
