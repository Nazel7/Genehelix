package com.spring.boot.App2.springbootprojectwithdatarest.controllers;


import com.spring.boot.App2.springbootprojectwithdatarest.services.EmployeeService;
import com.spring.boot.App2.springbootprojectwithdatarest.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")

    public List<Employee> getEmployees() {
        return employeeService.getEmployees();
    }

    @GetMapping("/employees/{ID}")
    public Employee getEmployee(@PathVariable int ID) {
        return employeeService.getEmployee(ID);
    }

    @PostMapping("/employees/post")
    public void addEmployee(@RequestBody Employee employee) {
        employee.setId(0);
        employeeService.addEmployee(employee);
    }

    @PutMapping("/employees/put")
    public void putEmployee(@RequestBody Employee employee) {
        employeeService.addEmployee(employee);
    }

    @DeleteMapping("/employees/{deleteID}")
    public void deleteEmployee(@PathVariable int deleteID) {
        employeeService.deleteEmployee(deleteID);
    }
}
