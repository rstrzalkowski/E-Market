package pl.marketapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pl.marketapi.entity.Product;

public interface ProductService {

    Page<Product> getAll(Pageable page);

    Product getById(Long id);

    Product add(Product product);

    void deleteById(Long id);
}