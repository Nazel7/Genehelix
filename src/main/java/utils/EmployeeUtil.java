package utils;

import com.spring.boot.App2.springbootprojectwithdatarest.services.EmployeeService;
import com.spring.boot.App2.springbootprojectwithdatarest.entities.Employee;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

public class EmployeeUtil {

    public static String getEmployeePage(Model model, EmployeeService employeeService, int pageNo) {
        int pageSize = 5;
        Page<Employee> page = employeeService.findPaginated(pageNo, pageSize);
        System.out.println(page.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("employeeList", page.getContent());
        model.addAttribute("totalPage", page.getTotalPages());

        return "employee-list";
    }

}
