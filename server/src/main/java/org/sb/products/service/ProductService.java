package org.sb.products.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.sb.core.exceptions.AlreadyExistsException;
import org.sb.products.dtos.CreateProductDTO;
import org.sb.products.model.Product;
import org.sb.products.repository.ProductRepository;

import java.util.List;

@ApplicationScoped
public class ProductService {

    @Inject
    protected ProductRepository productRepository;

    public Product create(CreateProductDTO data) {
        Product existing = productRepository.findByName(data.name());
        if(existing != null) {
            throw new AlreadyExistsException("Nome do produto existente: " + data.name());
        }

        Product product = new Product();
        product.setName(data.name());
        product.setDescription(data.description());
        product.setPrice(data.price());

        return productRepository.save(product);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
