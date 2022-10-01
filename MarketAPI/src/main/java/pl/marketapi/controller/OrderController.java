package pl.marketapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public List<Order> getUserOrders(@RequestParam String user, Pageable page) {
        if (user == null) {
            return orderService.getAll();
            // TODO to be changed
        } else {
            return orderService.getByUser(user, page).toList();
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/orders")
    public ResponseEntity placeOrder(@RequestBody PlaceOrderRequest placeOrderRequest) {
        return orderService.placeOrder(placeOrderRequest);
    }


}
