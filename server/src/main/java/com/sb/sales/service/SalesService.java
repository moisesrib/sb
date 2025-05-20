package com.sb.sales.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.sb.core.exceptions.NotFoundException;
import com.sb.customer.model.Customer;
import com.sb.customer.repository.CustomerRepository;
import com.sb.product.model.Product;
import com.sb.product.repository.ProductRepository;
import com.sb.sales.dto.CreateSalesRequestDTO;
import com.sb.sales.dto.SalesItemRequestDTO;
import com.sb.sales.dto.ResponseSalesDTO;
import com.sb.sales.enums.SalesStatusEnum;
import com.sb.sales.model.Sales;
import com.sb.sales.model.SalesItem;
import com.sb.sales.repository.SalesRepository;
import com.sb.user.model.User;
import com.sb.user.repository.UserRepository;

@Service
public class SalesService {

    @Autowired
    private SalesRepository salesRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseSalesDTO create(CreateSalesRequestDTO dto) {

        String email = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));

        Sales sale = new Sales();
        sale.setCustomer(customer);
        sale.setUser(user);
        sale.setDate(LocalDateTime.now());
        sale.setDiscount(dto.getDiscount());
        sale.setObservation(dto.getObservation());
        sale.setPaymentMethod(dto.getPaymentMethod());
        sale.setStatus(SalesStatusEnum.valueOf(dto.getStatus()));
        sale.setDeletedAt(null);

        Set<SalesItem> items = new HashSet<>();
        BigDecimal total = BigDecimal.ZERO;

        for (SalesItemRequestDTO itemDTO : dto.getItems()) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new NotFoundException("Produto não encontrado: " + itemDTO.getProductId()));

            SalesItem item = new SalesItem();
            item.setProduct(product);
            item.setQuantity(itemDTO.getQuantity());
            item.setUnitPrice(itemDTO.getUnitPrice());
            item.setStatus(SalesStatusEnum.valueOf(itemDTO.getStatus()));
            item.setSale(sale);
            item.setDeletedAt(null);
            item.calculateSubtotal();

            total = total.add(item.getSubtotal());

            items.add(item);
        }

        sale.setTotal(total.subtract(dto.getDiscount() != null ? dto.getDiscount() : BigDecimal.ZERO));
        sale.setItems(items);

        salesRepository.save(sale);

        return new ResponseSalesDTO(sale);
    }
}
