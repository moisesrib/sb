package com.sb.product.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sb.core.exceptions.AlreadyExistsException;
import com.sb.core.exceptions.NotFoundException;
import com.sb.product.dto.CreateRequestDTO;
import com.sb.product.dto.ProductReponseDTO;
import com.sb.product.model.Product;
import com.sb.product.repository.ProductRepository;
import com.sb.utils.BarcodeUtils;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductReponseDTO create(CreateRequestDTO data) {
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
        String barcode = BarcodeUtils.generateCode128Barcode();
        product.setBarcode(barcode);
        productRepository.save(product);

        return new ProductReponseDTO(product);
    }

    public List<ProductReponseDTO> findAll() {
        return productRepository.findAll().stream().map(ProductReponseDTO::new).collect(Collectors.toList());
    }

    public ProductReponseDTO findById(String barcode) {
        return productRepository.findFirstByBarcodeAndActiveTrueAndStockGreaterThan(barcode, 0)
            .map(ProductReponseDTO::new)
            .orElseThrow(() -> new NotFoundException("Produto não encontrado, inativo ou sem estoque"));
    }
}
