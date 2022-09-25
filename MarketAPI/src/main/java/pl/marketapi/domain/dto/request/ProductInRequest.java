package pl.marketapi.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductInRequest {

    private Long productId;
    private int quantity;

}
