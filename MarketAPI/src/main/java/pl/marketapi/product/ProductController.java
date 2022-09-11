package pl.marketapi.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/products")
    public List<Product> getAll() {
        return productService.getAll();
    }

    @PostMapping("/products/add")
    public Product add(Product product) {
        return productService.add(product);
    }

}
