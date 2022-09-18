package pl.marketapi.service;

import pl.marketapi.domain.entity.Order;

import java.util.List;

public interface OrderService {

    List<Order> getAll();

    Order add(Order order);
}
