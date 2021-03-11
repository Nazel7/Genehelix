package com.genehelix.controllers.rests;


import com.genehelix.entities.Employee;
import com.genehelix.interfaces.IEmployeeCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GeneralController {

    @Autowired
    private IEmployeeCustomerService IEmployeeCustomerService;

    @GetMapping("/employees")

    public List<Employee> getEmployees() {
        return IEmployeeCustomerService.getEmployees();
    }

    @GetMapping("/employees/{ID}")
    public Employee getEmployee(@PathVariable int ID) {
        return IEmployeeCustomerService.getEmployee(ID);
    }

    @PostMapping("/employees/post")
    public void addEmployee(@RequestBody Employee employee) {
        employee.setId(0);
        IEmployeeCustomerService.addEmployee(employee);
    }

    @PutMapping("/employees/put")
    public void putEmployee(@RequestBody Employee employee) {
        IEmployeeCustomerService.addEmployee(employee);
    }

    @DeleteMapping("/employees/{deleteID}")
    public void deleteEmployee(@PathVariable int deleteID) {
        IEmployeeCustomerService.deleteEmployee(deleteID);
    }
}
