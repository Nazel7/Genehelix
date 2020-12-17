package com.genehelix.controllers;


import com.genehelix.interfaces.IEmployeeService;
import com.genehelix.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    @Autowired
    private IEmployeeService IEmployeeService;

    @GetMapping("/employees")

    public List<Employee> getEmployees() {
        return IEmployeeService.getEmployees();
    }

    @GetMapping("/employees/{ID}")
    public Employee getEmployee(@PathVariable int ID) {
        return IEmployeeService.getEmployee(ID);
    }

    @PostMapping("/employees/post")
    public void addEmployee(@RequestBody Employee employee) {
        employee.setId(0);
        IEmployeeService.addEmployee(employee);
    }

    @PutMapping("/employees/put")
    public void putEmployee(@RequestBody Employee employee) {
        IEmployeeService.addEmployee(employee);
    }

    @DeleteMapping("/employees/{deleteID}")
    public void deleteEmployee(@PathVariable int deleteID) {
        IEmployeeService.deleteEmployee(deleteID);
    }
}
