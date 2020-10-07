package com.example.SqlSpringExample.controller;

import java.util.List;


import com.example.SqlSpringExample.exception.ResourceNotFoundException;
import com.example.SqlSpringExample.model.Employee;
import com.example.SqlSpringExample.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @DeleteMapping("/employees/{id}")
    public void deleteEmployee(@PathVariable(value = "id") Long employeeId){
        employeeRepository.deleteById(employeeId);
    }

    @PutMapping("/employees/{id}")
    public void updateEmployee(@PathVariable(value = "id") Long employeeId,
                                                   @Valid @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

        employee.setM_no(employeeDetails.getM_no());
        employee.setFirstName(employeeDetails.getFirstName());
        employeeRepository.save(employee);
    }
}
