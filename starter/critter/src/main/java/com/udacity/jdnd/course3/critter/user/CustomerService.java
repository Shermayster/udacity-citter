package com.udacity.jdnd.course3.critter.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
	@Autowired
	CustomerRepository customerRepository;
	public Customer save(Customer user) {
		return customerRepository.save(user);
	}

	public Customer get(Long id) {
		return customerRepository.getOne(id);
	}

	public List<Customer> getAll() {
		return customerRepository.findAll();
	}
}
