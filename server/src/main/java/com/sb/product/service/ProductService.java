package com.sb.product.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sb.core.exceptions.AlreadyExistsException;
import com.sb.core.exceptions.NotFoundException;
import com.sb.product.dto.CreateRequestDTO;
import com.sb.product.model.Product;
import com.sb.product.repository.ProductRepository;
import com.sb.utils.BarcodeUtils;

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
        String barcode = BarcodeUtils.generateEAN13Barcode();
        product.setBarcode(barcode);
        return productRepository.save(product);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(String barcode) {
        return productRepository.findByBarcodeAndActiveTrueAndStockGreaterThan(barcode, 0)
            .orElseThrow(() -> new NotFoundException("Produto não encontrado, inativo ou sem estoque"));
    }
}
