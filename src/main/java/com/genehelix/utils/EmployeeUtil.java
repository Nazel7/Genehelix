package com.genehelix.utils;

import com.genehelix.interfaces.IEmployeeCustomerService;
import com.genehelix.entities.Employee;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

public class EmployeeUtil {

    public static String getEmployeePage(Model model, IEmployeeCustomerService IEmployeeCustomerService, int pageNo) {
        int pageSize = 5;
        Page<Employee> page = IEmployeeCustomerService.findPaginated(pageNo, pageSize);
        System.out.println(page.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("employeeList", page.getContent());
        model.addAttribute("totalPage", page.getTotalPages());

        return "employee-list";
    }

}
