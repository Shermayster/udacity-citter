package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired EmployeeService employeeService;
    @Autowired PetService petService;
    @Autowired ScheduleService scheduleService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        return toDTO(scheduleService.save(fromDTO(scheduleDTO)));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.getAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        Pet pet = petService.get(petId);
        return scheduleService.getByPet(pet)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.get(employeeId);
        return scheduleService.getByEmployee(employee)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Long> petIdList = petService.getPetsByCustomer(customerId)
                .stream().map(Pet::getId).collect(Collectors.toList());
        return petIdList.stream()
                .map(petService::get)
                .flatMap(pet -> scheduleService
                        .getByPet(pet).stream().map(this::toDTO)).collect(Collectors.toList());

    }

    private Schedule fromDTO(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule, "employeeIds", "petIds");
        for(Long petId:scheduleDTO.getPetIds()) {
            schedule.addPet(petService.get(petId));
        }
        for(Long employeeId : scheduleDTO.getEmployeeIds()) {
            schedule.addEmployee(employeeService.get(employeeId));
        }

        return schedule;
    }

    private ScheduleDTO toDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        List<Long> petIdList = new ArrayList<>();
        List<Long> employeeIdList = new ArrayList<>();
        BeanUtils.copyProperties(schedule, scheduleDTO, "employeeIds", "petIds");
        for(Pet pet:schedule.getPets()) {
            petIdList.add(pet.getId());
        }
        scheduleDTO.setPetIds(petIdList);
        for(Employee employee : schedule.getEmployees()) {
            employeeIdList.add(employee.getId());
        }
        scheduleDTO.setEmployeeIds(employeeIdList);
        return scheduleDTO;
    }
}
