package com.sb.product.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sb.product.model.Product;

public interface ProductRepository extends JpaRepository<Product, UUID>{

    Optional<Product> findByName(String name);
    Optional<Product> findByBarcodeAndActiveTrueAndStockGreaterThan(String barcode, Integer stock);

    
}   
