package pl.marketapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.marketapi.domain.dto.request.PlaceOrderRequest;
import pl.marketapi.domain.dto.request.ProductInRequest;
import pl.marketapi.domain.entity.Order;
import pl.marketapi.domain.entity.OrderProduct;
import pl.marketapi.domain.entity.Product;
import pl.marketapi.domain.entity.User;
import pl.marketapi.repository.OrderRepository;

import java.util.List;
import java.util.Objects;

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
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Order placeOrder(PlaceOrderRequest placeOrderRequest) {

        String userEmail = placeOrderRequest.getUserEmail();
        List<ProductInRequest> orderContent = placeOrderRequest.getProducts();
        String shippingAddress = placeOrderRequest.getShippingAddress();

        User user = userService.getByEmail(userEmail);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!Objects.equals(userEmail, authentication.getName())) {
            throw new RuntimeException("Not authorized");
            //TODO custom exc
        }
        Order order = new Order(user, shippingAddress);
        order = orderRepository.save(order);
        Order finalOrder = order;
        orderContent.forEach((productInRequest -> {
            Product product = productService.getById(productInRequest.getProductId());
            OrderProduct orderProduct = new OrderProduct(
                    product,
                    finalOrder,
                    productInRequest.getQuantity());

            if (productInRequest.getQuantity() > product.getAmount()) {
                throw new RuntimeException("Error creating order");
                //TODO custom exc
            }
            product.reduceAvailableAmount(productInRequest.getQuantity());
            Product savedProduct = productService.save(product);
            finalOrder.addProduct(orderProduct);

        }));

        return orderRepository.save(finalOrder);
    }
}
