package pl.marketapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import pl.marketapi.repository.OrderProductRepository;
import pl.marketapi.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
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
        }

        Order order = new Order(user, shippingAddress);

        Order savedOrder = orderRepository.save(order);

        orderContent.forEach((productInRequest -> {
            Product product = productService.getById(productInRequest.getProductId());

            if (productInRequest.getQuantity() > product.getAmount()) {
                throw new RuntimeException("Error creating order");
            }
            OrderProduct orderProduct = new OrderProduct(
                    null,
                    product,
                    savedOrder,
                    productInRequest.getQuantity());

            product.reduceAvailableAmount(productInRequest.getQuantity());

            productService.add(product);
            orderProductRepository.save(orderProduct);
        }));

        //findOrderContent(order.getId());
        return savedOrder;
    }

    @Override
    public Page<Product> findOrderContent(Long orderId) {

        List<Product> content = new ArrayList<>();

        List<OrderProduct> orderProductList = orderProductRepository.findOrderProductByOrder_Id(orderId);


        orderProductList.forEach((orderProduct -> {
            productService.getById(orderProduct.getId());
        }));

        return null;
    }

}
