package com.film.library.service;

import com.film.library.dto.CustomerDTO;
import com.film.library.model.Customer;

public interface CustomerService {
    CustomerDTO save(CustomerDTO customerDTO);

    Customer findByUsername(String username);

    Customer saveInfor(Customer customer);
}
