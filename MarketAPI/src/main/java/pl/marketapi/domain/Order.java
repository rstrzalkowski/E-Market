package pl.marketapi.domain;


import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Order {

    private Map<Product, Integer> productList = new HashMap<>();
    private User buyer;
    
    public Order(User buyer) {
        this.buyer = buyer;
    }

    public double calculateCost() {
        double sum = 0;

        for (Map.Entry<Product, Integer> entry : productList.entrySet()) {
            sum += entry.getKey().getPrice() * entry.getValue();
        }
        return sum;

    }

    public boolean addToOrder(Product product, int amount) {
        if (product.getQuantity() > amount) {
            productList.put(product, amount);
            return true;
        }
        return false;
    }

}
