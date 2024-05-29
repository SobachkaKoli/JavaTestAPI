package com.example.javatest.service.Impl;

import com.example.javatest.model.Customer;
import com.example.javatest.model.DTO.CustomerDTO;
import com.example.javatest.model.DTO.CustomerResponseDTO;
import com.example.javatest.repo.CustomerRepository;
import com.example.javatest.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public CustomerResponseDTO createCustomer(CustomerDTO customerDTO) {

        validateCustomer(customerDTO);

        Customer customer = customerRepository.save(Customer.builder()
                .fullName(customerDTO.getFullName())
                .email(customerDTO.getEmail())
                .phone(customerDTO.getPhone())
                .isActive(true)
                .build());

        return CustomerResponseDTO.builder()
                .id(customer.getId())
                .fullName(customer.getFullName())
                .email(customer.getEmail())
                .phone(customer.getPhone()).build();

    }

    @Override
    public List<CustomerResponseDTO> getAllCustomers() {

        List<CustomerResponseDTO> listOfCustomers = new ArrayList<>();

        for (Customer customer : customerRepository.findAll()) {
            listOfCustomers.add(CustomerResponseDTO.builder()
                    .id(customer.getId())
                    .fullName(customer.getFullName())
                    .email(customer.getEmail())
                    .phone(customer.getPhone()).build());
        }
        return listOfCustomers;

    }

    @Override
    public CustomerResponseDTO getCustomerById(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        return CustomerResponseDTO.builder()
                .id(customer.getId())
                .fullName(customer.getFullName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .build();
    }

    @Override
    public CustomerResponseDTO updateCustomer(CustomerDTO customerDTO, Long customerId) {

        validateCustomer(customerDTO);

        Customer customer = customerRepository.findById(customerId).orElseThrow();

        if (customerDTO.getFullName() != null) {
            customer.setFullName(customerDTO.getFullName());
        }
        if (customerDTO.getPhone() != null) {
            customer.setPhone(customerDTO.getPhone());
        }
        customerRepository.save(customer);

        return CustomerResponseDTO.builder()
                .id(customerId)
                .fullName(customerDTO.getFullName())
                .email(customer.getEmail())
                .phone(customer.getPhone()).build();

    }

    @Override
    public void deleteCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        customer.setIsActive(false);
        customerRepository.save(customer);
    }


    private void validateCustomer(CustomerDTO customerDTO) throws ResponseStatusException {
        if (customerDTO.getEmail() == null || !isValidEmail(customerDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required and must be a valid email address.");
        }

        if (customerDTO.getFullName() == null || customerDTO.getFullName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is required.");
        }

        if (customerDTO.getPhone() != null && !customerDTO.getPhone().isEmpty()) {
            if (!isValidatePhone(customerDTO.getPhone())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid phone number. It should start with '+', followed by 6 to 14 digits.");
            }
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[\\w!#$%&'*+/=?^`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?$";
        return email != null && email.matches(emailRegex);
    }

    private boolean isValidatePhone(String phone) {
        return phone != null && phone.matches("\\+\\d{6,14}");
    }


}
