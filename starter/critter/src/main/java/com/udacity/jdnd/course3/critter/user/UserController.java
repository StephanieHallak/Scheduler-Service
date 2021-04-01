package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    CustomerService customerService;
    @Autowired
    EmployeeService employeeService;

    public CustomerDTO convertCustomerToCustomerDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        customerDTO.setPetIds(customer.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
        return customerDTO;
    }
    public Customer convertCustomerDTOToCustomer(CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        customer.setPets(new ArrayList<>());
        return customer;
    }
    public EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        employeeDTO.setDaysAvailable(employee.getDaysAvailable());
        employeeDTO.setSkills(employee.getSkills());
        return employeeDTO;
    }
    public Employee convertEmployeeDTOToEmployee(EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer savedCustomer = customerService.saveCustomer(convertCustomerDTOToCustomer(customerDTO));
        return convertCustomerToCustomerDTO(savedCustomer);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> allCustomers = customerService.getAllCustomers();
        return allCustomers.stream().map(this::convertCustomerToCustomerDTO).collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Customer customer = customerService.getOwnerByPetId(petId);
        return convertCustomerToCustomerDTO(customer);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeService.saveEmployee(convertEmployeeDTOToEmployee(employeeDTO));
        return convertEmployeeToEmployeeDTO(employee);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        return convertEmployeeToEmployeeDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> employees = employeeService.findEmployeesForService(employeeDTO.getSkills(), employeeDTO.getDate());
        return employees.stream().map(this::convertEmployeeToEmployeeDTO).collect(Collectors.toList());
    }

}
