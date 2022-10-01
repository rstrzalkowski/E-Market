package pl.marketapi.domain.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import pl.marketapi.domain.entity.OrderProduct;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
public class PlaceOrderRequest {

    @NotNull
    private Set<OrderProduct> products;

    @NotNull
    private String userEmail;

    @NotNull
    private String shippingAddress;

}
