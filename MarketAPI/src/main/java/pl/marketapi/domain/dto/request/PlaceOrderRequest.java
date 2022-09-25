package pl.marketapi.domain.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
public class PlaceOrderRequest {

    @NotNull
    private List<ProductInRequest> products;

    @NotNull
    private String userEmail;

    @NotNull
    private String shippingAddress;

}
