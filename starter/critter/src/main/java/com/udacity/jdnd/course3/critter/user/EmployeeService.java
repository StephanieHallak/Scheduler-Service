package com.udacity.jdnd.course3.critter.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(long employeeId){
        return employeeRepository.getOne(employeeId);
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId){
        Employee employee = employeeRepository.getOne(employeeId);
        employee.setDaysAvailable(daysAvailable);
        employeeRepository.save(employee);
    }

    public List<Employee> findEmployeesForService(Set<EmployeeSkill> employeeSkills, LocalDate date){
        //GET THE AVAILABLE EMPLOYEES ON THE DATE
        List<Employee> employeesAvailable = employeeRepository.getAllByDaysAvailableContains(date.getDayOfWeek());
        for (Employee e:employeesAvailable){
            System.out.println(e.getName());
        }
        //FILTER THE 'employeeAvailable' TO MATCH THE SKILLS NEEDED
        List<Employee> employeesAvailableAndSkills = employeesAvailable.stream().filter(employee -> employee.getSkills().containsAll(employeeSkills)).collect(Collectors.toList());
        //System.out.println(employeesAvailable);

        return employeesAvailableAndSkills;
    }
}
