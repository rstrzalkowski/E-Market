package pl.marketapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.marketapi.domain.dto.request.PlaceOrderRequest;
import pl.marketapi.domain.entity.Order;
import pl.marketapi.service.OrderService;

import java.util.List;

@RestController
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/orders")
    public List<Order> getAll() {
        return orderService.getAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/orders/place")
    public Order placeOrder(@RequestBody PlaceOrderRequest placeOrderRequest) {
        return orderService.placeOrder(placeOrderRequest);
    }

}
