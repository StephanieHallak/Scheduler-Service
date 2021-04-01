package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    PetRepository petRepository;
    @Autowired
    CustomerRepository customerRepository;

    public Schedule createSchedule(Schedule schedule, List<Long> employeeIds, List<Long> petIds){
        List<Employee> employees = employeeRepository.findAllById(employeeIds);
        List<Pet> pets = petRepository.findAllById(petIds);
        schedule.setEmployees(employees);
        schedule.setPets(pets);
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules(){
        return scheduleRepository.findAll();
    }

    public List<Schedule> getSchedulesForPet(long petId){
        Pet pet = petRepository.getOne(petId);
        return scheduleRepository.getAllByPetsContains(pet);
    }

    public List<Schedule> getSchedulesForEmployee(long employeeId){
        Employee employee = employeeRepository.getOne(employeeId);
        return scheduleRepository.getAllByEmployeesContains(employee);
    }

    public List<Schedule> getSchedulesForCustomer(long customerId){
        Customer customer = customerRepository.getOne(customerId);
        return scheduleRepository.getAllByPetsIn(customer.getPets());
    }
}
