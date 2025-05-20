package com.sb.sales.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sb.sales.model.Sales;

public interface SalesRepository extends JpaRepository<Sales, UUID> {

}
