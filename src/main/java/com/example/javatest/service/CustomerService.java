package com.example.javatest.service;

import com.example.javatest.model.DTO.CustomerDTO;
import com.example.javatest.model.DTO.CustomerResponseDTO;

import java.util.List;

public interface CustomerService {

    CustomerResponseDTO createCustomer(CustomerDTO customerDTO);

    List<CustomerResponseDTO> getAllCustomers();

    CustomerResponseDTO getCustomerById(Long customerId);

    CustomerResponseDTO updateCustomer(CustomerDTO customerDTO, Long customerId);

    void deleteCustomer(Long customerId);

}
