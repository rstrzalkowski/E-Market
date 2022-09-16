package pl.marketapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.marketapi.entity.Order;
import pl.marketapi.service.OrderService;

import java.util.List;

@RestController
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public List<Order> getAll() {
        return orderService.getAll();
    }

    @PostMapping("/orders/add")
    public Order add(@RequestBody Order order) {
        return orderService.add(order);
    }

}
