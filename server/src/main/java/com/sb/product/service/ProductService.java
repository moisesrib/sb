package com.sb.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sb.core.exceptions.AlreadyExistsException;
import com.sb.product.dto.CreateRequestDTO;
import com.sb.product.model.Product;
import com.sb.product.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product create(CreateRequestDTO data) {
        Optional<Product> existingProduct = productRepository.findByName(data.name());
        if (existingProduct.isPresent()) {
            throw new AlreadyExistsException("Nome do produto já existe");
        }

        Product product = new Product();
        product.setName(data.name());
        product.setDescription(data.description());
        product.setPrice(data.price());
        product.setPromotion(data.promotion());
        product.setCost(data.cost());
        product.setStock(data.stock());
        product.setActive(data.active());
        return productRepository.save(product);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
