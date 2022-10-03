package pl.marketapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.marketapi.domain.dto.request.PlaceOrderRequest;
import pl.marketapi.domain.entity.Order;
import pl.marketapi.domain.entity.OrderProduct;
import pl.marketapi.domain.entity.Product;
import pl.marketapi.domain.entity.User;
import pl.marketapi.repository.OrderRepository;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

//    @Autowired
//    private OrderProductRepository orderProductRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public Page<Order> getByUser(String userEmail, Pageable page) {
        String authenticatedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!Objects.equals(userEmail, authenticatedUserEmail)) {
            throw new RuntimeException("User can see only his own orders");
            //TODO custom exc
        }
        User user = userService.getByEmail(userEmail);
        return orderRepository.findByUser(user, page);
    }

    @Override
    @Transactional
    public ResponseEntity<Order> placeOrder(PlaceOrderRequest placeOrderRequest) {

        String userEmail = placeOrderRequest.getUserEmail();
        Set<OrderProduct> orderProducts = placeOrderRequest.getProducts();
        String shippingAddress = placeOrderRequest.getShippingAddress();

        User user = userService.getByEmail(userEmail);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!Objects.equals(userEmail, authentication.getName())) {
            throw new RuntimeException("Not authorized");
            //TODO custom exc
        }
        Order order = new Order(user, shippingAddress);
        orderProducts.forEach(order::addProduct);
        orderRepository.save(order);
        orderProducts.forEach((orderProduct -> {
            Product product = productService.getById(orderProduct.getProductId());

            if (orderProduct.getQuantity() > product.getAmount()) {
                throw new RuntimeException("Error creating order");
                //TODO custom exc
            }
            product.reduceAvailableAmount(orderProduct.getQuantity());
            productService.save(product);
        }));

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
}
