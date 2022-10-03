package pl.marketapi.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.marketapi.domain.entity.Order;
import pl.marketapi.domain.entity.Product;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderInfo {

    private Order order;
    private List<Product> products;
}
