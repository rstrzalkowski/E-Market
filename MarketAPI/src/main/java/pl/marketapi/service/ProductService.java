package pl.marketapi.service;

import pl.marketapi.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {


    List<Product> getAll();

    Optional<Product> getById(Long id);

    Product add(Product product);

    void deleteById(Long id);
}
