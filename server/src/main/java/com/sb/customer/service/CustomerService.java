package com.sb.customer.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sb.core.exceptions.AlreadyExistsException;
import com.sb.core.exceptions.NotFoundException;
import com.sb.customer.dto.CreateCustomerDTO;
import com.sb.customer.dto.ResponseCustomerDTO;
import com.sb.customer.model.Customer;
import com.sb.customer.repository.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public ResponseCustomerDTO create(CreateCustomerDTO data) {

        customerRepository.findByDocumentOrPhone(data.document(), data.phone()).ifPresent(customer -> {
            throw new AlreadyExistsException("Cliente já cadastrado");
        });

        Customer customer = new Customer();
        customer.setName(data.name());
        customer.setEmail(data.email());
        customer.setPhone(data.phone());
        customer.setAddress(data.address());
        customer.setDocument(data.document());
        customer.setObservation(data.observation());
        
        Customer newCustomer = customerRepository.save(customer);
        return new ResponseCustomerDTO(newCustomer);
    }

    public List<ResponseCustomerDTO> findAll() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(ResponseCustomerDTO::new).collect(Collectors.toList());
    }

    public ResponseCustomerDTO findById(UUID id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
        return new ResponseCustomerDTO(customer);
    }

}
