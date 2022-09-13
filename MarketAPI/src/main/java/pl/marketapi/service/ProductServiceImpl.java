package pl.marketapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import pl.marketapi.entity.Product;
import pl.marketapi.exception.ProductNotFoundException;
import pl.marketapi.repository.ProductRepository;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public Product add(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Page<Product> getAll(Pageable page) {
        return productRepository.findAll(page);
    }

    @Override
    public Product getById(Long id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()) {
            return product.get();
        } else {
            throw new ProductNotFoundException("Product with this id doesn't exist");
        }
    }

    @Override
    public void deleteById(Long id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()) {
            productRepository.deleteById(id);
        } else {
            throw new ProductNotFoundException("Product with this id doesn't exist");
        }
    }
}