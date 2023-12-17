package com.luv2code.springboot.thymeleafdemo.controller;


import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import com.luv2code.springboot.thymeleafdemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeService employeeService;


    @Autowired
    public EmployeeController(EmployeeService theEmployeeService) {
        this.employeeService = theEmployeeService;
    }

    //add mapping for "/list

    @GetMapping("/list")
    public String listEmployees(Model theModel){
        //get the employee from db

        List<Employee> theEmployees = employeeService.findAll();

        // add to the spring mode
        theModel.addAttribute("employees", theEmployees);

        return "employees/list-employees";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("employeeId") int thId, Model theModel){

        //get the employee from the service
        Employee theEmployee = employeeService.findById(thId);

        //set employee in the model to prepupulate the form
        theModel.addAttribute("employee", theEmployee);

        return "employees/employee-form";
    }


    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel)
    {
        Employee theEmployee = new Employee();

        theModel.addAttribute("employee",theEmployee);


        return "employees/employee-form";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee theEmployee){
        employeeService.save(theEmployee);

        //use a redirect to prevent duplicate submission

        return "redirect:/employees/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("employeeId") int theId){
        //delete the employee

        employeeService.deleteById(theId);

        //redirect to the /employee/list
        return "redirect:/employees/list";
    }
}
