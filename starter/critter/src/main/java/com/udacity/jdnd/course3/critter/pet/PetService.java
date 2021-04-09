package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {
	@Autowired PetRepository petRepository;
	@Autowired CustomerRepository customerRepository;

	public Pet save(Pet pet) {
		Pet savedPet =  petRepository.save(pet);
		Customer customer = savedPet.getOwner();
		customer.addPet(savedPet);
		customerRepository.save(customer);
		return savedPet;
	}

	public Pet get(long petId) {
		return petRepository.getOne(petId);
	}

	public List<Pet> findAll() {
		return petRepository.findAll();
	}

	public List<Pet> getPetsByCustomer(Long ownerId) {
		return customerRepository.getOne(ownerId).getPets();
	}

	public List<Pet> getAllByIds(List<Long> ids) {
		return petRepository.findAllById(ids);
	}
}
