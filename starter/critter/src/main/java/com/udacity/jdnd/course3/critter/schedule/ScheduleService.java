package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
	@Autowired
	private ScheduleRepository scheduleRepository;
	public Schedule save(Schedule schedule){
		return scheduleRepository.save(schedule);
	}

	public List<Schedule> getAll() {
		return scheduleRepository.findAll();
	}

	public Schedule getById(Long id) {
		return scheduleRepository.getOne(id);
	}

	public List<Schedule> getByPet(Pet pet) {
		return scheduleRepository.findAllByPets(pet);
	}

	public List<Schedule> getByEmployee(Employee employee) {
		return scheduleRepository.getByEmployees(employee);
	}
}
