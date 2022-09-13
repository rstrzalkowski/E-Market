package pl.marketapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.marketapi.entity.Product;
import pl.marketapi.service.ProductService;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;


    @GetMapping("/products")
    public List<Product> getAll(Pageable page) {
        return productService.getAll(page).toList();
    }

    @GetMapping("/products/{id}")
    public Product getById(@PathVariable Long id) {
        return productService.getById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/products/add")
    public Product add(@RequestBody Product product) {
        return productService.add(product);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/products/{id}")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

}