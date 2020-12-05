package com.spring.boot.App2.springbootprojectwithdatarest.controller;


import com.spring.boot.App2.springbootprojectwithdatarest.appServiceDAO.EmployeeServiceDAO;
import com.spring.boot.App2.springbootprojectwithdatarest.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api")
public class RestController {

    @Autowired
    private EmployeeServiceDAO employeeServiceDAO;

    @GetMapping("/employees")

    public List<Employee> getEmployees() {
        return employeeServiceDAO.getEmployees();
    }

    @GetMapping("/employees/{ID}")
    public Employee getEmployee(@PathVariable int ID) {
        return employeeServiceDAO.getEmployee(ID);
    }

    @PostMapping("/employees/post")
    public void addEmployee(@RequestBody Employee employee) {
        employee.setId(0);
        employeeServiceDAO.addEmployee(employee);
    }

    @PutMapping("/employees/put")
    public void putEmployee(@RequestBody Employee employee) {
        employeeServiceDAO.addEmployee(employee);
    }

    @DeleteMapping("/employees/{deleteID}")
    public void deleteEmployee(@PathVariable int deleteID) {
        employeeServiceDAO.deleteEmployee(deleteID);
    }
}
