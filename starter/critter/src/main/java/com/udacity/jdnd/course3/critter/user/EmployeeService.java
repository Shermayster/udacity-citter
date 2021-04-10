package com.udacity.jdnd.course3.critter.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {
	@Autowired
	EmployeeRepository employeeRepository;

	public Employee save(Employee employee) {
		return employeeRepository.save(employee);
	}

	public List<Employee> getAll() { return employeeRepository.findAll();};

	public Employee get(Long id) {
		return employeeRepository.getOne(id);
	}

	public List<Employee> getEmployeesBySkillsAndDate(DayOfWeek daysAvailable, Set<EmployeeSkill> skills) {
		return employeeRepository.findByDaysAvailable(daysAvailable)
				.stream()
				.filter(user -> user.getSkills().containsAll(skills))
				.collect(Collectors.toList());

	}
}
